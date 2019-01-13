package survey.property.roadster.com.surveypropertytax.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.annotation.KeepName;

@Entity(tableName = "property")
@Keep
@KeepName
public class PropertyDbObject {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @NonNull
    @ColumnInfo(name = "property_id")
    private String propertyId;

    @Nullable
    @ColumnInfo(name = "name")
    private String propertyName;

    @Nullable
    @ColumnInfo(name = "contact_no")
    private String contactNo;

    @Nullable
    @ColumnInfo(name = "address")
    private String address;

    @Nullable
    @ColumnInfo(name = "latitude")
    private String latitude;

    @Nullable
    @ColumnInfo(name = "longitude")
    private String longitude;

    @Nullable
    @ColumnInfo(name = "url_signature")
    private String urlSignature;

    @Nullable
    @ColumnInfo(name = "url_property_image")
    private String urlPropertyImage;

    public PropertyDbObject(@NonNull String propertyId, @NonNull String propertyName, @NonNull String contactNo, @NonNull String address, @NonNull String latitude, @NonNull String longitude, String urlSignature,String urlPropertyImage) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.contactNo = contactNo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.urlSignature =  urlSignature;
        this.urlPropertyImage = urlPropertyImage;
        this.address = address;
    }


    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public int getUid() {
        return uid;
    }

    @NonNull
    public String getPropertyId() {
        return propertyId;
    }

    @NonNull
    public String getPropertyName() {
        return propertyName;
    }

    @NonNull
    public String getContactNo() {
        return contactNo;
    }

    @NonNull
    public String getLatitude() {
        return latitude;
    }

    @NonNull
    public String getLongitude() {
        return longitude;
    }

    @NonNull
    public String getUrlSignature() {
        return urlSignature;
    }

    @NonNull
    public String getUrlPropertyImage() {
        return urlPropertyImage;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setPropertyId(@NonNull String propertyId) {
        this.propertyId = propertyId;
    }

    public void setPropertyName(@NonNull String propertyName) {
        this.propertyName = propertyName;
    }

    public void setContactNo(@NonNull String contactNo) {
        this.contactNo = contactNo;
    }

    public void setLatitude(@NonNull String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(@NonNull String longitude) {
        this.longitude = longitude;
    }

    public void setUrlSignature(@NonNull String urlSignature) {
        this.urlSignature = urlSignature;
    }

    public void setUrlPropertyImage(@NonNull String urlPropertyImage) {
        this.urlPropertyImage = urlPropertyImage;
    }
}
