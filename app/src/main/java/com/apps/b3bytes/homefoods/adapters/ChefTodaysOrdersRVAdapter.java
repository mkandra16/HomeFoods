package com.apps.b3bytes.homefoods.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.OneDishOrder;

import java.util.List;


public class ChefTodaysOrdersRVAdapter extends RecyclerView.Adapter<ChefTodaysOrdersRVAdapter.ViewHolder> {
    private List<OneDishOrder> items;
    private ItemClickListener itemClickListener;

    public ChefTodaysOrdersRVAdapter(List<OneDishOrder> objects) {
        this.items = objects;
    }

    public void SetOnItemClickListener(final ItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        public void onItemClick(OneDishOrder item, int position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.chef_home_orders_list_item, viewGroup, false);
        return ViewHolder.newInstance(parent, viewType);

    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final OneDishOrder item;
        final int pos = position;
        item = items.get(position);
        //viewHolder.setOrderNumVal(item.getDishName());
        //TODO: set other views
        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(item, pos);
            }
        });
    }

    // With the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private View parent;
        protected TextView tvOrderNumHdr;
        protected TextView tvOrderNumVal;
        protected ImageView ivPickupDelivery;

        public static ViewHolder newInstance(View parent, int viewType) {
            TextView tvOrderNumHdr = (TextView) parent.findViewById(R.id.tvOrderNumHdr);
            TextView tvOrderNumVal = (TextView) parent.findViewById(R.id.tvOrderNumVal);
            ImageView ivPickupDelivery = (ImageView) parent.findViewById(R.id.ivPickupDelivery);

            return new ViewHolder(parent, tvOrderNumHdr, tvOrderNumVal, ivPickupDelivery);
        }

        private ViewHolder(View parent, TextView tvOrderNumHdr, TextView tvOrderNumVal, ImageView ivPickupDelivery) {
            super(parent);
            this.parent = parent;
            this.tvOrderNumHdr = tvOrderNumHdr;
            this.tvOrderNumVal = tvOrderNumVal;
            this.ivPickupDelivery = ivPickupDelivery;
        }

        public void setOrderNumVal(int orderNum) {
            tvOrderNumVal.setText("" + orderNum);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
