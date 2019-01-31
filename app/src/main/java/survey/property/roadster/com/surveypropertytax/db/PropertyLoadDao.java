package survey.property.roadster.com.surveypropertytax.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


@Dao
public interface PropertyLoadDao {

    @Query("SELECT *from property where uid not null")
    Flowable<List<PropertyDbObject>> getAllProperties();

    @Query("SELECT *from property where uid not null and uid > :uid order by uid limit 10")
    Flowable<List<PropertyDbObject>> getPropertyAfter(int uid);

    @Query("SELECT *from property where uid not null and uid = :id")
    Single<PropertyDbObject> getAProperty(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PropertyDbObject> propertyDbObjects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PropertyDbObject... propertyDbObjects);

    @Delete
    void delete(PropertyDbObject tripLoadDbObject);

    @Query("UPDATE property SET is_action_taken = :flag WHERE uid = :uid")
    void updateData(int uid, Boolean flag);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PropertyDbObject tripLoadDbObject);

    @Query("SELECT count(*) from property")
    Flowable<Integer> isEmpty();

    @Query("SELECT *from property where property_id like '%' || :arg || '%'")
    Flowable<List<PropertyDbObject>> getSearchProperties(String arg);
}
