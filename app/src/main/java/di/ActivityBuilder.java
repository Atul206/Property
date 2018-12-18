package di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import survey.property.roadster.com.surveypropertytax.BaseActivityModule;
import ui.HomeActivity;
import ui.dagger.LoginActivityModule;
import ui.dagger.LoginFragmentProvider;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {LoginActivityModule.class, LoginFragmentProvider.class, BaseActivityModule.class})
    @ActivityScope
    abstract HomeActivity bindDetailActivity();
}
