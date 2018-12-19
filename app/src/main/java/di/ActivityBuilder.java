package di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import survey.property.roadster.com.surveypropertytax.BaseActivityModule;
import ui.HomeActivity;
import ui.dagger.HomeActivityModule;
import ui.dagger.SurevyFragmentProvider;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {HomeActivityModule.class, SurevyFragmentProvider.class, BaseActivityModule.class})
    @ActivityScope
    abstract HomeActivity bindDetailActivity();
}
