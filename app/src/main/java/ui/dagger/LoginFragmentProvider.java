package ui.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import di.FragmentScope;
import ui.fragment.LoginFragment;

@Module
public abstract class LoginFragmentProvider {

    @ContributesAndroidInjector(modules = {LoginFragmentModule.class})
    @FragmentScope
    abstract LoginFragment loginFragment();
}
