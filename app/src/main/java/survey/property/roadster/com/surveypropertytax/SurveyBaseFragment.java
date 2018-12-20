package survey.property.roadster.com.surveypropertytax;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;

public abstract class SurveyBaseFragment<P, T extends BaseIntranction> extends DaggerFragment {

    @Inject
    protected P mPresenter;


    private T mListener;

    @CallSuper
    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);

        Class<T> listenerClass = getListenerClass();

        if (listenerClass.isInstance(context)) {
            mListener = listenerClass.cast(context);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Fragments context");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_base, container, false);
        View inflatedView = inflater.inflate(getLayout(), (ViewGroup) parentView.findViewById(R.id.main_container), false);
        ((FrameLayout) parentView.findViewById(R.id.main_container)).addView(inflatedView, 0);

        ButterKnife.bind(this, parentView);

        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preInit();
        initLayout();
        initListener();
        postInit();

    }

    public void preInit(){

    }

    public abstract void initLayout();

    public void postInit(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }

    @LayoutRes
    public abstract int getLayout();

    @NonNull
    protected abstract Class<T> getListenerClass();

    protected abstract SwipeRefreshLayout getSwipRefreshView();

    protected abstract void initListener();

    @NonNull
    protected T getActivityCommunicator() {
        return mListener;
    }
}
