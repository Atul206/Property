package survey.property.roadster.com.surveypropertytax;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import javax.inject.Inject;

import di.AppComponent;
import di.DaggerAppComponent;

public class PApplication extends DaggerBaseApplication {

    @Inject
    FirebaseJobDispatcher firebaseJobDispatcher;

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        scheduleOfflineJob();
    }

    @Override
    public AppComponent applicationInjector() {
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    private void scheduleOfflineJob(){
        Job myJob = firebaseJobDispatcher.newJobBuilder()
                .setService(OfflineJobService.class)
                .setRecurring(true)
                .setReplaceCurrent(true)
                .setTrigger(true ? Trigger.executionWindow(60, 60*2) : Trigger.NOW)
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setTag(true ? "offline-service" : "offline-service-ot")
                .setLifetime(Lifetime.FOREVER)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();
        firebaseJobDispatcher.schedule(myJob);
    }
}
