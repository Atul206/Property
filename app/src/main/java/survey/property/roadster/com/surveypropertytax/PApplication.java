package survey.property.roadster.com.surveypropertytax;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import di.AppComponent;
import di.DaggerAppComponent;

public class PApplication extends DaggerBaseApplication {

    //TODO :
    //@Inject
    //FirebaseJobDispatcher firebaseJobDispatcher;

    public AppComponent appComponent;

    @Override
    public AppComponent applicationInjector() {
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
