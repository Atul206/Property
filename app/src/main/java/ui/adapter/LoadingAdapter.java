package ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import javax.inject.Inject;

public abstract class LoadingAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    @NonNull
    protected T data;

    @Inject
    AdapterClickCallback<T> callback;

    @NonNull
    Context context;

    public LoadingAdapter(Context context, AdapterClickCallback<T> callback) {
        super();
        this.context = context;
        this.callback = callback;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return getMainViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        bindData(holder, position);
    }

    abstract VH getMainViewHolder(ViewGroup parent, int viewType);

    public void removeAll() {
        data = null;
        notifyDataSetChanged();
    }

    public interface AdapterClickCallback<T> {
        void onAdapterItemClick(int position, @NonNull T data);
    }

    public void addItem(T items){
        data = items;
        notifyDataSetChanged();
    }

    abstract void bindData(VH holder, int p);
}
