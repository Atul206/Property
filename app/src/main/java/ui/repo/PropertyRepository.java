package ui.repo;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;
import survey.property.roadster.com.surveypropertytax.db.PropertyLoadDao;
import ui.data.PropertyListDto;

import static android.support.constraint.Constraints.TAG;

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

    public Flowable<PropertyListDto> getPropertyAfterUid(int uid) {
        return propertyLoadDao.getPropertyAfter(uid)
                .subscribeOn(Schedulers.io())
                .map(PropertyListDto::new)
                .observeOn(Schedulers.io());
    }

    public void update(int uid, Boolean flag){
        try{
            Completable.fromRunnable(() -> {
                propertyLoadDao.updateData(uid, flag);
                Log.d(TAG, "update: " + flag);
            })
                    .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                    .subscribe( () -> {

                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insert(PropertyDbObject propertyDbObject) {
        try {
            Completable.fromRunnable(() -> propertyLoadDao.insert(propertyDbObject))
                    .subscribeOn(Schedulers.io())
                    .subscribe(() -> {
                    });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insertAll(List<PropertyDbObject> propertyDbObject) {
        try {
            Completable.fromRunnable(() -> {
                try {
                    propertyLoadDao.insertAll(propertyDbObject);
                }catch (Exception e){
                    e.printStackTrace();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .subscribe(() -> {
                    });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Flowable<PropertyListDto> getSearchItem(String search) {
        return propertyLoadDao.getSearchProperties(search)
                .subscribeOn(Schedulers.io())
                .map(PropertyListDto::new)
                .observeOn(Schedulers.io());
    }

    public Flowable<Integer> isEmpty() {
        return propertyLoadDao.isEmpty()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Single<PropertyDbObject> getPropertyOfUid(int uid) {
        return propertyLoadDao.getAProperty(uid).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
    }
}
