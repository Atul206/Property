package ui.dagger;

import dagger.Module;
import dagger.Provides;
import di.ActivityScope;
import survey.property.roadster.com.surveypropertytax.SurveyBaseActivity;
import ui.LoginActivity;

@Module
public class LoginActivityModule {

    @ActivityScope
    @Provides
    public static SurveyBaseActivity surveyBaseActivity(LoginActivity loginActivity){
        return loginActivity;
    }
}
