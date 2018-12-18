package ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import di.ActivityScope;
import survey.property.roadster.com.surveypropertytax.SurveyBaseActivity;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.fragment.LoginFragment;

@ActivityScope
public class LoginActivity extends SurveyBaseActivity implements LoginFragment.LoginIntraction  {

    @Override
    protected int getLayout() {
        return android.support.compat.R.layout.notification_action;
    }

    @Override
    public Class<? extends SurveyBaseFragment> initialFragmentClass() {
        return LoginFragment.class;
    }
}
