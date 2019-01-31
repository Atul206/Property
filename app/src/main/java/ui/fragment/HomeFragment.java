package ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import di.FragmentScope;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import survey.property.roadster.com.surveypropertytax.BaseIntranction;
import survey.property.roadster.com.surveypropertytax.R;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import survey.property.roadster.com.surveypropertytax.db.SubmitedLoadObject;
import ui.HomeActivity;
import ui.HomePresenter;
import ui.HomeView;
import ui.adapter.LoadingAdapter;
import ui.adapter.PropertyAdapter;
import ui.data.PropertyData;
import ui.data.PropertyDto;
import ui.enums.TagType;

@FragmentScope
public class HomeFragment extends SurveyBaseFragment<HomePresenter, HomeFragment.LoginIntraction>
        implements LoadingAdapter.AdapterClickCallback<PropertyData>, HomeView {


    private static final String QR_CODE_STR = "qr_code_search";
    public static final int REQUEST_CAMERA = 1;

    @Inject
    @Named("propertySearchString")
    public PublishSubject<String> textSearchObservable;

    @Inject
    protected PropertyAdapter adapter;

    @Inject
    StorageReference storageReference;


    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.fab2)
    FloatingActionButton fab2;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_list)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.property_search)
    EditText propertySearch;

    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    @BindView(R.id.todayCount)
    TextView todayCount;

    @BindView(R.id.totalcount)
    TextView totalCount;

    private BottomSheetBehavior bottomSheetBehavior;
    private String qrCode;

    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItems = getAdapter().getItemCount();
                if (totalItems > 0 && ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition() >= totalItems - 1) {
                    mPresenter.onPageScrolled();
                }
            }
        });
    }

    public void setQrCodeStr(String qr) {
        qrCode = qr;
        if (qrCode != null) {
            propertySearch.setText(qrCode);
        }
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        if (getActivity().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA);
                    return;
                }
            }
        }
        getActivityCommunicator().gotoScanFragment();
    }


    @OnClick(R.id.fab2)
    void onFab2Click() {
        getActivityCommunicator().gotoFormFragment(null, TagType.ADD);
    }


    @Override
    public void postInit() {
        super.postInit();
        mPresenter.generateData();
        mPresenter.load();
        if (qrCode != null && qrCode.length() > 0) {
            propertySearch.setText(qrCode);
        }
    }

    @OnTextChanged(R.id.property_search)
    void onTextSearch() {
        String searchString = propertySearch.getText().toString();
        if (searchString.length() >= 0) {
            textSearchObservable.onNext(searchString);
        } else {
            propertySearch.clearFocus();
            getAdapter().removeAll();
            mPresenter.load();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(getAdapter());
        propertySearch.clearFocus();
    }

    @NonNull
    @Override
    protected Class<LoginIntraction> getListenerClass() {
        return LoginIntraction.class;
    }

    @Override
    protected SwipeRefreshLayout getSwipRefreshView() {
        return swipeRefreshLayout;
    }

    @Override
    public void onAdapterItemClick(int position, @NonNull List<PropertyDto> data, TagType tagType) {
        getActivityCommunicator().gotoFormFragment(data.get(position), tagType);
    }

    void openBottomSheet(PropertyDto propertyData) {
        getActivityCommunicator().gotoActionFragment(propertyData);
    }

    public PropertyAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void updateList(PropertyData propertyData) {
        List<PropertyDto> data = propertyData.getItem();
        if (null != data && !data.isEmpty()) {
            getAdapter().addItem(data);
            mPresenter.updateCurrentPage(data.get(data.size() - 1).getUid());
        }
    }

    @Override
    public void searchList(PropertyData propertyData) {
        getAdapter().removeAll();
        List<PropertyDto> data = propertyData.getItem();
        if (null != data && !data.isEmpty()) {
            if (data.size() > 1) {
                data.remove(data.size() - 1);
            }
            getAdapter().addItem(data);
            mPresenter.updateCurrentPage(0);
        }
    }

    private void onRefresh() {
        getAdapter().removeAll();
        mPresenter.generateData();
        mPresenter.load();
        swipeRefreshLayout.setRefreshing(false);
        propertySearch.setText("");
        mPresenter.reset();
    }

    public interface LoginIntraction extends BaseIntranction {
        void gotoActionFragment(PropertyDto propertyData);

        void gotoFormFragment(PropertyDto propertyDto, TagType tagType);

        void gotoScanFragment();
    }

    @Override
    public void readData(String uid) {
        //mApp.readDataFromDatabase("39C1000U63");
    }

    @Override
    public void updateTodayCount(int count, int totalC) {
        ((HomeActivity) getActivity()).runOnUiThread(() -> {
            todayCount.setText(count + " entry submitted in last 24 hour");
            totalCount.setText("Total " + totalC + " entry submmited till date");

        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.finish();
        super.onDestroyView();
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
}
