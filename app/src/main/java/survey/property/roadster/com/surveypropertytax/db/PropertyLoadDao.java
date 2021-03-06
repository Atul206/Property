package survey.property.roadster.com.surveypropertytax.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface PropertyLoadDao {

    @Query("SELECT *from property where uid not null")
    Flowable<List<PropertyDbObject>> getAllProperties();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PropertyDbObject> propertyDbObjects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PropertyDbObject... tripLoadDbObjects);

    @Delete
    void delete(PropertyDbObject tripLoadDbObject);

    @Update
    void update(PropertyDbObject... tripLoadDbObjects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PropertyDbObject tripLoadDbObject);

    @Query("SELECT *from property where name like '%' || :arg || '%' or " +
            "property_id like '%' || :arg || '%'")
    Flowable<List<PropertyDbObject>> getSearchProperties(String arg);
}
