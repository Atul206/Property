package ui.dagger;


import dagger.Binds;
import dagger.Module;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragmentModule;
import ui.ActionView;
import ui.adapter.LoadingAdapter;
import ui.data.PropertyData;
import ui.fragment.PropertyActionFragment;

@Module(includes = {SurveyBaseFragmentModule.class, HomeBasePresenterModule.class})
public abstract class PropertyActionFragmentModule {
    @Binds
    abstract SurveyBaseFragment propertyAction(PropertyActionFragment homeFragment);

    @Binds
    abstract LoadingAdapter.AdapterClickCallback<PropertyData> filteredCallback(PropertyActionFragment homeFragment);

    @Binds
    abstract ActionView baseView(PropertyActionFragment homeFragment);
}
