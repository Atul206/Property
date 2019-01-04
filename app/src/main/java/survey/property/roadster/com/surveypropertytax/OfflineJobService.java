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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ui.data.DetailDto;
import ui.data.PropertyDto;

public class OfflineJobService  extends JobService {

    private static final String TAG = OfflineJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters job) {
        if(CommonUtil.isNetworkAvailable(this)){
            PApplication application = (PApplication) getApplication();
            //application.uploadData();
            Log.d(TAG, "Job Started and call every 15 min duration");
        }else{
            Log.d(TAG, "Mobile device is offline or No internet connectivity, will try next scheduler");
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
