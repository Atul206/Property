package ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ui.HomeActivity;
import ui.data.PropertyData;
import ui.data.PropertyDto;
import ui.data.PropertyListDto;
import ui.enums.TagType;

public abstract class LoadingAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    @NonNull
    protected List<PropertyDto> data;

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
        try {
            ((HomeActivity)context).runOnUiThread(() -> {
                data = null;
                notifyDataSetChanged();
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface AdapterClickCallback<T> {
        void onAdapterItemClick(int position, @NonNull List<PropertyDto> data, TagType tagType);
    }

    public void addItem(List<PropertyDto> items){
        try {

            ((HomeActivity) context).runOnUiThread(() -> {
                if (data == null) {
                    data = new ArrayList<>();
                }
                data.addAll(items);
                notifyDataSetChanged();
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    abstract void bindData(VH holder, int p);
}
