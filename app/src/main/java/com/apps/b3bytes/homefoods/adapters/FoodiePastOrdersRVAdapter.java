package com.apps.b3bytes.homefoods.adapters;

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
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.FoodieOrder;
import com.apps.b3bytes.homefoods.utils.Utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


public class FoodiePastOrdersRVAdapter extends RecyclerView.Adapter<FoodiePastOrdersRVAdapter.ViewHolder> {
    onOrderDetailsClickListener orderDetailsClickListener;
    OnBuyDishAgainClickListener buyDishAgainClickListener;
    OnWriteDishReviewClickListener writeDishReviewClickListener;

    public interface onOrderDetailsClickListener {
        public void orderDetailsClicked(FoodieOrder foodieOrder);
    }

    public interface OnBuyDishAgainClickListener {
        public void buyDishAgainClicked(DishOrder dishOrder);
    }

    public interface OnWriteDishReviewClickListener {
        public void writeDishReviewClicked(DishOrder dishOrder);
    }

    public void setOnAddToCartClickListener(onOrderDetailsClickListener listener) {
        orderDetailsClickListener = listener;
    }

    public void setOnBuyDishAgainClickListener(OnBuyDishAgainClickListener listener) {
        buyDishAgainClickListener = listener;
    }

    public void setOnWriteDishReviewClickListener(OnWriteDishReviewClickListener listener) {
        writeDishReviewClickListener = listener;
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

        public void bindView(DishLinkingFoodieOrder order) {
            final DishOrder dishOrder = order.dishOrder;
            final FoodieOrder foodieOrder = order.foodieOrder;

            //TODO
            //ivDishOrderedImage.setImageResource();
            Utils.initTextView(tvDishOrderedName, dishOrder.getmDishOnSale().getmDish().getmDishName());
            Utils.initTextView(tvDishOrderedFromChef, "From Chef: " + dishOrder.getmFoodie().getmUserName());
            Utils.initTextView(tvDishOrderedStatusVal, foodieOrder.getmOrderStatus().toString());
            Utils.initTextView(tvDishOrderedDateVal, foodieOrder.getmOrderedDate());

            bBuyAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buyDishAgainClickListener.buyDishAgainClicked(dishOrder);
                }
            });

            bWriteReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    writeDishReviewClickListener.writeDishReviewClicked(dishOrder);
                }
            });

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
