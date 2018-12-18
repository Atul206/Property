package survey.property.roadster.com.surveypropertytax;

import android.app.Activity;
import android.content.Context;

import dagger.Binds;
import dagger.Module;
import di.ActivityScope;

@Module
public abstract class BaseActivityModule {
    @Binds
    @ActivityScope
    abstract Context context(SurveyBaseActivity baseActivity);

    @Binds
    @ActivityScope
    abstract Activity activity(SurveyBaseActivity baseActivity);
}
