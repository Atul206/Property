package ui;

import android.location.Location;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;

import survey.property.roadster.com.surveypropertytax.BasePresenter;
import survey.property.roadster.com.surveypropertytax.PApplication;
import ui.data.PropertyDto;
import ui.enums.TagType;

public class FormPresenter extends BasePresenter<FormView> {

    private PropertyDto propertyData;

    @Inject
    StorageReference storageReference;

    @Inject
    DatabaseReference databaseReference;
    private Location lastLocation;
    private TagType tagType;

    @Inject
    public FormPresenter() {
        super();
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void setPropertyData(PropertyDto propertyData) {
        this.propertyData = propertyData;
    }

    public PropertyDto getPropertyData() {
        return propertyData;
    }

    public void registerOfflineFile() {
        switch (tagType){
            case ADD:
                break;
            default:
                if(view.getSignatureBitmap() != null) {
                    propertyData.setSingatureBitmap(view.getSignatureBitmap());
                }
                if(view.getPhotoBitmap() != null) {
                    propertyData.setPhotoBitmap(view.getPhotoBitmap());
                }
        }
        view.getApplicationInstance().addOfflinePropertyItem(propertyData);
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public TagType getTagType() {
        return tagType;
    }
}
