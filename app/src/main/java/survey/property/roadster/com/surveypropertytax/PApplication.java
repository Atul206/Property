package survey.property.roadster.com.surveypropertytax;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.danlew.android.joda.JodaTimeAndroid;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import di.AppComponent;
import di.DaggerAppComponent;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;
import survey.property.roadster.com.surveypropertytax.db.SubmitedLoadObject;
import ui.data.DetailDto;
import ui.data.JsonFileList;
import ui.data.PropertyDto;
import ui.data.PropertyRepoMapper;
import ui.repo.LoadRepository;
import ui.repo.PropertyRepository;

public class PApplication extends DaggerBaseApplication {

    private static final String TAG = PApplication.class.getSimpleName();

    @Inject
    FirebaseJobDispatcher firebaseJobDispatcher;

    @Inject
    PropertyRepository propertyRepository;

    @Inject
    LoadRepository loadRepository;

    SharedPreferences sharedPreferences;

    private static volatile PApplication sInstance;

    public AppComponent appComponent;

    private List<PropertyDto> propertyDtos;

    private static PApplication pApplication;

    private List<PropertyDbObject> propertyDbObjects;

    private String[] fileName = {
            "data.json",
            "file_1000.json",
            "file_2000.json",
            "file_3000.json",
            "file_4000.json",
            "file_5000.json",
            "file_6000.json",
            "file_7000.json",
            "file_8000.json",
            "file_9000.json",
            "file_10000.json",
            "file_11000.json",
            "file_12000.json",
            "file_13000.json",
            "file_14000.json",
            "file_15000.json",
            "file_16000.json",
            "file_17000.json",
            "file_18000.json",
            "file_19000.json",
            "file_20000.json",
            "file_21000.json",
            "file_22000.json",
            "file_23000.json",
            "file_24000.json",
            "file_25000.json",
            "file_26000.json",
            "file_27000.json",
            "file_28000.json",
            "file_29000.json",
            "file_30000.json",
            "file_31000.json",
            "file_32000.json",
            "file_33000.json",
            "file_34000.json",
            "file_35000.json",
            "file_36000.json",
            "file_37000.json",
            "file_38000.json",
            "file_39000.json",
            "file_40000.json",
            "file_41000.json",
            "file_42000.json",
            "file_43000.json",
            "file_44000.json",
            "file_45000.json",
            "file_46000.json",
            "file_47000.json",
            "file_48000.json",
            "file_49000.json",
            "file_50000.json",
            "file_51000.json",
            "file_52000.json",
            "file_53000.json",
            "file_54000.json",
            "file_55000.json",
            "file_56000.json",
            "file_57000.json",
            "file_58000.json",
            "file_59000.json",
            "file_60000.json",
            "file_61000.json",
            "file_62000.json",
            "file_63000.json",
            "file_64000.json",
            "file_65000.json",
            "file_66000.json",
            "file_67000.json",
            "file_68000.json",
            "file_69000.json",
            "file_70000.json",
            "file_71000.json",
            "file_72000.json",
            "file_73000.json",
            "file_74000.json",
            "file_75000.json",
            "file_76000.json",
            "file_77000.json",
            "file_78000.json",
            "file_79000.json",
            "file_80000.json",
            "file_81000.json",
            "file_82000.json",
            "file_83000.json",
            "file_84000.json",
            "file_85000.json",
            "file_86000.json",
            "file_87000.json",
            "file_88000.json",
            "file_89000.json",
            "file_90000.json",
            "file_91000.json",
            "file_92000.json",
            "file_93000.json",
            "file_94000.json",
            "file_95000.json",
            "file_96000.json",
            "file_97000.json",
            "file_98000.json",
            "file_99000.json",
            "file_100000.json",
            "file_101000.json",
            "file_102000.json",
            "file_103000.json",
            "file_104000.json",
            "file_105000.json",
            "file_106000.json",
            "file_107000.json",
            "file_108000.json",
            "file_109000.json",
            "file_110000.json",
            "file_111000.json",
            "file_112000.json",
            "file_113000.json",
            "file_114000.json",
            "file_115000.json",
            "file_116000.json",
            "file_117000.json",
            "file_118000.json",
            "file_119000.json",
            "file_120000.json",
            "file_121000.json",
            "file_122000.json",
            "file_123000.json",
            "file_124000.json",
            "file_125000.json",
            "file_126000.json",
            "file_127000.json",
            "file_128000.json",
            "file_129000.json",
            "file_130000.json",
            "file_131000.json",
            "file_132000.json",
            "file_133000.json",
            "file_134000.json",
            "file_135000.json",
            "file_136000.json",
            "file_137000.json",
            "file_138000.json",
            "file_139000.json",
            "file_140000.json",
            "file_141000.json",
            "file_142000.json",
            "file_143000.json",
            "file_144000.json",
            "file_145000.json",
            "file_146000.json",
            "file_147000.json",
            "file_148000.json",
            "file_149000.json",
            "file_150000.json",
            "file_151000.json",
            "file_152000.json",
            "file_153000.json",
            "file_154000.json",
            "file_155000.json",
            "file_156000.json",
            "file_157000.json",
            "file_158000.json",
            "file_159000.json",
            "file_160000.json",
            "file_161000.json",
            "file_162000.json",
            "file_163000.json",
            "file_164000.json",
            "file_165000.json",
            "file_166000.json",
            "file_167000.json",
            "file_168000.json",
            "file_169000.json",
            "file_170000.json",
            "file_171000.json",
            "file_172000.json",
            "file_173000.json",
            "file_174000.json",
            "file_175000.json",
            "file_176000.json",
            "file_177000.json",
            "file_178000.json",
            "file_179000.json",
            "file_180000.json",
            "file_181000.json",
            "file_182000.json",
            "file_183000.json",
            "file_184000.json",
            "file_185000.json",
            "file_186000.json",
            "file_187000.json",
            "file_188000.json",
            "file_189000.json",
            "file_190000.json",
            "file_191000.json",
            "file_192000.json",
            "file_193000.json",
            "file_194000.json",
            "file_195000.json",
            "file_196000.json",
            "file_197000.json",
            "file_198000.json",
            "file_199000.json",
            "file_200000.json",
            "file_201000.json",
            "file_202000.json",
            "file_203000.json",
            "file_204000.json",
            "file_205000.json",
            "file_206000.json",
            "file_207000.json",
            "file_208000.json",
            "file_209000.json",
            "file_210000.json"

    };


    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        propertyRepository.isEmpty().observeOn(Schedulers.computation()).subscribeOn(Schedulers.computation()).delay(1000, TimeUnit.MILLISECONDS).subscribe(i -> {
            if (i == null || i == 0) {
                readFileFromJsonFile();
            }
        });
        scheduleOfflineJob();
        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

    }

    public void readFileFromJsonFile() {
        for (String f : fileName) {
            Observable.fromCallable(() -> {
                String json = null;
                try {
                    InputStream inputStream = this.getAssets().open(f);
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();
                    json = new String(buffer, "UTF-8");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //return new PropertyRepoMapper(new JsonFileList());
                JsonFileList jsonFileLists = new JsonFileList();
                try {
                    jsonFileLists = new Gson().fromJson(json, JsonFileList.class);
                } catch (JsonSyntaxException e) {
                    Log.d(TAG + " Fallback", f);
                    e.printStackTrace();
                }
                return new PropertyRepoMapper(jsonFileLists);
            }).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation()).subscribe(propertyRepoMapper -> {
                //Log.d(TAG, f);
                propertyRepository.insertAll(propertyRepoMapper.getPropertyDbObjectList());
                //setPropertyDbObjects(propertyRepoMapper.getPropertyDbObjectList());
            });
        }
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
        if(this.propertyDbObjects == null) {
            this.propertyDbObjects = new ArrayList<>();
        }
        this.propertyDbObjects.addAll(propertyDbObjects);
    }

    void uploadData() {
        List<PropertyDto> removeList = new ArrayList<>();
        if (getPropertyDtos() != null) {
            for (PropertyDto p : getPropertyDtos()) {
                uploadSignature(p);
                removeList.add(p);
            }

            for(PropertyDto p:removeList){
                removeOfflinePropertyItem(p);
            }
        } else {
            Log.d(TAG, "Property getting NULL");
        }
    }

    public void uploadSignature(PropertyDto propertyData) {
        StorageReference signatureImagesRef = FirebaseStorage.getInstance().getReference().child("signature/" + propertyData.getPropertyId() + "_" + propertyData.getPropertyName() + "_signature.jpg");

        Bitmap bitmap = propertyData.getSingatureBitmap();
        if (bitmap != null) {
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
                propertyData.setSingatureBitmap(null);
                propertyData.setUrlSignature(taskSnapshot.getDownloadUrl().toString());
                uploadPropertyPicture(propertyData);
            });
        } else {
            uploadPropertyPicture(propertyData);
        }
    }

    public void uploadPropertyPicture(PropertyDto propertyData) {
        StorageReference propertyImagesRef = FirebaseStorage.getInstance().getReference().child("photo/" + propertyData.getPropertyId() + "_" + propertyData.getPropertyName() + "_photo.jpg");
        Bitmap bitmap = propertyData.getPhotoBitmap();
        if (bitmap != null) {
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
                propertyData.setPhotoBitmap(null);
                propertyData.setUrlPropertyImage(taskSnapshot.getDownloadUrl().toString());
                writeToDatabase(propertyData);
            });
        } else {
            writeToDatabase(propertyData);
        }
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
            reference.setValue(detailDto, (databaseError, databaseReference) -> Log.d(TAG, "Action taken"));
            loadRepository.insert(new SubmitedLoadObject(propertyData.getPropertyId(), true, Calendar.getInstance().getTimeInMillis()));
        } else {
            Log.d(TAG, "user getting null");
        }
    }

    public void readDataFromDatabase(String uId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("property_survey");
        reference = reference.child(uId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                DetailDto value = dataSnapshot.getValue(DetailDto.class);
                Log.d(TAG, "Value is: " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
