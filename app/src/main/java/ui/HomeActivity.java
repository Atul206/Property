package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.storage.StorageReference;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import javax.inject.Inject;

import di.ActivityScope;
import survey.property.roadster.com.surveypropertytax.R;
import survey.property.roadster.com.surveypropertytax.SurveyBaseActivity;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.LocationUtil.LocationHelper;
import ui.activity.LoginIntroActivity;
import ui.data.PropertyData;
import ui.data.PropertyDto;
import ui.enums.TagType;
import ui.fragment.FormFragment;
import ui.fragment.HomeFragment;
import ui.fragment.PropertyActionFragment;
import ui.fragment.PropertyScanFragment;

@ActivityScope
public class HomeActivity extends SurveyBaseActivity
        implements HomeFragment.LoginIntraction, FormFragment.LoginIntraction,
        PropertyActionFragment.PropertyActionFragmentIntraction,
        PropertyScanFragment.PropertyScanFragmentIntraction{

    String qrCodeString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void gotoFormFragment(PropertyDto data) {
        startFragment(FormFragment.newInstance(data, getLastLocation(), TagType.NO_ACTION),true);
    }

    @Override
    public void gotoScanFragment() {
        startFragment(PropertyScanFragment.newInstance(), true);
    }

    @Override
    public void gotoActionFragment(PropertyDto data) {
        startFragment(PropertyActionFragment.newInstance(data), true);
    }

    @Override
    public void gotoFormFragment(PropertyDto data, TagType tagType) {
        startFragment(FormFragment.newInstance(data, getLastLocation(), tagType),true);
    }

    @Override
    public Class<? extends SurveyBaseFragment> initialFragmentClass() {
        return HomeFragment.newInstance().getClass();
    }

    @Override
    public void gotoFormFragment(String text) {
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setPropertyId(text);

        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(android.R.color.white)
                .setIcon(R.drawable.ic_property_action)
                //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                .setTitle("Property id - " + String.valueOf(text))
                .setMessage("Please select an action")
                .setPositiveButton(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(FormFragment.newInstance(propertyDto, getLastLocation(), TagType.YES),true);
                        Toast.makeText(HomeActivity.this, "Yes clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no, new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        startFragment(FormFragment.newInstance(propertyDto, getLastLocation(), TagType.NO),true);
                        Toast.makeText(HomeActivity.this, "No clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public void qrCodeString(String text) {
        this.qrCodeString = text;
        ((HomeFragment)getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName())).setQrCodeStr(qrCodeString);

    }
}
