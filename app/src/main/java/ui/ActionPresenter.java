package ui;

import javax.inject.Inject;

import survey.property.roadster.com.surveypropertytax.BasePresenter;
import ui.data.PropertyActionList;
import ui.data.PropertyData;
import ui.data.PropertyDto;

public class ActionPresenter extends BasePresenter<ActionView> {

    private PropertyDto propertyData;

    @Inject
    public ActionPresenter() {
        super();
    }

    public void setPropertyData(PropertyDto propertyData) {
        this.propertyData = propertyData;
    }

    public PropertyDto getPropertyData() {
        return propertyData;
    }

    public void load(){
        view.addItemInList(new PropertyActionList());
    }
}
