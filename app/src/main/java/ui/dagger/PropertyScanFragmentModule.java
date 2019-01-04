package ui.dagger;

import dagger.Binds;
import dagger.Module;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragmentModule;
import ui.ScanView;
import ui.fragment.PropertyScanFragment;

@Module(includes = {SurveyBaseFragmentModule.class, HomeBasePresenterModule.class})
public abstract class PropertyScanFragmentModule {

    @Binds
    abstract SurveyBaseFragment propertyScanFragment(PropertyScanFragment propertyScanFragment);

    @Binds
    abstract ScanView scanView(PropertyScanFragment propertyScanFragment);
}
