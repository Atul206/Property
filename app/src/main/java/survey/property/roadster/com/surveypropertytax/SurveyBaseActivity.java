package survey.property.roadster.com.surveypropertytax;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class SurveyBaseActivity extends DaggerAppCompatActivity implements HasFragmentInjector, HasSupportFragmentInjector {

    private ViewGroup vMainContainer;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<android.app.Fragment> frameworkFragmentInjector;

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

        if (savedInstanceState == null && initialFragmentClass() != null)
            startFragment(initialFragmentClass(), true);


    }

    protected abstract int getLayout();

    public Class<? extends SurveyBaseFragment> initialFragmentClass() {
        return null;
    }

    protected void startFragment(
            @NonNull Class<? extends SurveyBaseFragment> fragmentClass,
            boolean addToBackStack) {
        SurveyBaseFragment fragment = null;
        try {
            fragment = fragmentClass.newInstance();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            int index = fm.getBackStackEntryCount() - 1;
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
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
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
}
