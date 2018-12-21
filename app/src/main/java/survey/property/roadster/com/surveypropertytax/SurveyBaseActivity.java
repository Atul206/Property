package survey.property.roadster.com.surveypropertytax;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;
import ui.LocationUtil.LocationHelper;

public abstract class SurveyBaseActivity extends DaggerAppCompatActivity implements HasFragmentInjector, HasSupportFragmentInjector, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private ViewGroup vMainContainer;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<android.app.Fragment> frameworkFragmentInjector;

    LocationHelper locationHelper;

    Location mLastLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        vMainContainer = findViewById(R.id.main_container);

        if (getLayout() != 0) {
            getLayoutInflater().inflate(getLayout(), vMainContainer);
        }

        ButterKnife.bind(this);

        setUpLocation();

        if (savedInstanceState == null && initialFragmentClass() != null)
            startFragment(initialFragmentClass(), true, null);
    }

    private void setUpLocation(){
        locationHelper = new LocationHelper(this);
        locationHelper.checkpermission();
        // check availability of play services
        if (locationHelper.checkPlayServices()) {
            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient(this, this);
        }
    }

    protected abstract int getLayout();

    public Class<? extends SurveyBaseFragment> initialFragmentClass() {
        return null;
    }

    public void startFragment(
            @NonNull Class<? extends SurveyBaseFragment> fragmentClass,
            boolean addToBackStack,
            @Nullable Uri uri) {
        try {
            SurveyBaseFragment fragment = fragmentClass.newInstance();
            //fragment.setUri(uri);
            startFragment(
                    fragment,
                    addToBackStack
            );
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void startFragment(
            @NonNull SurveyBaseFragment fragment,
            boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //CommonLib.hideKeyboard(this);

        int index = fm.getBackStackEntryCount() - 1;
//         hack to speed up animations
//         uncomment when we include animations
//        int index = fm.getBackStackEntryCount() - 1;
        if (index >= 0) {
            FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(index);
            String tag = backEntry.getName();

            transaction.hide(fm.findFragmentByTag(tag));
        }

        transaction.
                add(R.id.main_container, fragment, fragment.getClass().getSimpleName());


        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        transaction.commitAllowingStateLoss();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    @Override
    public AndroidInjector<android.app.Fragment> fragmentInjector() {
        return frameworkFragmentInjector;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public Location getLastLocation(){
        return mLastLocation;
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Once connected with google api, get the location
        mLastLocation = locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        locationHelper.connectApiClient();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        locationHelper.onActivityResult(requestCode, resultCode, data);
    }
}
