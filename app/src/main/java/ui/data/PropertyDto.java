package ui.data;

import android.location.Location;

import java.io.Serializable;

public class PropertyDto implements Serializable {
    long propertyId;
    String propertyName;
    int distance;
    String latitude;
    String longitude;

    public PropertyDto(long propertyId, String propertyName,String latitude, String longitude) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = calculateDistance();
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    private int calculateDistance(){
        Location l2 = new Location(("end"));
        l2.setLatitude(Double.valueOf(latitude));
        l2.setLongitude(Double.valueOf(longitude));
        return 12;//(int) new Location("start").distanceTo(l2);
    }
}
