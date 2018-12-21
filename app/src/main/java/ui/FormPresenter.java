package ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import survey.property.roadster.com.surveypropertytax.BasePresenter;
import survey.property.roadster.com.surveypropertytax.CommonUtil;
import survey.property.roadster.com.surveypropertytax.PApplication;
import survey.property.roadster.com.surveypropertytax.db.PropertyDbObject;
import ui.data.DetailDto;
import ui.data.PropertyData;
import ui.data.PropertyDto;
import ui.repo.PropertyRepository;

import static android.support.constraint.Constraints.TAG;

public class FormPresenter extends BasePresenter<FormView> {

    private PropertyDto propertyData;

    @Inject
    StorageReference storageReference;

    @Inject
    DatabaseReference databaseReference;
    private Location lastLocation;

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
        propertyData.setSingatureBitmap(view.getSignatureBitmap());
        propertyData.setPhotoBitmap(view.getPhotoBitmap());
        PApplication.getInstance().addOfflinePropertyItem(propertyData);
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Location getLastLocation() {
        return lastLocation;
    }
}
