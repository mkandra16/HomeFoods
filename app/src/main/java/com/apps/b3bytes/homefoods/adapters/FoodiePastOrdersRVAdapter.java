package com.apps.b3bytes.homefoods.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.utils.ContactDetails;

import java.util.ArrayList;


public class FoodiePastOrdersRVAdapter extends RecyclerView.Adapter<FoodiePastOrdersRVAdapter.ViewHolder> {
    /* TODO: TEST DATA */
    String[] dateArray = {"Apr 14, 2015", "Mar 09, 2015", "Aug 15, 2015", "Sep 01, 2015"};
    String[] dishNameArray = {"Dish Name1", "Dish Name2", "Dish Name3", "Dish Name4"};
    String[] chefNameArray = {"Chef Name1", "Chef Name2", "Chef Name3", "Chef Name4"};
    /* TODO: END TEST DATA */

    private ArrayList<DishOrder> items;
    private ItemClickListener itemClickListener;

    private Context mContext;
    private View parent;

    public FoodiePastOrdersRVAdapter() {
        this.items = new ArrayList<DishOrder>();
//        // Todo : Get all past orders of Foodie
//        AppGlobalState.gDataLayer.getOrdersForChef(Foodie.createDummyFoodie(), new DataLayer.getChefOrdersCallback() {
//            @Override
//            public void done(ArrayList<ChefOrder> orders, Exception e) {
//                items = orders;
//                notifyDataSetChanged();
//            }
//        });
        for (int i = 0; i < dishNameArray.length; i++) {
            DishOrder order = new DishOrder();

            items.add(order);
        }
    }

    public void SetOnItemClickListener(final ItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        public void onItemClick(DishOrder order, int position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        parent = LayoutInflater.from(mContext).inflate(R.layout.foodie_order_history_item, viewGroup, false);
        return ViewHolder.newInstance(parent, mContext, viewType);

    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final DishOrder item;
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
        private Context context;
        private int currentId;
        private LinearLayout llRoot;
        private RelativeLayout rlFoodieOrderItemMainHdr;

//        protected RatingBar rbDishReview;
//        protected TextView tvDishReviewDate;
//        protected ImageView ivFoodie;
//        protected TextView tvFoodieName;
//        protected TextView tvDishReviewOneline;
//        protected TextView tvDishReviewDetails;

        public static ViewHolder newInstance(View parent, Context context, int viewType) {
            return new ViewHolder(parent, context);
        }

        private ViewHolder(View parent, Context context) {
            super(parent);
            this.parent = parent;
            this.context = context;
            this.llRoot = (LinearLayout) parent.findViewById(R.id.llRoot);
            this.rlFoodieOrderItemMainHdr = (RelativeLayout) parent.findViewById(R.id.rlFoodieOrderItemMainHdr);

            currentId = (int) rlFoodieOrderItemMainHdr.getId();

            this.llRoot.addView(createChefHeaderLayout(currentId));
            currentId++;

            this.llRoot.addView(createChefOneDishLayout(currentId));
            currentId++;

            this.llRoot.addView(createChefOneDishLayout(currentId));
            currentId++;

//            rbDishReview = (RatingBar) parent.findViewById(R.id.rbDishReview);
//            tvDishReviewDate = (TextView) parent.findViewById(R.id.tvDishReviewDate);
//            ivFoodie = (ImageView) parent.findViewById(R.id.ivFoodie);
//            tvFoodieName = (TextView) parent.findViewById(R.id.tvFoodieName);
//            tvDishReviewOneline = (TextView) parent.findViewById(R.id.tvDishReviewOneline);
//            tvDishReviewDetails = (TextView) parent.findViewById(R.id.tvDishReviewDetails);
        }

        private RelativeLayout createChefHeaderLayout(int currentId) {
            RelativeLayout llOrderTotal = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.foodie_order_history_item_chef_header, null, false);

        /* TODO: populate address */

            return llOrderTotal;
        }

        private RelativeLayout createChefOneDishLayout(int currentId) {
            RelativeLayout llOrderTotal = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.foodie_order_history_item_chef_one_dish, null, false);

        /* TODO: populate address */

            return llOrderTotal;
        }

        public void bindView(DishOrder order) {
//            rbDishReview.setRating(review.getmRating());
//            tvDishReviewDate.setText(review.getmDate());
//            tvFoodieName.setText(review.getmFoodieName());
//            tvDishReviewOneline.setText(review.getmOneLine());
//            tvDishReviewDetails.setText(review.getmMultiLine());
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
