package ui.dagger;

import dagger.Binds;
import dagger.Module;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragmentModule;
import ui.fragment.HomeFragment;

@Module(includes = {SurveyBaseFragmentModule.class})
public abstract class LoginFragmentModule {

    @Binds
    abstract SurveyBaseFragment loginFragment(HomeFragment homeFragment);

    /*@Binds
    abstract HomeView loginView(HomeFragment loginFragment);*/
}
