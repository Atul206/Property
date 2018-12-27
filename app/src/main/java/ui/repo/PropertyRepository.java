package ui.repo;

import android.location.Location;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;
import survey.property.roadster.com.surveypropertytax.db.PropertyLoadDao;
import ui.data.PropertyListDto;

public class PropertyRepository {

    @NonNull
    PropertyLoadDao propertyLoadDao;
    private List<PropertyDbObject> allProperties;


    @Inject
    public PropertyRepository(PropertyLoadDao propertyLoadDao) {
        this.propertyLoadDao = propertyLoadDao;
    }

    public Flowable<PropertyListDto> getAllProperties() {
        return propertyLoadDao.getAllProperties()
                .subscribeOn(Schedulers.io())
                .map(PropertyListDto::new)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insert(PropertyDbObject propertyDbObject) {
        Completable.fromRunnable(() -> propertyLoadDao.insert(propertyDbObject))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {});
    }

    public void insertAll(List<PropertyDbObject> propertyDbObject) {
        Completable.fromRunnable(() -> propertyLoadDao.insertAll(propertyDbObject))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {});
    }

    public Flowable<PropertyListDto> getSearchItem(String search) {
        return propertyLoadDao.getSearchProperties(search)
                .subscribeOn(Schedulers.io())
                .map(PropertyListDto::new)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Integer> isEmpty() {
        return propertyLoadDao.isEmpty()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
