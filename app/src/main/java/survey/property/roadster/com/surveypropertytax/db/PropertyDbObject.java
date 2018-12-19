package survey.property.roadster.com.surveypropertytax.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;

import com.google.android.gms.common.annotation.KeepName;

@Entity(tableName = "property")
@Keep
@KeepName
public class PropertyDbObject {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @NonNull
    @ColumnInfo(name = "property_id")
    private Long propertyId;

    @NonNull
    @ColumnInfo(name = "name")
    private String propertyName;

    @NonNull
    @ColumnInfo(name = "latitude")
    private String latitude;

    @NonNull
    @ColumnInfo(name = "longitude")
    private String longitude;

    public PropertyDbObject(@NonNull Long propertyId, @NonNull String propertyName, @NonNull String latitude, @NonNull String longitude) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public int getUid() {
        return uid;
    }

    @NonNull
    public Long getPropertyId() {
        return propertyId;
    }

    @NonNull
    public String getPropertyName() {
        return propertyName;
    }

    @NonNull
    public String getLatitude() {
        return latitude;
    }

    @NonNull
    public String getLongitude() {
        return longitude;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setPropertyId(@NonNull Long propertyId) {
        this.propertyId = propertyId;
    }

    public void setPropertyName(@NonNull String propertyName) {
        this.propertyName = propertyName;
    }

    public void setLatitude(@NonNull String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(@NonNull String longitude) {
        this.longitude = longitude;
    }
}
