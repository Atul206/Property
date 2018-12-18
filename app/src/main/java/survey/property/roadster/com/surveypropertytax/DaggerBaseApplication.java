package survey.property.roadster.com.surveypropertytax;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.BroadcastReceiver;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;

public abstract class DaggerBaseApplication extends Application implements HasActivityInjector, HasFragmentInjector{

    @Inject DispatchingAndroidInjector<Activity> activityInjector;
    @Inject DispatchingAndroidInjector<Fragment> fragmentInjector;
    private volatile boolean requireInject = true;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    /**
     * Implementations should return an {@link AndroidInjector} for the concrete {@link
     * DaggerBaseApplication}. Typically, that injector is a {@link dagger.Component}.
     */
    public abstract AndroidInjector<? extends DaggerBaseApplication> applicationInjector();

    private void initInjector(){
        if(requireInject){
            synchronized (this) {
                if (requireInject) {
                    @SuppressWarnings("unchecked")
                    AndroidInjector<DaggerBaseApplication> applicationInjector =
                            (AndroidInjector<DaggerBaseApplication>) applicationInjector();
                    applicationInjector.inject(this);
                    if (requireInject) {
                        throw new IllegalStateException(
                                "The AndroidInjector returned from applicationInjector() did not buildAndInject the "
                                        + "DaggerApplication");
                    }
                }
            }
        }
    }

    @Inject
    void setInjected() {
        requireInject = false;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> fragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

}
