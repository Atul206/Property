package ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import di.FragmentScope;
import survey.property.roadster.com.surveypropertytax.BaseIntranction;
import survey.property.roadster.com.surveypropertytax.R;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.FormPresenter;
import ui.FormView;
import ui.LocationUtil.LocationHelper;
import ui.data.PropertyDto;

import static android.app.Activity.RESULT_OK;

@FragmentScope
public class FormFragment extends SurveyBaseFragment<FormPresenter, FormFragment.LoginIntraction>
        implements FormView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    @BindView(R.id.signaturePad)
    SignaturePad signaturePad;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.edit_property_name)
    EditText editPropertyName;
    @BindView(R.id.edit_property_contact_no)
    EditText editPropertyContactNo;
    @BindView(R.id.btn_save_form)
    Button btnSaveForm;

    public static String SURVEY_PIC = "survey_pic";
    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_STORAGE = 2;

    private Uri fileUri;
    private File actualImageFile;
    private Bitmap imageBitmap;

    private static final int THUMBNAIL_WIDTH = 400;
    private static final int THUMBNAIL_HEIGHT = 150;

    private Location mLastLocation;

    double latitude;
    double longitude;

    LocationHelper locationHelper;

    @Inject
    StorageReference storageReference;


    @Override
    public void preInit() {
        super.preInit();
        //Bundle bundle = getArguments();
        //bundle.getString(R.string)
    }

    @Override
    protected void initListener() {
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                Bitmap bitmap = signaturePad.getSignatureBitmap();
                if (bitmap != null) {
                    Toast.makeText(getActivity(), "Signature captured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });
    }

    @Override
    public void postInit() {
        super.postInit();
        locationHelper = new LocationHelper(getContext());
        locationHelper.checkpermission();
        // check availability of play services
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient(this, this);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_form;
    }

    @Override
    public void initLayout() {
    }

    @NonNull
    @Override
    protected Class<LoginIntraction> getListenerClass() {
        return LoginIntraction.class;
    }

    @Override
    protected SwipeRefreshLayout getSwipRefreshView() {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Once connected with google api, get the location
        mLastLocation = locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        locationHelper.connectApiClient();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public interface LoginIntraction extends BaseIntranction {
        void gotoFormFragment(PropertyDto data);
    }

    @Override
    public void onDestroyView() {
        mPresenter.finish();
        super.onDestroyView();
    }

    @OnClick(R.id.btn_save_form)
    public void submitForm() {
        mLastLocation = locationHelper.getLocation();

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            showToast("latitude : " + String.valueOf(latitude) + " longitide : " + String.valueOf(longitude));
        } else {

            if (btnSaveForm.isEnabled())
                btnSaveForm.setEnabled(false);

            showToast("Couldn't get the location. Make sure location is enabled on the device");
        }

        uploadSignature();
        uploadPropertyPic();
    }

    private void uploadPropertyPic() {
        StorageReference propertyImagesRef = storageReference.child("images/signatures/sample.jpg");

        Bitmap bitmap = signaturePad.getSignatureBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = propertyImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("PIC",exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.i("PIC",taskSnapshot.getMetadata().getPath());
            }
        });
    }

    private void uploadSignature() {
        StorageReference signatureImagesRef = storageReference.child("images/signatures/sample.jpg");


        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = signatureImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("PIC",exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.i("PIC",taskSnapshot.getMetadata().getPath());
            }
        });
    }

    @OnClick(R.id.btn_take_image)
    public void openPopUpForImageGrab() {
        final CharSequence[] items = {"Take photo", "Pic from gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_take_pic, null);
        builder.setCustomTitle(view);
        builder.setItems(items, (dialog, item) -> {
            fileUri = getOutputMediaFileUri("identifier", getActivity());
            if (items[item].equals("Take photo")) {
                openCamera(fileUri); // Fire Camera Intent
            } else if (items[item].equals("Pic from gallery")) {
//                fireGalleryIntent(fileUri, activity); // Fire Gallery Intent
            }
        });
        builder.show();
    }

    public void openCamera(Uri fileUri) {
        Log.d("Ravi", "openCamera-called");
        if (getActivity().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA);
                    return;
                }
                if ((getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                        (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_STORAGE);
                    return;
                }

            }
            File f = null;
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (fileUri != null)
                f = new File(fileUri.getPath());
            if (f != null && f.exists()) {
                f.delete();
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            if (fileUri != null)
                startActivityForResult(intent, 100);
            else
                showToast("Some issues with storage, try deleting some items from memory.");

        } else {
            showToast("Camera not supported");
        }
    }

    public Uri getOutputMediaFileUri(String identifier, Context context) {
        File file = getOutputMediaFile(identifier);
        if (file != null)
            if (Build.VERSION.SDK_INT < 24) {
                return Uri.fromFile(file);
            } else {
                return FileProvider.getUriForFile(
                        context,
                        context.getApplicationContext()
                                .getPackageName() + ".provider", file);
            }
        else
            return null;
    }

    public File getOutputMediaFile(String identifier) {

        // External sdcard location
        File mediaFile = null;
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                SURVEY_PIC);

        // Create the storage directory if it does not exist
        if (mediaStorageDir != null && !mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(SURVEY_PIC, "Oops! Failed create "
                        + SURVEY_PIC + " directory");
                return null;
            }
        }
        if (mediaStorageDir != null)
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + identifier + ".jpg");
        return mediaFile;
    }

    public File getFile(Context context, Uri uri) {
        if (uri != null) {
            String path = uri.getPath();
            if (path != null) {
                return new File(path);
            }
        }
        return null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {  // On return from camera intent
            setDocImage();
        } else if (requestCode == 400 && resultCode == RESULT_OK) {  // On return from gallery intent
            if (data != null) {
//                setDocImage(data.getData());
            }
        } else locationHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void setDocImage() {
        actualImageFile = getFile(getActivity(), fileUri);
        setLayoutWithPODImage(actualImageFile);
    }

    private void setLayoutWithPODImage(File actualImageFile) {
        if (actualImageFile == null) {
            Toast.makeText(getActivity(), "Cannot upload picture from this resource currently!", Toast.LENGTH_LONG).show();
            return;
        }
        Bitmap demo = getBitmapFromFile(actualImageFile.getPath(), THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
        if (demo != null && demo.getWidth() > 0 && demo.getHeight() > 0) {
//            viewDoc.setVisibility(View.VISIBLE);
//            uploadAgain.setText(getString(R.string.upload_again));
            imageView.setImageBitmap(demo);
            imageBitmap = demo;
        } else {
//            viewDoc.setVisibility(View.GONE);
//            uploadAgain.setText(getString(R.string.take_photo));
        }
    }

    public static Bitmap getBitmapFromFile(String bitmapFilePath, int reqWidth, int reqHeight) {
        // calculating image size
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(new FileInputStream(new File(bitmapFilePath)), null, options);
        } catch (FileNotFoundException e) {
            Log.e("DEBUG", e.getMessage());
        }

        int scale = calculateInSampleSize(options, reqWidth, reqHeight);

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;

        try {
            return BitmapFactory.decodeStream(new FileInputStream(new File(bitmapFilePath)), null, o2);
        } catch (FileNotFoundException e) {
            Log.e("DEBUG", e.getMessage());
            return null;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
