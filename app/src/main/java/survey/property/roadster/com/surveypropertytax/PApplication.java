package survey.property.roadster.com.surveypropertytax;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import di.AppComponent;
import di.DaggerAppComponent;
import ui.data.PropertyDto;

public class PApplication extends DaggerBaseApplication {

    @Inject
    FirebaseJobDispatcher firebaseJobDispatcher;

    private static volatile PApplication sInstance;

    public AppComponent appComponent;

    private List<PropertyDto> propertyDtos;

    private static PApplication pApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        scheduleOfflineJob();
    }


    public static PApplication getInstance() {
        if (sInstance == null) {
            synchronized (PApplication.class) {
                if (sInstance == null) {
                    sInstance = new PApplication();
                }
            }
        }
        return sInstance;
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

    public List<PropertyDto> getPropertyDtos() {
        return propertyDtos;
    }

    public void addOfflinePropertyItem(PropertyDto propertyDto){
        if(propertyDtos == null) {
            propertyDtos = new ArrayList<>();
        }
        propertyDtos.add(propertyDto);
    }

    public void removeOfflinePropertyItem(PropertyDto propertyDto){
        List<PropertyDto> copy = propertyDtos;
        int i = 0;
        for(PropertyDto p: propertyDtos){
            if(p.getPropertyId() == propertyDto.getPropertyId()){
                copy.remove(i);
            }
            i++;
        }
        propertyDtos = copy;
    }
}
