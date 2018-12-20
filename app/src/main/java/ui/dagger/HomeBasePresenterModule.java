package ui.dagger;

import dagger.Binds;
import dagger.Module;
import survey.property.roadster.com.surveypropertytax.BasePresenter;
import ui.FormPresenter;
import ui.HomePresenter;

@Module
public abstract class HomeBasePresenterModule {

    @Binds
    abstract BasePresenter homeBasePresenter(HomePresenter homePresenter);

    @Binds
    abstract BasePresenter formBasePresenter(FormPresenter homePresenter);
}
