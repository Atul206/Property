package ui.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import di.FragmentScope;
import ui.fragment.FormFragment;
import ui.fragment.HomeFragment;
import ui.fragment.PropertyActionFragment;
import ui.fragment.PropertyScanFragment;

@Module
public abstract class SurevyFragmentProvider {

    @ContributesAndroidInjector(modules = {HomeFragmentModule.class})
    @FragmentScope
    abstract HomeFragment loginFragment();

    @ContributesAndroidInjector(modules = {FormFragmentModule.class})
    @FragmentScope
    abstract FormFragment formFragment();

    @ContributesAndroidInjector(modules = {PropertyActionFragmentModule.class})
    @FragmentScope
    abstract PropertyActionFragment propertyActionFragment();

    @ContributesAndroidInjector(modules = {PropertyScanFragmentModule.class})
    @FragmentScope
    abstract PropertyScanFragment propertyScanFragment();
}
