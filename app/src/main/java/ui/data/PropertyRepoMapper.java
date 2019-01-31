package ui.data;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;

public class PropertyRepoMapper {
    List<PropertyDbObject> propertyDbObjectList = new ArrayList<>();
    JsonFileList jsonFileList;

    @SuppressLint("CheckResult")
    public PropertyRepoMapper(JsonFileList jsonFileListss) {
        this.jsonFileList = jsonFileListss;
        Observable.fromCallable(() -> {
            this.jsonFileList = jsonFileListss;
            return jsonFileListss;
        }).observeOn(Schedulers.computation()
        ).subscribeOn(Schedulers.computation()
        ).subscribe(__ -> {
            proccessData();
        });
    }

    void proccessData() {
        if (jsonFileList.getSheet1() != null)
            for (JsonToPojo p : jsonFileList.getSheet1()) {
                if (p.getPropertyID() != null) {
                    propertyDbObjectList.add(new PropertyDbObject(p.getPropertyID(), p.getOwnerName(), p.getContactDetail(), p.getAddress(), p.getLatitude(), p.getLongitude(), null, null, false));
                }
            }
    }

    public List<PropertyDbObject> getPropertyDbObjectList() {
        return propertyDbObjectList;
    }
}
