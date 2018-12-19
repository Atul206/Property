package ui.fragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnTextChanged;
import dagger.Provides;
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

@FragmentScope
public class HomeFragment extends SurveyBaseFragment<HomePresenter, HomeFragment.LoginIntraction>
        implements LoadingAdapter.AdapterClickCallback<PropertyData>, HomeView{

    @Inject
    @Named("propertySearchString")
    public PublishSubject<String> textSearchObservable;

    @Inject
    protected PropertyAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_list)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.property_search)
    EditText propertySearch;

    @Override
    public void preInit() {
        super.preInit();
        //Bundle bundle = getArguments();
        //bundle.getString(R.string)
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
        getActivityCommunicator().gotoFormFragment();
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
        mPresenter.load();
        swipeRefreshLayout.setRefreshing(false);
    }

    public interface LoginIntraction extends BaseIntranction {
        void gotoFormFragment();
    }
}
