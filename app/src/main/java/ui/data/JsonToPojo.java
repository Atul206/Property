package ui.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JsonToPojo implements Serializable {
    @SerializedName("HTAX_PROPERTY_UNIT_ID")
    @Expose
    String propertyID;

    @SerializedName("OWNER_NAME")
    @Expose
    String ownerName;

    @SerializedName("Mobile number")
    String contactDetail;

    @SerializedName("FULL_ADDRESS")
    @Expose
    String address;

    @SerializedName("latitude")
    @Expose
    String latitude;

    @SerializedName("longitude")
    @Expose
    String longitude;

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getContactDetail() {
        return contactDetail;
    }
}
