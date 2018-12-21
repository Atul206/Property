package ui.data;

import java.io.Serializable;

public class DetailDto implements Serializable {
    private PropertyDto data;
    private String phoneNumber;
    private long createdDate;
    private String profilePicUrl;
    private String emailId;
    private String downloadUrl;
    private String name;

    public PropertyDto getData() {
        return data;
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

    public void setData(PropertyDto data) {
        this.data = data;
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

    public DetailDto(PropertyDto data, String phoneNumber, long createdDate, String profilePicUrl, String emailId, String downloadUrl, String name) {
        this.data = data;
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
