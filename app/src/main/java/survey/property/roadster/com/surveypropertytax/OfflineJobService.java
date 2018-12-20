package survey.property.roadster.com.surveypropertytax;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class OfflineJobService  extends JobService {

    private static final String TAG = OfflineJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters job) {
        if(CommonUtil.isNetworkAvailable(this)){
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
