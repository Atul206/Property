package ui.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JsonToPojo implements Serializable {
    @SerializedName("unique_property_id")
    @Expose
    String propertyID;

    @SerializedName("owenr_name")
    @Expose
    String ownerName;

    @SerializedName("mobile_no")
    String contactDetail;

    @SerializedName("postal_add")
    @Expose
    String address;

    @SerializedName("new_postal_add")
    @Expose
    String newAddress;

    @SerializedName("latitude")
    @Expose
    String latitude;

    @SerializedName("longitude")
    @Expose
    String longitude;

    public String getNewAddress() {
        return newAddress;
    }

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
