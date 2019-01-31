package survey.property.roadster.com.surveypropertytax.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PropertyDbObject.class, SubmitedLoadObject.class}, version = 13, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PropertyLoadDao propertyLoadDao();
    public abstract SubmitedLoadDao submitedLoadDao();
}
