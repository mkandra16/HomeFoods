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


public class ChefTodaysOrdersSnapshotRVAdapter extends RecyclerView.Adapter<ChefTodaysOrdersSnapshotRVAdapter.ViewHolder> {
    private List<OneDishOrder> items;
    private ItemClickListener itemClickListener;

    public ChefTodaysOrdersSnapshotRVAdapter(List<OneDishOrder> objects) {
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
        View parent = LayoutInflater.from(context).inflate(R.layout.chef_snapshot_list_item, viewGroup, false);
        return ViewHolder.newInstance(parent, viewType);

    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final OneDishOrder item;
        final int pos = position;
        item = items.get(position);
        viewHolder.setDishName(item.getDishName());
        viewHolder.setDishQty(item.getQuantity());
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
        private TextView tvDishNameOrderedToday;
        private TextView tvTotalDishOrdersToday;


        public static ViewHolder newInstance(View parent, int viewType) {
            TextView tvDishNameOrderedToday = (TextView) parent.findViewById(R.id.tvDishNameOrderedToday);
            TextView tvTotalDishOrdersToday = (TextView) parent.findViewById(R.id.tvTotalDishOrdersToday);
            return new ViewHolder(parent, tvDishNameOrderedToday, tvTotalDishOrdersToday);
        }

        private ViewHolder(View parent, TextView tvDishNameOrderedToday, TextView tvTotalDishOrdersToday) {
            super(parent);
            this.parent = parent;
            this.tvDishNameOrderedToday = tvDishNameOrderedToday;
            this.tvTotalDishOrdersToday = tvTotalDishOrdersToday;
        }

        public void setDishName(CharSequence text) {
            tvDishNameOrderedToday.setText(text);
        }

        public void setDishQty(int qty) {
            tvTotalDishOrdersToday.setText("" + qty);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
