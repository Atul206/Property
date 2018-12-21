package ui.data;

import android.location.Location;

import java.io.Serializable;

public class PropertyDto implements Serializable {
    long propertyId;
    String propertyName;
    String contactNo;
    int distance;
    String latitude;
    String longitude;
    String urlSignature;
    String urlPropertyImage;

    public PropertyDto(long propertyId, String propertyName, String contactNo,String latitude, String longitude, String urlSignature, String urlPropertyImage) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.contactNo = contactNo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.urlSignature = urlSignature;
        this.urlPropertyImage = urlPropertyImage;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUrlSignature() {
        return urlSignature;
    }

    public void setUrlSignature(String urlSignature) {
        this.urlSignature = urlSignature;
    }

    public String getUrlPropertyImage() {
        return urlPropertyImage;
    }

    public void setUrlPropertyImage(String urlPropertyImage) {
        this.urlPropertyImage = urlPropertyImage;
    }

    private int calculateDistance(){
        Location l2 = new Location(("end"));
        l2.setLatitude(Double.valueOf(latitude));
        l2.setLongitude(Double.valueOf(longitude));
        return 12;//(int) new Location("start").distanceTo(l2);
    }
}
