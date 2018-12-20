package ui.fragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnTextChanged;
import di.FragmentScope;
import io.reactivex.subjects.PublishSubject;
import survey.property.roadster.com.surveypropertytax.BaseIntranction;
import survey.property.roadster.com.surveypropertytax.R;
import survey.property.roadster.com.surveypropertytax.SurveyBaseFragment;
import ui.FormPresenter;
import ui.FormView;
import ui.data.PropertyDto;

@FragmentScope
public class FormFragment extends SurveyBaseFragment<FormPresenter, FormFragment.LoginIntraction>
        implements FormView {

    @Override
    public void preInit() {
        super.preInit();
        //Bundle bundle = getArguments();
        //bundle.getString(R.string)
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void postInit() {
        super.postInit();
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

    public interface LoginIntraction extends BaseIntranction {
        void gotoFormFragment(PropertyDto data);
    }

    @Override
    public void onDestroyView() {
        mPresenter.finish();
        super.onDestroyView();
    }
}
