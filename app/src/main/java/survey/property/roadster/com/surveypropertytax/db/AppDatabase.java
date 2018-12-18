package survey.property.roadster.com.surveypropertytax.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PropertyDbObject.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PropertyLoadDao propertyLoadDao();
}
