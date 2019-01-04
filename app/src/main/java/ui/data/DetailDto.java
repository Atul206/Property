package ui.data;

import java.util.HashMap;
import java.util.Map;

public class DetailDto{
    private String phoneNumber;
    private long createdDate;
    private String profilePicUrl;
    private String emailId;
    private String downloadUrl;
    private String name;

    Map<String, String> data;

    public DetailDto(PropertyDto propertyData) {

        data = new HashMap<>();
        data.put("propertyId", propertyData.getPropertyId());
        data.put("propertyName", propertyData.getPropertyName());
        data.put("contactNo", propertyData.getContactNo());
        data.put("distance", String.valueOf(propertyData.getDistance()));
        data.put("latitude", propertyData.getLatitude());
        data.put("longitude", propertyData.getLongitude());
        data.put("urlSignature", propertyData.getUrlSignature());
        data.put("urlPropertyImage", propertyData.getUrlPropertyImage());
        data.put("propertyAction", propertyData.getPropertyAction());
        data.put("propertyAddress", propertyData.getAddress());
        data.put("propertyEmailId", propertyData.getPropertyEmail());
        data.put("propertyPermanentAddress", propertyData.getPropertyEmail());
        data.put("propertyRemark", propertyData.getRemarks());
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

    public Map<String, String> getData() {
        return data;
    }
}
