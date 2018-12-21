package survey.property.roadster.com.surveypropertytax;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.inject.Inject;

import ui.data.DetailDto;
import ui.data.PropertyDto;

import static android.support.constraint.Constraints.TAG;

public class OfflineJobService  extends JobService {

    @Inject
    StorageReference storageReference;

    @Inject
    DatabaseReference databaseReference;

    private static final String TAG = OfflineJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters job) {
        if(CommonUtil.isNetworkAvailable(this)){
            List<PropertyDto> propertyDtos = PApplication.getInstance().getPropertyDtos();
            for(PropertyDto p:propertyDtos){
                uploadPropertyPicture(p);
                uploadPropertyPicture(p);
                PApplication.getInstance().removeOfflinePropertyItem(p);
            }
            Log.d(TAG, "Job Started and call every 15 min duration");
        }else{
            Log.d(TAG, "Mobile device is offline or No internet connectivity, will try next scheduler");
        }
        return true;
    }

    public void uploadSignature(PropertyDto propertyData) {
        StorageReference signatureImagesRef = storageReference.child("signature/" + propertyData.getPropertyId() + "_" + propertyData.getPropertyName() + "_signature.jpg");

        Bitmap bitmap = propertyData.getSingatureBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = signatureImagesRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("PIC",exception.getMessage());
            }
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...
            propertyData.setUrlSignature(taskSnapshot.getDownloadUrl().toString());
            writeToDatabase(propertyData);
        });
    }

    public void uploadPropertyPicture(PropertyDto propertyData) {
        StorageReference propertyImagesRef = storageReference.child("photo/" + propertyData.getPropertyId() + "_" + propertyData.getPropertyName() + "_photo.jpg");
        Bitmap bitmap = propertyData.getPhotoBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = propertyImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Log.i("PIC",exception.getMessage());
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
        DetailDto detailDto = new DetailDto();
        detailDto.setName(user.getDisplayName());
        detailDto.setEmailId(user.getEmail());
        detailDto.setProfilePicUrl(user.getPhotoUrl().toString());
        detailDto.setCreatedDate(System.currentTimeMillis());
        detailDto.setPhoneNumber(user.getPhoneNumber());
        detailDto.setData(propertyData);
        if (user != null) {
            databaseReference.child(uId).setValue(detailDto)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Write success"))
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            // ...
                            Log.d(TAG, "Write fail");

                        }
                    });
        } else {
            Log.d(TAG, "user getting null");
        }
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
