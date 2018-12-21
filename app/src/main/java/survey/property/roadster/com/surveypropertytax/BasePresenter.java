package survey.property.roadster.com.surveypropertytax;

import android.location.Location;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import survey.property.roadster.com.surveypropertytax.db.PropertyLoadDao;
import ui.LocationUtil.LocationHelper;

public class BasePresenter<T extends BaseView> {

    @Inject
    public T view;

    private CompositeDisposable compositeDisposable;

    public BasePresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    protected void addCompositeDisposable(Disposable... compositeDisposabl){
        compositeDisposable.addAll(compositeDisposabl);
    }

    public CompositeDisposable getDisposables() {
        return compositeDisposable;
    }

    public void finish() {
        compositeDisposable.clear();
    }
}
