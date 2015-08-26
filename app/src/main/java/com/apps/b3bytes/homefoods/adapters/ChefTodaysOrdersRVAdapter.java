package com.apps.b3bytes.homefoods.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.Foodie;

import java.util.ArrayList;


public class ChefTodaysOrdersRVAdapter extends RecyclerView.Adapter<ChefTodaysOrdersRVAdapter.ViewHolder> {
    private ArrayList<ChefOrder> items;
    private ItemClickListener itemClickListener;

    public ChefTodaysOrdersRVAdapter() {
        this.items = new ArrayList<ChefOrder>();
        // Todo : Get Orders received by current User.
        AppGlobalState.gDataLayer.getOrdersForChef(Foodie.createDummyFoodie(), new DataLayer.getChefOrdersCallback() {
            @Override
            public void done(ArrayList<ChefOrder> orders, Exception e) {
                items = orders;
                notifyDataSetChanged();
            }
        });
    }

    public void SetOnItemClickListener(final ItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        public void onItemClick(ChefOrder item, int position);
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
        final ChefOrder item;
        final int pos = position;
        item = items.get(position);
        viewHolder.bindView(item);
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
        protected TextView tvOrderNumVal;
        protected TextView tvNumberOfDishesVal;
        protected TextView tvDishesNamesVal;
        protected TextView tvTotalPriceVal;
        public static ViewHolder newInstance(View parent, int viewType) {
            ImageView ivPickupDelivery = (ImageView) parent.findViewById(R.id.ivPickupDelivery);

            return new ViewHolder(parent);
        }

        private ViewHolder(View parent) {
            super(parent);
            this.parent = parent;
            tvOrderNumVal = (TextView) parent.findViewById(R.id.tvOrderNumVal);
            tvNumberOfDishesVal = (TextView) parent.findViewById(R.id.tvNumberOfDishesVal);
            tvDishesNamesVal = (TextView) parent.findViewById(R.id.tvDishesNamesVal);
            tvTotalPriceVal = (TextView) parent.findViewById(R.id.tvTotalPriceVal);
        }

        public void bindView(ChefOrder order) {
            tvOrderNumVal.setText("" + order.getmTag());
            tvNumberOfDishesVal.setText("" + order.getmDishOrders().size());
            tvTotalPriceVal.setText("" + order.getmTotal());
            String dishesStr = new String();
            for (DishOrder d : order.getmDishOrders()) {
                if (! dishesStr.isEmpty()) {
                    dishesStr += ",";
                }
                dishesStr += d.getmDishOnSale().getmDish().getmDishName();
            }
            tvDishesNamesVal.setText("" + dishesStr);

        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
