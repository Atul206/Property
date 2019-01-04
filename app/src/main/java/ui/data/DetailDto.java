package ui.data;

import android.graphics.Bitmap;

public class DetailDto {
    private String phoneNumber;
    private long createdDate;
    private String profilePicUrl;
    private String emailId;
    private String downloadUrl;
    private String name;
    String propertyId;
    String propertyName;
    String contactNo;
    int distance;
    String latitude;
    String longitude;
    String urlSignature;
    String urlPropertyImage;
    private String propertyAction;

    public DetailDto(PropertyDto propertyData) {
        this.propertyId = propertyData.getPropertyId();
        this.propertyName = propertyData.getPropertyName();
        this.contactNo = propertyData.getContactNo();
        this.distance = propertyData.getDistance();
        this.latitude = propertyData.getLatitude();
        this.longitude = propertyData.getLongitude();
        this.urlSignature = propertyData.getUrlSignature();
        this.urlPropertyImage = propertyData.getUrlPropertyImage();
        this.propertyAction = propertyData.getPropertyAction();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getName() {
        return name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailDto(String phoneNumber, long createdDate, String profilePicUrl, String emailId, String downloadUrl, String name) {
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.profilePicUrl = profilePicUrl;
        this.emailId = emailId;
        this.downloadUrl = downloadUrl;
        this.name = name;
    }

    public DetailDto() {
    }

}
