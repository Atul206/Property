package ui.dagger;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import di.FragmentScope;
import io.reactivex.subjects.PublishSubject;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragmentModule;
import ui.FormView;
import ui.HomeView;
import ui.adapter.LoadingAdapter;
import ui.data.PropertyData;
import ui.fragment.FormFragment;
import ui.fragment.HomeFragment;

@Module(includes = {SurveyBaseFragmentModule.class, HomeBasePresenterModule.class})
public abstract class FormFragmentModule {

    @Binds
    abstract SurveyBaseFragment formFragment(FormFragment formFragment);

    @Binds
    abstract FormView baseView(FormFragment formFragment);
}
