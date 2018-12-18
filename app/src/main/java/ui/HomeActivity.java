package ui;

import di.ActivityScope;
import survey.property.roadster.com.surveypropertytax.SurveyBaseActivity;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
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
    public Class<? extends SurveyBaseFragment> initialFragmentClass() {
        return HomeFragment.class;
    }
}
