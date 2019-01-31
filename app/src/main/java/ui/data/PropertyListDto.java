package ui.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;

public class PropertyListDto implements PropertyData {

    List<PropertyDto> propertyDtoList;
    List<PropertyDbObject> propertyDbObjects;

    public PropertyListDto(List<PropertyDbObject> propertyDbObjects) {
        this.propertyDbObjects = propertyDbObjects;
        propertyDtoList = new ArrayList<>();
        processData();
    }



    @Override
    public void processData(){
        for(PropertyDbObject p: propertyDbObjects) {
            if(!p.getPropertyId().equals("unique_property_id")) {
                propertyDtoList.add(new PropertyDto(p.getUid(), p.getPropertyId(), p.getPropertyName(), p.getContactNo(), p.getAddress(), p.getLatitude(), p.getLongitude(), p.getUrlSignature(), p.getUrlPropertyImage(), p.getActionTaken()));
            }
        }
        Collections.sort(propertyDtoList, new SortByDistance());
    }

    @Override
    public List<PropertyDto> getItem() {
        return propertyDtoList;
    }

    class SortByDistance implements Comparator<PropertyDto> {

        @Override
        public int compare(PropertyDto o1, PropertyDto o2) {
            return o1.getDistance() - o2.getDistance();
        }
    }
}
