package ui.repo;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import survey.property.roadster.com.surveypropertytax.db.SubmitedLoadDao;
import survey.property.roadster.com.surveypropertytax.db.SubmitedLoadObject;

public class LoadRepository {

    @NonNull
    SubmitedLoadDao submitedLoadDao;

    @Inject
    public LoadRepository(@NonNull SubmitedLoadDao submitedLoadDao) {
        this.submitedLoadDao = submitedLoadDao;
    }

    public void insert(SubmitedLoadObject loadObject){
        try {
            Completable.fromRunnable(() -> submitedLoadDao.insert(loadObject))
                    .subscribeOn(Schedulers.io())
                    .subscribe(() -> {
                    });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Flowable<List<SubmitedLoadObject>> getLoadObject(Long start, Long end){
        return submitedLoadDao.getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Flowable<List<SubmitedLoadObject>> getAllData(){
        return submitedLoadDao.getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
