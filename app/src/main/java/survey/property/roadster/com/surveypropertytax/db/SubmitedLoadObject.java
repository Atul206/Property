package survey.property.roadster.com.surveypropertytax.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.annotation.KeepName;

import java.util.List;

@Entity(tableName = "load")
@Keep
@KeepName
public class SubmitedLoadObject {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @NonNull
    @ColumnInfo
    private String property_db_id;

    @NonNull
    @ColumnInfo(name = "action")
    private Boolean action;

    @NonNull
    @ColumnInfo(name = "created_time")
    private Long createdTime;

    public SubmitedLoadObject(@NonNull String property_db_id, @NonNull Boolean action, Long createdTime) {
        this.property_db_id = property_db_id;
        this.action = action;
        this.createdTime = createdTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @NonNull
    public String getProperty_db_id() {
        return property_db_id;
    }

    public void setProperty_db_id(@NonNull String property_db_id) {
        this.property_db_id = property_db_id;
    }

    @NonNull
    public Boolean getAction() {
        return action;
    }

    public void setAction(@NonNull Boolean action) {
        this.action = action;
    }

    @NonNull
    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(@Nullable Long createdTime) {
        this.createdTime = createdTime;
    }
}
