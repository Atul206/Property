package ui.dagger;

import dagger.Binds;
import dagger.Module;
import survey.property.roadster.com.surveypropertytax.BasePresenter;
import ui.ActionPresenter;
import ui.FormPresenter;
import ui.HomePresenter;
import ui.ScanPresenter;
import ui.fragment.PropertyScanFragment;

@Module
public abstract class HomeBasePresenterModule {

    @Binds
    abstract BasePresenter homeBasePresenter(HomePresenter homePresenter);

    @Binds
    abstract BasePresenter formBasePresenter(FormPresenter formPresenter);

    @Binds
    abstract BasePresenter actionPresenter(ActionPresenter actionPresenter);

    @Binds
    abstract BasePresenter scanPresenter(ScanPresenter scanPresenter);
}
