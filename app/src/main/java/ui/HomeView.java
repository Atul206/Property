package ui;

import survey.property.roadster.com.surveypropertytax.BaseView;
import ui.data.PropertyData;

public interface HomeView extends BaseView {
    void updateList(PropertyData propertyData);
}
