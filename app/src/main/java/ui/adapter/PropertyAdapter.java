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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import survey.property.roadster.com.surveypropertytax.R;
import ui.LocationUtil.LocationHelper;
import ui.data.PropertyData;
import ui.data.PropertyDto;
import ui.enums.TagType;

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
    void bindData(PropertyViewHolder holder,int position) {
        holder.holdData(position);
    }

    public class PropertyViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        @BindView(R.id.main_layout)
        ConstraintLayout mainLayout;

        @NonNull
        @BindView(R.id.property_label)
        TextView propertyLabel;

        @NonNull
        @BindView(R.id.property_id)
        TextView propertyId;

        @NonNull
        @BindView(R.id.property_name)
        TextView propertyName;

        @NonNull
        @BindView(R.id.property_distance)
        TextView propertyDistance;

        @NonNull
        @BindView(R.id.contact)
        TextView contact;

        @NonNull
        @BindView(R.id.property_address)
        TextView propertyAddress;

        public PropertyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void holdData(int position){
            PropertyDto item = (PropertyDto) data.get(position);
            if(item.getActionTaken() != null && item.getActionTaken()){
                mainLayout.setBackground(context.getResources().getDrawable(R.drawable.submit_card_bg));
            }
            if(item != null && item instanceof PropertyDto) {
                propertyName.setText(context.getString(R.string.name) + " " +item.getPropertyName());
                propertyId.setText(context.getString(R.string.property_id) + " " +String.valueOf(item.getPropertyId()));
                propertyAddress.setText(context.getString(R.string.address) + " " +String.valueOf(item.getAddress()));
                propertyDistance.setOnClickListener(v -> {
                    callback.onAdapterItemClick(position, data, TagType.YES);
                    //callback.onAdapterItemClick();
                });

                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onAdapterItemClick(position, data, TagType.NO);
                    }
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
        return data == null ? 0: data.size();
    }
}
