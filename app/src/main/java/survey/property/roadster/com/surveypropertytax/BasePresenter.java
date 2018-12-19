package survey.property.roadster.com.surveypropertytax;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import survey.property.roadster.com.surveypropertytax.db.PropertyLoadDao;

public class BasePresenter<T extends BaseView> {

    @Inject
    public T view;

    public BasePresenter() {
    }
}
