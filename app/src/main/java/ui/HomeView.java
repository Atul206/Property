package ui;

import java.util.List;

import survey.property.roadster.com.surveypropertytax.BaseView;
import survey.property.roadster.com.surveypropertytax.db.SubmitedLoadObject;
import ui.data.PropertyData;

public interface HomeView extends BaseView {
    void updateList(PropertyData propertyData);
    void searchList(PropertyData propertyData);

    void readData(String uid);

    void updateTodayCount(int count, int totalCount);
}
