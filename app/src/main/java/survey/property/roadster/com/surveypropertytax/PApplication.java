package survey.property.roadster.com.surveypropertytax;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import di.AppComponent;
import di.DaggerAppComponent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;
import ui.data.DetailDto;
import ui.data.JsonFileList;
import ui.data.PropertyDto;
import ui.data.PropertyRepoMapper;

public class PApplication extends DaggerBaseApplication {

    private static final String TAG = PApplication.class.getSimpleName();

    @Inject
    FirebaseJobDispatcher firebaseJobDispatcher;

    SharedPreferences sharedPreferences;

    private static volatile PApplication sInstance;

    public AppComponent appComponent;

    private List<PropertyDto> propertyDtos;

    private static PApplication pApplication;

    private List<PropertyDbObject> propertyDbObjects;

    @Override
    public void onCreate() {
        super.onCreate();
        readFileFromJsonFile();
        scheduleOfflineJob();
    }

    private void readFileFromJsonFile() {
        Observable.fromCallable(() -> {
            String json = null;
            try {
                InputStream inputStream = this.getAssets().open("data.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json = new String(buffer, "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new PropertyRepoMapper(new Gson().fromJson(json, JsonFileList.class));
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(propertyRepoMapper -> {
            setPropertyDbObjects(propertyRepoMapper.getPropertyDbObjectList());
        });
    }

    @Override
    public AppComponent applicationInjector() {
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    private void scheduleOfflineJob() {
        Job myJob = firebaseJobDispatcher.newJobBuilder()
                .setService(OfflineJobService.class)
                .setRecurring(false)
                .setReplaceCurrent(true)
                .setTrigger(false ? Trigger.executionWindow(0, 60) : Trigger.NOW)
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setTag(true ? "offline-service" : "offline-service-ot")
                .setLifetime(Lifetime.FOREVER)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();
        firebaseJobDispatcher.schedule(myJob);
    }

    public List<PropertyDto> getPropertyDtos() {
        return propertyDtos;
    }

    public void addOfflinePropertyItem(PropertyDto propertyDto) {
        if (propertyDtos == null) {
            propertyDtos = new ArrayList<>();
        }
        propertyDtos.add(propertyDto);
        uploadData();
    }

    public void removeOfflinePropertyItem(PropertyDto propertyDto) {
        List<PropertyDto> copy = propertyDtos;
        int i = 0;
        for (PropertyDto p : propertyDtos) {
            if (p.getPropertyId().equals(propertyDto.getPropertyId())) {
                copy.remove(i);
            }
            i++;
        }
        propertyDtos = copy;
    }

    public List<PropertyDbObject> getPropertyDbObjects() {
        return propertyDbObjects;
    }

    public void setPropertyDbObjects(List<PropertyDbObject> propertyDbObjects) {
        this.propertyDbObjects = propertyDbObjects;
    }

    void uploadData() {
        if (getPropertyDtos() != null) {
            for (PropertyDto p : getPropertyDtos()) {
                uploadSignature(p);
                removeOfflinePropertyItem(p);
            }
        } else {
            Log.d(TAG, "Property getting NULL");
        }
    }

    public void uploadSignature(PropertyDto propertyData) {
        StorageReference signatureImagesRef = FirebaseStorage.getInstance().getReference().child("signature/" + propertyData.getPropertyId() + "_" + propertyData.getPropertyName() + "_signature.jpg");

        Bitmap bitmap = propertyData.getSingatureBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = signatureImagesRef.putBytes(data);

        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Log.i("PIC", exception.getMessage());
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...
            propertyData.setUrlSignature(taskSnapshot.getDownloadUrl().toString());
            uploadPropertyPicture(propertyData);
        });
    }

    public void uploadPropertyPicture(PropertyDto propertyData) {
        StorageReference propertyImagesRef = FirebaseStorage.getInstance().getReference().child("photo/" + propertyData.getPropertyId() + "_" + propertyData.getPropertyName() + "_photo.jpg");
        Bitmap bitmap = propertyData.getPhotoBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = propertyImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Log.i("PIC", exception.getMessage());
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...
            propertyData.setUrlPropertyImage(taskSnapshot.getDownloadUrl().toString());
            writeToDatabase(propertyData);
        });
    }

    private void writeToDatabase(PropertyDto propertyData) {
        String uId = String.valueOf(propertyData.getPropertyId());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DetailDto detailDto = new DetailDto(propertyData);
        detailDto.setName(user.getDisplayName());
        detailDto.setEmailId(user.getEmail());
        detailDto.setProfilePicUrl(user.getPhotoUrl().toString());
        detailDto.setCreatedDate(System.currentTimeMillis());
        detailDto.setPhoneNumber(user.getPhoneNumber());
        if (user != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("property_survey");
            reference = reference.child(uId);
            reference.setValue(detailDto).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, String.valueOf(task.getResult()));
                }
            })
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Write success"))
                    .addOnFailureListener(e -> {
                        // Write failed
                        // ...
                        Log.d(TAG, "Write fail");

                    });
        } else {
            Log.d(TAG, "user getting null");
        }
    }
}
