package com.apps.b3bytes.homefoods.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.ChefReview;

import java.util.ArrayList;


public class ChefReviewsRVAdapter extends RecyclerView.Adapter<ChefReviewsRVAdapter.ViewHolder> {
    private ArrayList<ChefReview> items;
    private final Activity context;
    private ItemClickListener itemClickListener;

    public ChefReviewsRVAdapter(Activity context, ArrayList<ChefReview> items) {
        this.context = context;
        this.items = items;
    }

    public void SetOnItemClickListener(final ItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        public void onItemClick(ChefReview dish, int position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.chef_reviews_item, viewGroup, false);
        return ViewHolder.newInstance(parent, viewType);

    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final ChefReview item;
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
        protected RatingBar rbChefReview;
        protected TextView tvChefReviewDate;
        protected ImageView ivFoodie;
        protected TextView tvFoodieName;
        protected TextView tvChefReviewOneline;
        protected TextView tvChefReviewDetails;
        protected CheckBox cbChefReviewUseful;

        public static ViewHolder newInstance(View parent, int viewType) {
            return new ViewHolder(parent);
        }

        private ViewHolder(View parent) {
            super(parent);
            this.parent = parent;
            rbChefReview = (RatingBar) parent.findViewById(R.id.rbChefReview);
            tvChefReviewDate = (TextView) parent.findViewById(R.id.tvChefReviewDate);
            ivFoodie = (ImageView) parent.findViewById(R.id.ivFoodie);
            tvFoodieName = (TextView) parent.findViewById(R.id.tvFoodieName);
            tvChefReviewOneline = (TextView) parent.findViewById(R.id.tvChefReviewOneline);
            tvChefReviewDetails = (TextView) parent.findViewById(R.id.tvChefReviewDetails);
            cbChefReviewUseful =  (CheckBox) parent.findViewById(R.id.cbChefReviewUseful);
        }

        public void bindView(ChefReview review) {
            rbChefReview.setRating(review.getmRating());
            tvChefReviewDate.setText(review.getmDate());
            tvFoodieName.setText(review.getmFoodieName());
            tvChefReviewOneline.setText(review.getmOneLine());
            tvChefReviewDetails.setText(review.getmMultiLine());
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
