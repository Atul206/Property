package ui.data;

import java.util.ArrayList;
import java.util.List;

import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;

public class PropertyRepoMapper {
    List<PropertyDbObject> propertyDbObjectList = new ArrayList<>();
    JsonFileList jsonFileList;

    public PropertyRepoMapper(JsonFileList jsonFileList) {
        this.jsonFileList = jsonFileList;
        proccessData();
    }

    void proccessData(){
        if(jsonFileList.getSheet1() != null) {
            for (JsonToPojo p : jsonFileList.getSheet1()) {
                propertyDbObjectList.add(new PropertyDbObject(p.getPropertyID(), p.getOwnerName(), p.getContactDetail(), p.getAddress(), p.getLatitude(), p.getLongitude(), null, null));
            }
        }
    }

    public List<PropertyDbObject> getPropertyDbObjectList() {
        return propertyDbObjectList;
    }
}
