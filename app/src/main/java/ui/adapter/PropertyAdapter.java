package ui.adapter;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import survey.property.roadster.com.surveypropertytax.R;
import ui.LocationUtil.LocationHelper;
import ui.data.PropertyData;
import ui.data.PropertyDto;

public class PropertyAdapter extends  LoadingAdapter<PropertyData, PropertyAdapter.PropertyViewHolder> {


    private Location mLastLocation;
    @Inject
    public PropertyAdapter(Context context, AdapterClickCallback<PropertyData> callback) {
        super(context, callback);
    }

    @Override
    PropertyViewHolder getMainViewHolder(ViewGroup parent, int viewType) {
        return  new PropertyViewHolder(LayoutInflater.from(context).inflate(R.layout.property_item,
                parent, false));
    }

    @Override
    PropertyData getItem(int position) {
        return (PropertyData) data.getItem().get(position);
    }

    @Override
    void bindData(PropertyViewHolder holder,int position) {
        holder.holdData(position);
    }

    public class PropertyViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        @BindView(R.id.main_layout)
        ConstraintLayout mainLayout;

        @NonNull
        @BindView(R.id.property_id)
        TextView propertyId;

        @NonNull
        @BindView(R.id.property_name)
        TextView propertyName;

        @NonNull
        @BindView(R.id.property_distance)
        TextView propertyDistance;

        public PropertyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void holdData(int position){
            PropertyDto item = data.getItem().get(position);
            if(item != null && item instanceof PropertyDto) {
                propertyDistance.setText(context.getString(R.string.distance) + " " + String.valueOf(calculateDistance(item) + " Km"));
                propertyName.setText(context.getString(R.string.name) + " " +item.getPropertyName());
                propertyId.setText(context.getString(R.string.property_id) + " " +String.valueOf(item.getPropertyId()));

                mainLayout.setOnClickListener(__ -> {
                    callback.onAdapterItemClick(position, data);
                });
            }
        }

        private int calculateDistance(PropertyDto propertyDto){
            if(LocationHelper.getlocation() != null) {
                mLastLocation = LocationHelper.getlocation();
                Location l2 = new Location(("end"));
                l2.setLatitude(Double.valueOf(propertyDto.getLatitude()));
                l2.setLongitude(Double.valueOf(propertyDto.getLongitude()));
                return (int) (mLastLocation.distanceTo(l2)/1000);
            }else{
                return 0;
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0: data.getItem().size();
    }
}
