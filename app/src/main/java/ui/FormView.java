package ui;

import android.graphics.Bitmap;

import survey.property.roadster.com.surveypropertytax.BaseView;
import ui.data.PropertyData;

public interface FormView extends BaseView {
    public Bitmap getSignatureBitmap();
    public Bitmap getPhotoBitmap();

    void errorNetworkError();
}
