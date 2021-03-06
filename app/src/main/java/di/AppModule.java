package di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import survey.property.roadster.com.surveypropertytax.db.AppDatabase;
import survey.property.roadster.com.surveypropertytax.db.PropertyLoadDao;

@Singleton
@Module
public class AppModule {

    @Provides
    @Singleton
    @Named("ApplicationContext")
    Context context(Application application){
        return application;
    }

    @Singleton
    @Provides
    AppDatabase appDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "property-database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    PropertyLoadDao propertyLoadDao(AppDatabase appDatabase) {
        return appDatabase.propertyLoadDao();
    }


    @Provides
    @Singleton
    FirebaseJobDispatcher firebaseJobDispatcher(Application application){
        return  new FirebaseJobDispatcher(new GooglePlayDriver(application));
    }


    @Provides
    @Singleton
    StorageReference firebaseStorage(){
        return FirebaseStorage.getInstance().getReference();
    }
}
