package com.apps.b3bytes.homefoods.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.FoodieOrder;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;


public class FoodiePastOrdersRVAdapter extends RecyclerView.Adapter<FoodiePastOrdersRVAdapter.ViewHolder> {
    /* TODO: TEST DATA */
    String[] dateArray = {"Apr 14, 2015", "Mar 09, 2015", "Aug 15, 2015", "Sep 01, 2015"};
    String[] dishNameArray = {"Dish Name1", "Dish Name2", "Dish Name3", "Dish Name4"};
    String[] chefNameArray = {"Chef Name1", "Chef Name2", "Chef Name3", "Chef Name4"};
        /* TODO: END TEST DATA */

    onOrderDetailsClickListener orderDetailsClickListener;

    public interface onOrderDetailsClickListener {
        public void orderDetailsClicked(FoodieOrder foodieOrder);
    }

    public void setOnAddToCartClickListener(onOrderDetailsClickListener listener) {
        orderDetailsClickListener = listener;
    }

    public class DishLinkingFoodieOrder {
        DishOrder dishOrder;
        FoodieOrder foodieOrder;
    }

    private Context mContext;
    private ArrayList<DishLinkingFoodieOrder> items;
    private ItemClickListener itemClickListener;

    public FoodiePastOrdersRVAdapter(Context context) {
        mContext = context;
        this.items = new ArrayList<DishLinkingFoodieOrder>();
        EnumSet<FoodieOrder.OrderStatus> statuses = EnumSet.of(FoodieOrder.OrderStatus.Ordered);
        AppGlobalState.gDataLayer.getFoodieOrders(statuses, new DataLayer.GetFoodieOrdersCallback() {
            @Override
            public void done(ArrayList<FoodieOrder> foodieOrders, Exception e) {
                if (e == null) {
                    for (FoodieOrder foodieOrder : foodieOrders) {
                        List<DishOrder> dishOrders = foodieOrder.getDishOrders();
                        for (DishOrder dishOrder : dishOrders) {
                            DishLinkingFoodieOrder item = new DishLinkingFoodieOrder();
                            item.dishOrder = dishOrder;
                            item.foodieOrder = foodieOrder;
                            items.add(item);
                        }
                    }
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Successfully retrieved all pending orders", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Failed to get all Pending orders", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        for (int i = 0; i < dishNameArray.length; i++) {
//            FoodieOrder order = new FoodieOrder();
//            items.add(order);
//        }
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
        View parent = LayoutInflater.from(context).inflate(R.layout.foodie_past_order_item, viewGroup, false);
        return new ViewHolder(parent);

    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final DishLinkingFoodieOrder item;
        final int pos = position;
        item = items.get(position);
        viewHolder.bindView(item);
        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(item.foodieOrder, pos);
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
        private ImageView ivDishOrderedImage;
        private TextView tvDishOrderedName;
        private TextView tvDishOrderedFromChef;
        private TextView tvDishOrderedStatusVal;
        private TextView tvDishOrderedDateVal;
        private Button bBuyAgain;
        private Button bWriteReview;
        private Button bOrderDetails;

        private ViewHolder(View parent) {
            super(parent);
            this.parent = parent;
            ivDishOrderedImage = (ImageView) parent.findViewById(R.id.ivDishOrderedImage);
            tvDishOrderedName = (TextView) parent.findViewById(R.id.tvDishOrderedName);
            tvDishOrderedFromChef = (TextView) parent.findViewById(R.id.tvDishOrderedFromChef);
            tvDishOrderedStatusVal = (TextView) parent.findViewById(R.id.tvDishOrderedStatusVal);
            tvDishOrderedDateVal = (TextView) parent.findViewById(R.id.tvDishOrderedDateVal);
            bBuyAgain = (Button) parent.findViewById(R.id.bBuyAgain);
            bWriteReview = (Button) parent.findViewById(R.id.bWriteReview);
            bOrderDetails = (Button) parent.findViewById(R.id.bOrderDetails);
        }

        private void initTextView(TextView tvView, String text) {
            if (text != null && !text.isEmpty()) {
                tvView.setText(text);
            }
        }

        public void bindView(DishLinkingFoodieOrder order) {
            DishOrder dishOrder = order.dishOrder;
            final FoodieOrder foodieOrder = order.foodieOrder;

            //TODO
            //ivDishOrderedImage.setImageResource();
            initTextView(tvDishOrderedName, dishOrder.getmDishOnSale().getmDish().getmDishName());
            initTextView(tvDishOrderedFromChef, "From Chef: " + dishOrder.getmFoodie().getmUserName());
            initTextView(tvDishOrderedStatusVal, foodieOrder.getmOrderStatus().toString());
            initTextView(tvDishOrderedDateVal, foodieOrder.getmOrderedDate());


            bOrderDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderDetailsClickListener.orderDetailsClicked(foodieOrder);
                }
            });
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
