package ui.data;

import java.util.ArrayList;
import java.util.List;

public class PropertyActionList implements PropertyData {

    List<String> propertyActions;

    public PropertyActionList() {
        propertyActions = new ArrayList<>();
        processData();
    }

    @Override
    public void processData() {
        propertyActions.add("Property Tax Bill Delivery");
        propertyActions.add("Development Charges Notice Delivery");
        propertyActions.add("Lodging Charges Notice Delivery");
        propertyActions.add("Property Tax Recovery Notice Delivery");
        propertyActions.add("Property Sealing Notice Delivery");
        propertyActions.add("Property Auction Notice Delivery");
    }

    @Override
    public List<String> getItem() {
        return propertyActions;
    }
}
