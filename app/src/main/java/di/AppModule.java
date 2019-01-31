package di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import survey.property.roadster.com.surveypropertytax.PApplication;
import survey.property.roadster.com.surveypropertytax.db.AppDatabase;
import survey.property.roadster.com.surveypropertytax.db.PropertyLoadDao;
import survey.property.roadster.com.surveypropertytax.db.SubmitedLoadDao;
import ui.LocationUtil.LocationHelper;

@Singleton
@Module
public class AppModule {

    @Provides
    @Singleton
    @Named("ApplicationContext")
    Context context(Application application){
        return application;
    }

    @Provides
    @Singleton
    PApplication pApplication(PApplication application){ return application;}

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
    SubmitedLoadDao submitedLoadDao(AppDatabase appDatabase) {
        return appDatabase.submitedLoadDao();
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

    @Provides
    @Singleton
    DatabaseReference databaseReference(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("property_survey");
        return myRef;
    }


}
