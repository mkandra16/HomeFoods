package com.apps.b3bytes.homefoods.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.FoodieOrder;
import com.apps.b3bytes.homefoods.utils.Utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;


public class FoodiePendingOrdersRVAdapter extends RecyclerView.Adapter<FoodiePendingOrdersRVAdapter.ViewHolder> {
    private ArrayList<FoodieOrder> items;
    private ItemClickListener itemClickListener;
    private Context mContext;

    onViewCancelOrderClickListener viewCancelOrderClickListener;

    public interface onViewCancelOrderClickListener {
        public void viewCancelOrderClicked(FoodieOrder foodieOrder);
    }

    public void setOnViewCancelOrderClickListener(onViewCancelOrderClickListener listener) {
        viewCancelOrderClickListener = listener;
    }

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
        return new ViewHolder(parent);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View parent;
        private TextView tvOrderNumberVal;
        private TextView tvOrderedFromChefVal;
        private TextView tvOrderedPriceVal;
        private TextView tvDishOrderedOnVal;
        private TextView tvDishDeliveryOnDateVal;
        private Button bViewCancelOrder;

        private ViewHolder(View parent) {
            super(parent);
            this.parent = parent;
            tvOrderNumberVal = (TextView) parent.findViewById(R.id.tvOrderNumberVal);
            tvOrderedFromChefVal = (TextView) parent.findViewById(R.id.tvOrderedFromChefVal);
            tvOrderedPriceVal = (TextView) parent.findViewById(R.id.tvOrderedPriceVal);
            tvDishOrderedOnVal = (TextView) parent.findViewById(R.id.tvDishOrderedOnVal);
            tvDishDeliveryOnDateVal = (TextView) parent.findViewById(R.id.tvDishDeliveryOnDateVal);
            bViewCancelOrder = (Button) parent.findViewById(R.id.bViewCancelOrder);
        }

        public void bindView(final FoodieOrder order) {
            Utils.initTextView(tvOrderNumberVal, order.getmTag());

            String chefNames = new String();
            Set<ChefOrder> chefOrders = order.getmChefOrders();
            for (ChefOrder chef : chefOrders) {
                if (!chefNames.isEmpty()) {
                    chefNames += ", ...";
                    break;
                }
                chefNames = chef.getmChef().getmUserName();
            }
            Utils.initTextView(tvOrderedFromChefVal, chefNames);
            Utils.initTextView(tvOrderedPriceVal, String.valueOf(order.getmTotal()));
            Utils.initTextView(tvDishOrderedOnVal, order.getmOrderedDate());
            //TODO: get the delivery on date
            Utils.initTextView(tvDishDeliveryOnDateVal, order.getmOrderedDate());

            bViewCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewCancelOrderClickListener.viewCancelOrderClicked(order);
                }
            });
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
