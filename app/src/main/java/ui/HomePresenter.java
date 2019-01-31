package ui;

import android.annotation.SuppressLint;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import survey.property.roadster.com.surveypropertytax.BasePresenter;
import survey.property.roadster.com.surveypropertytax.db.SubmitedLoadObject;
import ui.data.PropertyData;
import ui.repo.LoadRepository;
import ui.repo.PropertyRepository;

public class HomePresenter extends BasePresenter<HomeView> {

    private String searchStr;
    private int mCurrentPage = 0;

    @Inject
    @Named("propertySearchString")
    PublishSubject<String> txtSearchObservable;

    @Inject
    PropertyRepository propertyRepository;

    @Inject
    LoadRepository loadRepository;

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

        addCompositeDisposable(propertyRepository.getPropertyAfterUid(mCurrentPage).subscribe(propertyDbObjectList -> {
            view.updateList((PropertyData) propertyDbObjectList);
        }));

        addCompositeDisposable(loadRepository.getAllData().subscribe(loadDb -> {
            DateTime dateTime = DateTime.now();
            long startDate = dateTime.minusMinutes(23).getMillis();
            long endDate = dateTime.getMillis();
            Set<String> uniqueSet = new HashSet<>();
            Set<String> uniqueTotalSet = new HashSet<>();
            for (SubmitedLoadObject s : loadDb) {
                uniqueTotalSet.add(s.getProperty_db_id());
                if(s.getCreatedTime() >= startDate && s.getCreatedTime() <= endDate)
                    uniqueSet.add(s.getProperty_db_id());
            }
            view.updateTodayCount(uniqueSet.size(), uniqueTotalSet.size());
        }));

    }

    public void loadSearch() {
        propertyRepository.getSearchItem(searchStr).observeOn(Schedulers.computation()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(propertyDbObjectList -> {
            view.searchList(propertyDbObjectList);
        });
    }

    public void generateData() {
        //view.readData(searchStr);
        /*List<PropertyDbObject> propertyDbObjectList = view.getApplicationInstance().getPropertyDbObjects();
        if (propertyDbObjectList == null) {
            return;
        }*/
        /*propertyRepository.isEmpty().delay(1000, TimeUnit.MILLISECONDS).subscribe(i -> {
            if (i == null || i == 0) {
                //propertyRepository.insertAll(view.getApplicationInstance().getPropertyDbObjects());
            }
        });*/
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void onPageScrolled() {
        propertyRepository.getPropertyAfterUid(mCurrentPage).subscribe(propertyDbObjectList -> {
            view.updateList((PropertyData) propertyDbObjectList);
        });
    }

    public void reset() {
        mCurrentPage = 0;
    }

    public void updateCurrentPage(int uid) {
        mCurrentPage = uid;
    }
}
