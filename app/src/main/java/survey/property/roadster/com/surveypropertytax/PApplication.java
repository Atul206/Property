package survey.property.roadster.com.surveypropertytax;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.gson.Gson;

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
import ui.data.JsonFileList;
import ui.data.PropertyDto;
import ui.data.PropertyRepoMapper;

public class PApplication extends DaggerBaseApplication {

    @Inject
    FirebaseJobDispatcher firebaseJobDispatcher;

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
            if(p.getPropertyId().equals(propertyDto.getPropertyId())){
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
}
