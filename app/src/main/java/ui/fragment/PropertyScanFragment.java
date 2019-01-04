package ui.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;

import javax.xml.transform.Result;

import butterknife.BindView;
import survey.property.roadster.com.surveypropertytax.BaseIntranction;
import survey.property.roadster.com.surveypropertytax.R;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.ScanPresenter;
import ui.ScanView;

public class PropertyScanFragment extends SurveyBaseFragment<ScanPresenter, PropertyScanFragment.PropertyScanFragmentIntraction> implements ScanView {



    @BindView(R.id.scanner_view)
    CodeScannerView codeScannerView;

    private CodeScanner mCodeScanner;

    @Override
    public void preInit() {
        super.preInit();
        mCodeScanner = new CodeScanner(getActivity(), codeScannerView);
    }

    @Override
    public void initLayout() {

    }


    @Override
    public int getLayout() {
        return R.layout.qr_fragment;
    }

    @NonNull
    @Override
    protected Class<PropertyScanFragmentIntraction> getListenerClass() {
        return PropertyScanFragmentIntraction.class;
    }

    @Override
    protected SwipeRefreshLayout getSwipRefreshView() {
        return null;
    }

    @Override
    protected void initListener() {
        mCodeScanner.setDecodeCallback(result -> getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
                getActivityCommunicator().qrCodeString(result.getText());
                getActivity().onBackPressed();
            }
        ));
        codeScannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    public static PropertyScanFragment newInstance(){
        PropertyScanFragment fragment = new PropertyScanFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCodeScanner.stopPreview();
    }

    public interface PropertyScanFragmentIntraction extends BaseIntranction {
        void qrCodeString(String text);
    }
}
