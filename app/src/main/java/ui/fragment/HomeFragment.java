package ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import di.FragmentScope;
import survey.property.roadster.com.surveypropertytax.BaseIntranction;
import survey.property.roadster.com.surveypropertytax.R;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.HomePresenter;

@FragmentScope
public class HomeFragment extends SurveyBaseFragment<HomePresenter, HomeFragment.LoginIntraction> {


    public HomeFragment() {
    }

    @Override
    public void postInit() {
        super.postInit();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initLayout() {

    }

    @NonNull
    @Override
    protected Class<LoginIntraction> getListenerClass() {
        return LoginIntraction.class;
    }

    public interface LoginIntraction extends BaseIntranction {

    }
}
