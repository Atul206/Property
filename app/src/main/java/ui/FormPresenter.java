package ui;

import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import survey.property.roadster.com.surveypropertytax.BasePresenter;
import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;
import ui.data.PropertyData;
import ui.repo.PropertyRepository;

public class FormPresenter extends BasePresenter<FormView> {

    @Inject
    public FormPresenter() {
        super();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
