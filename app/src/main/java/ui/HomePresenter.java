package ui;

import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import survey.property.roadster.com.surveypropertytax.BasePresenter;
import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;
import ui.data.PropertyData;
import ui.repo.PropertyRepository;

public class HomePresenter extends BasePresenter<HomeView> {

    private String searchStr;

    @Inject
    @Named("propertySearchString")
    PublishSubject<String> txtSearchObservable;

    @Inject
    PropertyRepository propertyRepository;

    @Inject
    public HomePresenter() {
        super();
    }

    @SuppressLint("CheckResult")
    public void load() {
        addCompositeDisposable(txtSearchObservable
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((query) -> {
                    searchStr = query;
                    loadSearch();
        }));

        addCompositeDisposable(propertyRepository.getAllProperties().subscribe( propertyDbObjectList  -> {
            view.updateList((PropertyData) propertyDbObjectList);
        }));
    }

    public void loadSearch(){
        propertyRepository.getSearchItem(searchStr).subscribe( propertyDbObjectList  -> {
            view.updateList(propertyDbObjectList);
        });
    }

    public void generateData() {
        propertyRepository.insert(new PropertyDbObject(1l, "Laxman villa", "27.2428", "72.2342" ));
    }

    @Override
    public void finish() {
        super.finish();
    }
}
