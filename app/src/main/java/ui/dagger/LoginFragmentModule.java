package ui.dagger;

import dagger.Binds;
import dagger.Module;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragmentModule;
import ui.LoginView;
import ui.fragment.LoginFragment;

@Module(includes = {SurveyBaseFragmentModule.class})
public abstract class LoginFragmentModule {

    @Binds
    abstract SurveyBaseFragment loginFragment(LoginFragment loginFragment);

    /*@Binds
    abstract LoginView loginView(LoginFragment loginFragment);*/
}
