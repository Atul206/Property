package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;

import di.ActivityScope;
import survey.property.roadster.com.surveypropertytax.SurveyBaseActivity;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.LocationUtil.LocationHelper;
import ui.data.PropertyData;
import ui.data.PropertyDto;
import ui.fragment.FormFragment;
import ui.fragment.HomeFragment;

@ActivityScope
public class HomeActivity extends SurveyBaseActivity implements HomeFragment.LoginIntraction, FormFragment.LoginIntraction  {

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void gotoFormFragment(PropertyDto data) {
        //TODO : @anshul intiate your developed fragment
        //startFragment();
        startFragment(FormFragment.newInstance(data, getLastLocation()),true);
    }

    @Override
    public Class<? extends SurveyBaseFragment> initialFragmentClass() {
        return HomeFragment.class;
    }

}
