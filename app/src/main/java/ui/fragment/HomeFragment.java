package ui.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.storage.StorageReference;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnTextChanged;
import di.FragmentScope;
import io.reactivex.subjects.PublishSubject;
import survey.property.roadster.com.surveypropertytax.BaseIntranction;
import survey.property.roadster.com.surveypropertytax.R;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.HomePresenter;
import ui.HomeView;
import ui.adapter.LoadingAdapter;
import ui.adapter.PropertyAdapter;
import ui.data.PropertyData;
import ui.data.PropertyDto;

@FragmentScope
public class HomeFragment extends SurveyBaseFragment<HomePresenter, HomeFragment.LoginIntraction>
        implements LoadingAdapter.AdapterClickCallback<PropertyData>, HomeView{

    @Inject
    @Named("propertySearchString")
    public PublishSubject<String> textSearchObservable;

    @Inject
    protected PropertyAdapter adapter;

    @Inject
    StorageReference storageReference;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_list)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.property_search)
    EditText propertySearch;

    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    public void preInit() {
        super.preInit();

    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
    }

    @Override
    public void postInit() {
        super.postInit();
        mPresenter.generateData();
        mPresenter.load();
    }

    @OnTextChanged(R.id.property_search)
    void onTextSearch(){
        String searchString = propertySearch.getText().toString();
        if(searchString.length() >= 0){
            textSearchObservable.onNext(searchString);
        }else{
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
    public void onAdapterItemClick(int position, @NonNull PropertyData data) {
        openBottomSheet(((List<PropertyDto>)data.getItem()).get(position));
    }

    void openBottomSheet(PropertyDto propertyData){
       getActivityCommunicator().gotoActionFragment(propertyData);
    }

    public PropertyAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void updateList(PropertyData propertyData) {
        getAdapter().addItem(propertyData);
    }

    private void onRefresh(){
        getAdapter().removeAll();
        mPresenter.generateData();
        mPresenter.load();
        swipeRefreshLayout.setRefreshing(false);
    }

    public interface LoginIntraction extends BaseIntranction {
        void gotoActionFragment(PropertyDto propertyData);
    }

    @Override
    public void onDestroyView() {
        mPresenter.finish();
        super.onDestroyView();
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
}
