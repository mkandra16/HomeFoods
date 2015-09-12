package com.apps.b3bytes.homefoods.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.DishReview;

import java.util.ArrayList;


public class FoodieOrderHistoryRVAdapter extends RecyclerView.Adapter<FoodieOrderHistoryRVAdapter.ViewHolder> {
    private ArrayList<DishOrder> items;
    private final Activity context;
    private ItemClickListener itemClickListener;

    public FoodieOrderHistoryRVAdapter(Activity context, ArrayList<DishOrder> items) {
        this.context = context;
        this.items = items;
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
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.foodie_order_history_item, viewGroup, false);
        return ViewHolder.newInstance(parent, viewType);

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
//        protected RatingBar rbDishReview;
//        protected TextView tvDishReviewDate;
//        protected ImageView ivFoodie;
//        protected TextView tvFoodieName;
//        protected TextView tvDishReviewOneline;
//        protected TextView tvDishReviewDetails;

        public static ViewHolder newInstance(View parent, int viewType) {
            return new ViewHolder(parent);
        }

        private ViewHolder(View parent) {
            super(parent);
            this.parent = parent;
//            rbDishReview = (RatingBar) parent.findViewById(R.id.rbDishReview);
//            tvDishReviewDate = (TextView) parent.findViewById(R.id.tvDishReviewDate);
//            ivFoodie = (ImageView) parent.findViewById(R.id.ivFoodie);
//            tvFoodieName = (TextView) parent.findViewById(R.id.tvFoodieName);
//            tvDishReviewOneline = (TextView) parent.findViewById(R.id.tvDishReviewOneline);
//            tvDishReviewDetails = (TextView) parent.findViewById(R.id.tvDishReviewDetails);
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
