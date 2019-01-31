package ui.data;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.Serializable;

import ui.LocationUtil.LocationHelper;

public class PropertyDto implements Serializable {
    int uid;
    String propertyId;
    String propertyName;
    String contactNo;
    int distance;
    String latitude;
    String longitude;
    String address;
    String urlSignature;
    String urlPropertyImage;
    private Bitmap singatureBitmap;
    private Bitmap photoBitmap;
    private String propertyAction;
    private String permanentAddress;
    private String remarks;
    private String propertyEmail;
    private String newContact;
    private String reason;
    private Boolean isActionTaken = false;

    public PropertyDto() {
    }

    public PropertyDto(int uid, String propertyId, String propertyName, String contactNo, String address, String latitude, String longitude, String urlSignature, String urlPropertyImage, Boolean isActionTaken) {
        this.uid = uid;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.contactNo = contactNo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.urlSignature = urlSignature;
        this.urlPropertyImage = urlPropertyImage;
        this.address = address;
        this.distance = 0;
        this.isActionTaken = isActionTaken;
    }

    public String getPropertyName() {
        return (propertyName == null || propertyName.length() == 0) ? " - " : propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyId() {
        return (propertyId == null || propertyId.length() == 0) ? " - " : propertyId;
    }

    public void setPropertyId(String propertyId) {
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

    public void setSingatureBitmap(Bitmap singatureBitmap) {
        this.singatureBitmap = singatureBitmap;
    }

    public void setPhotoBitmap(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }

    public Bitmap getSingatureBitmap() {
        return singatureBitmap;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public String getPropertyAction() {
        return propertyAction;
    }

    public void setPropertyAction(String propertyAction) {
        this.propertyAction = propertyAction;
    }

    public String getAddress() {
        return (address == null || address.length() == 0) ? " - " : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPropertyEmail() {
        return propertyEmail;
    }

    public void setPropertyEmail(String propertyEmail) {
        this.propertyEmail = propertyEmail;
    }

    public String getNewContact() {
        return newContact;
    }

    public void setNewContact(String newContact) {
        this.newContact = newContact;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Boolean getActionTaken() {
        return isActionTaken;
    }

    public void setActionTaken(Boolean actionTaken) {
        isActionTaken = actionTaken;
    }


}
