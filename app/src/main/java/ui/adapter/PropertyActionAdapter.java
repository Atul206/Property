package ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import survey.property.roadster.com.surveypropertytax.R;
import ui.data.PropertyData;

public class PropertyActionAdapter extends LoadingAdapter<PropertyData, PropertyActionAdapter.PropertyActionViewHolder>  {


    @Inject
    public PropertyActionAdapter(Context context, AdapterClickCallback<PropertyData> callback) {
        super(context, callback);
    }

    @Override
    PropertyActionViewHolder getMainViewHolder(ViewGroup parent, int viewType) {
        return  new PropertyActionViewHolder(LayoutInflater.from(context).inflate(R.layout.bottomsheet_item,
                parent, false));
    }

    @Override
    void bindData(PropertyActionViewHolder holder, int p) {
        holder.holdData(p);
    }

    public class PropertyActionViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        @BindView(R.id.main_layout)
        ConstraintLayout mainLayout;

        @NonNull
        @BindView(R.id.action_type)
        TextView actionType;

        public PropertyActionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void holdData(int position){
            String item = ((List<String>)data.getItem()).get(position);
            if(item != null && item instanceof String) {
                actionType.setText(item);
                mainLayout.setOnClickListener(__ -> {
                    callback.onAdapterItemClick(position, data);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0: ((List<String>)data.getItem()).size();
    }
}
