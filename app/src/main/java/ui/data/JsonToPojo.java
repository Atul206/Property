package ui.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JsonToPojo implements Serializable {
    @SerializedName("UNIQUE_PROPERTY_ID")
    @Expose
    String propertyID;

    @SerializedName("OWNER_NAME")
    @Expose
    String ownerName;

    @SerializedName("HTAX_NOTICE_ID")
    String contactDetail;


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
