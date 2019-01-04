package ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import survey.property.roadster.com.surveypropertytax.BaseIntranction;
import survey.property.roadster.com.surveypropertytax.R;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.ActionPresenter;
import ui.ActionView;
import ui.adapter.LoadingAdapter;
import ui.adapter.PropertyActionAdapter;
import ui.data.PropertyActionList;
import ui.data.PropertyData;
import ui.data.PropertyDto;

public class PropertyActionFragment extends SurveyBaseFragment<ActionPresenter, PropertyActionFragment.PropertyActionFragmentIntraction>
        implements LoadingAdapter.AdapterClickCallback<PropertyData>, ActionView {

    private static final String PROPERTY_DATA = "property_data";

    @Inject
    protected PropertyActionAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PropertyDto propertyDto;

    @Override
    public void initLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(getAdapter());

    }

    @Override
    public int getLayout() {
        return R.layout.property_bottomsheet;
    }

    public PropertyActionAdapter getAdapter() {
        return adapter;
    }

    @NonNull
    @Override
    protected Class<PropertyActionFragmentIntraction> getListenerClass() {
        return PropertyActionFragmentIntraction.class;
    }

    @Override
    protected SwipeRefreshLayout getSwipRefreshView() {
        return null;
    }

    @Override
    public void preInit() {
        super.preInit();
        Bundle bundle = getArguments();
        propertyDto = (PropertyDto) bundle.getSerializable(PROPERTY_DATA);
        mPresenter.setPropertyData(propertyDto);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onAdapterItemClick(int position, @NonNull PropertyData data) {
        propertyDto.setPropertyAction(((List<String>)data.getItem()).get(position));
        getActivityCommunicator().gotoFormFragment(propertyDto);
    }

    @Override
    public void postInit() {
        super.postInit();
        mPresenter.load();
    }

    @Override
    public void addItemInList(PropertyActionList propertyActionList) {
        getAdapter().removeAll();
        getAdapter().addItem(propertyActionList);
    }

    public static PropertyActionFragment newInstance(PropertyDto data) {
        PropertyActionFragment fragment = new PropertyActionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROPERTY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface PropertyActionFragmentIntraction extends BaseIntranction {
        void gotoFormFragment(PropertyDto propertyDto);
    }
}
