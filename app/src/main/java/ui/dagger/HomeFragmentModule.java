package ui.dagger;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import di.FragmentScope;
import io.reactivex.subjects.PublishSubject;
import survey.property.roadster.com.surveypropertytax.BaseView;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragmentModule;
import ui.HomePresenter;
import ui.HomeView;
import ui.adapter.LoadingAdapter;
import ui.data.PropertyData;
import ui.fragment.HomeFragment;

@Module(includes = {SurveyBaseFragmentModule.class, HomeBasePresenterModule.class})
public abstract class HomeFragmentModule {

    @Binds
    abstract SurveyBaseFragment loginFragment(HomeFragment homeFragment);

    @Binds
    abstract LoadingAdapter.AdapterClickCallback<PropertyData> filteredCallback(HomeFragment homeFragment);

    @Binds
    abstract HomeView baseView(HomeFragment homeFragment);

    @Provides
    @FragmentScope
    @Named("propertySearchString")
    public static PublishSubject<String> textSearchObservable() {
        return PublishSubject.create();
    }


    /*@Binds
    abstract HomeView loginView(HomeFragment loginFragment);*/
}
