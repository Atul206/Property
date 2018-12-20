package ui;

import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;

import di.ActivityScope;
import survey.property.roadster.com.surveypropertytax.SurveyBaseActivity;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.data.PropertyData;
import ui.data.PropertyDto;
import ui.fragment.HomeFragment;

@ActivityScope
public class HomeActivity extends SurveyBaseActivity implements HomeFragment.LoginIntraction  {

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
    }

    @Override
    public Class<? extends SurveyBaseFragment> initialFragmentClass() {
        return HomeFragment.class;
    }
}
