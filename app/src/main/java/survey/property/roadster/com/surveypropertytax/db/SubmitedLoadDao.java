package survey.property.roadster.com.surveypropertytax.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface SubmitedLoadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SubmitedLoadObject tripLoadDbObject);

    @Query("SELECT *from load where created_time BETWEEN :start AND :end")
    Flowable<List<SubmitedLoadObject>> getSubmitedLoadBetween(Long start, Long end);

    @Query("SELECT *from load")
    Flowable<List<SubmitedLoadObject>> getAllData();
}
