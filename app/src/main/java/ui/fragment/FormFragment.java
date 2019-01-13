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
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.firebase.storage.StorageReference;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import di.FragmentScope;
import survey.property.roadster.com.surveypropertytax.BaseIntranction;
import survey.property.roadster.com.surveypropertytax.R;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.FormPresenter;
import ui.FormView;
import ui.data.PropertyDto;
import ui.enums.TagType;

import static android.app.Activity.RESULT_OK;

@FragmentScope
public class FormFragment extends SurveyBaseFragment<FormPresenter, FormFragment.LoginIntraction>
        implements FormView {

    @Nullable
    @BindView(R.id.signaturePad)
    SignaturePad signaturePad;
    @Nullable
    @BindView(R.id.image_view)
    ImageView imageView;
    @Nullable
    @BindView(R.id.edit_property_name)
    EditText editPropertyName;
    @Nullable
    @BindView(R.id.edit_property_contact_no)
    EditText editPropertyContactNo;
    @Nullable
    @BindView(R.id.edit_property_address)
    EditText editPropertyAddress;
    @Nullable
    @BindView(R.id.edit_property_paddress)
    EditText editPermanentAddress;
    @Nullable
    @BindView(R.id.edit_property_email_id)
    EditText editEmailAddress;
    @Nullable
    @BindView(R.id.edit_property_remark)
    EditText editRemarks;
    @Nullable
    @BindView(R.id.edit_property_id)
    EditText editPropertyId;
    @Nullable
    @BindView(R.id.btn_save_form)
    Button btnSaveForm;
    @Nullable
    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @Nullable
    @BindView(R.id.edit_property_contact_no_new)
    EditText contactNew;


    public static String SURVEY_PIC = "survey_pic";
    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_STORAGE = 2;

    private Uri fileUri;
    private File actualImageFile;
    private Bitmap imageBitmap;

    private static final int THUMBNAIL_WIDTH = 400;
    private static final int THUMBNAIL_HEIGHT = 150;

    private static final String PROPERTY_DATA = "property_data";
    private static final String LOCATION_DATA = "location_data";
    private static final String TAG_TYPE = "tag_type";

    double latitude;
    double longitude;

    @Inject
    StorageReference storageReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        PropertyDto propertyData = (PropertyDto) bundle.getSerializable(PROPERTY_DATA);
        Location location = bundle.getParcelable(LOCATION_DATA);
        TagType tagType = (TagType) bundle.getSerializable(TAG_TYPE);
        mPresenter.setPropertyData(propertyData);
        mPresenter.setLastLocation(location);
        mPresenter.setTagType(tagType);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_navigate, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_navigate:
                navigate();
                return true;
            default:
                break;
        }

        return false;
    }

    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    protected void initListener() {
        switch (mPresenter.getTagType()) {
            case NO:
                spinner.setItems("Locked", "Owner refused", "No one lives");
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        mPresenter.getPropertyData().setReason(String.valueOf(item));
                    }
                });
                break;
            default:
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
                        Toast.makeText(getActivity(), "Signature clear", Toast.LENGTH_LONG).show();
                    }
                });
        }
    }

    @Override
    public void postInit() {
        super.postInit();
    }

    @Override
    public int getLayout() {
        switch (mPresenter.getTagType()) {
            case NO:
                return R.layout.fragment_form_no;
            default:
                return R.layout.fragment_form;
        }
    }

    @Override
    public void initLayout() {
        switch (mPresenter.getTagType()) {
            case YES:
                break;
            case NO:
                break;
            default:
                return;
        }
        editPropertyContactNo.setText(String.valueOf(mPresenter.getPropertyData().getContactNo()));
        editPropertyName.setText(String.valueOf(mPresenter.getPropertyData().getPropertyName()));
        editPropertyAddress.setText(String.valueOf(mPresenter.getPropertyData().getAddress()));
        editPropertyId.setText(String.valueOf(mPresenter.getPropertyData().getPropertyId()));
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
        Location mLastLocation = mPresenter.getLastLocation();

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            showToast("latitude : " + String.valueOf(latitude) + " longitide : " + String.valueOf(longitude));
        } else {

            if (btnSaveForm.isEnabled())
                btnSaveForm.setEnabled(false);

            showToast("Couldn't get the location. Make sure location is enabled on the device");
        }
        if(signaturePad != null || imageView != null) {

        }else{
            showToast("Signature or photo missing");
            return;
        }
        if (imageBitmap != null) {
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
        }

        mPresenter.getPropertyData().setPermanentAddress(String.valueOf(editPermanentAddress.getText()));
        mPresenter.getPropertyData().setPropertyEmail(String.valueOf(editEmailAddress.getText()));
        mPresenter.getPropertyData().setRemarks(String.valueOf(editRemarks.getText()));
        mPresenter.getPropertyData().setNewContact(String.valueOf(contactNew.getText()));
        if (mPresenter.getPropertyData().getLatitude() == null)
            mPresenter.getPropertyData().setLatitude(String.valueOf(latitude));
        if (mPresenter.getPropertyData().getLongitude() == null)
            mPresenter.getPropertyData().setLongitude(String.valueOf(longitude));
        mPresenter.registerOfflineFile();
        new LovelyInfoDialog(getActivity())
                .setTopColorRes(android.R.color.white)
                .setIcon(R.drawable.ic_success)
                //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                .setTitle("Property id - " + String.valueOf(editPropertyId.getText()))
                .setMessage("Submited")
                .show();
        getActivity().onBackPressed();
    }

    public void navigate() {
        if (TextUtils.isEmpty(mPresenter.getPropertyData().getLatitude()) || TextUtils.isEmpty(mPresenter.getPropertyData().getLongitude())) {
            Toast.makeText(getActivity(), "Sorry can't navigate.Location of the property not defined!", Toast.LENGTH_LONG).show();
            return;
        }
        String uriString = "http://maps.google.com/maps?daddr=" + mPresenter.getPropertyData().getLatitude() + "," + mPresenter.getPropertyData().getLongitude();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uriString));
        startActivity(intent);
    }

    @Nullable
    @Optional
    @OnClick(R.id.clear)
    public void clearSignaturePad() {
        signaturePad.clear();
    }

    @Nullable
    @Optional
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
        }
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

    public static FormFragment newInstance(PropertyDto propertyDto, Location location, TagType tagType) {
        FormFragment fragment = new FormFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROPERTY_DATA, propertyDto);
        bundle.putParcelable(LOCATION_DATA, location);
        bundle.putSerializable(TAG_TYPE, tagType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Bitmap getSignatureBitmap() {
        if (signaturePad == null) return null;
        return signaturePad.getSignatureBitmap();
    }

    @Override
    public Bitmap getPhotoBitmap() {
        if (imageView == null) return null;
        else return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }

    @Override
    public void errorNetworkError() {
        //showError snackbar
    }
}
