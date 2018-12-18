package ui.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import di.FragmentScope;
import ui.fragment.HomeFragment;

@Module
public abstract class LoginFragmentProvider {

    @ContributesAndroidInjector(modules = {LoginFragmentModule.class})
    @FragmentScope
    abstract HomeFragment loginFragment();
}
