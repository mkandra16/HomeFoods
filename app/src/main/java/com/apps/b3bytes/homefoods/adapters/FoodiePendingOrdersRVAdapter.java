package com.apps.b3bytes.homefoods.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.FoodieOrder;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;


public class FoodiePendingOrdersRVAdapter extends RecyclerView.Adapter<FoodiePendingOrdersRVAdapter.ViewHolder> {
    /* TODO: TEST DATA */
    String[] dateArray = {"Apr 14, 2015", "Mar 09, 2015", "Aug 15, 2015", "Sep 01, 2015"};
    String[] dishNameArray = {"Dish Name1", "Dish Name2", "Dish Name3", "Dish Name4"};
    String[] chefNameArray = {"Chef Name1", "Chef Name2", "Chef Name3", "Chef Name4"};
    /* TODO: END TEST DATA */

    private ArrayList<FoodieOrder> items;
    private ItemClickListener itemClickListener;
    private Context mContext;

    public FoodiePendingOrdersRVAdapter(Context context) {
        mContext = context;
        this.items = new ArrayList<FoodieOrder>();
        EnumSet<FoodieOrder.OrderStatus> statuses = EnumSet.of(FoodieOrder.OrderStatus.Ordered);
        AppGlobalState.gDataLayer.getFoodieOrders(statuses, new DataLayer.GetFoodieOrdersCallback() {
            @Override
            public void done(ArrayList<FoodieOrder> orders, Exception e) {
                if (e == null) {
                    items.addAll(orders);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Successfully retrieved all pending orders", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Failed to get all Pending orders", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void SetOnItemClickListener(final ItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        public void onItemClick(FoodieOrder order, int position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.foodie_pending_order_item, viewGroup, false);
        return ViewHolder.newInstance(parent, viewType);

    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final FoodieOrder item;
        final int pos = position;
        item = items.get(position);
        viewHolder.bindView(item);
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

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private View parent;
        private TextView tvOrderNumberVal;
        private TextView tvOrderedFromChefVal;
        private TextView tvOrderedPriceVal;
        private TextView tvDishOrderedOnVal;
        private TextView tvDishOrderedDateVal;

        public static ViewHolder newInstance(View parent, int viewType) {
            return new ViewHolder(parent);
        }

        private ViewHolder(View parent) {
            super(parent);
            this.parent = parent;
            tvOrderNumberVal = (TextView) parent.findViewById(R.id.tvOrderNumberVal);
            tvOrderedFromChefVal = (TextView) parent.findViewById(R.id.tvOrderedFromChefVal);
            tvOrderedPriceVal = (TextView) parent.findViewById(R.id.tvOrderedPriceVal);
            tvDishOrderedOnVal = (TextView) parent.findViewById(R.id.tvDishOrderedOnVal);
            tvDishOrderedDateVal = (TextView) parent.findViewById(R.id.tvDishOrderedDateVal);
        }

        private void initTextView(TextView tvView, String text) {
            if (text != null && !text.isEmpty()) {
                tvView.setText(text);
            }
        }

        public void bindView(FoodieOrder order) {
            initTextView(tvOrderNumberVal, order.getmTag());

            String chefNames = new String();
            Set<ChefOrder> chefOrders = order.getmChefOrders();
            for (ChefOrder chef : chefOrders) {
                if (!chefNames.isEmpty()) {
                    chefNames += ", ...";
                    break;
                }
                chefNames = chef.getmChef().getmUserName();
            }
            initTextView(tvOrderedFromChefVal, chefNames);
            initTextView(tvOrderedPriceVal, String.valueOf(order.getmTotal()));
            initTextView(tvDishOrderedOnVal, order.getmOrderStatus().toString());
            initTextView(tvDishOrderedDateVal, order.getmOrderedDate());
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
