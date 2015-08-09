package com.apps.b3bytes.homefoods.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.Dish;

/**
 * Created by sindhu on 8/8/2015.
 */

public class FoodieResultsAdapter extends RecyclerView.Adapter<FoodieResultsAdapter.DishViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class DishViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTVDishName;
        private TextView mTVDishPrice;
       // private ImageView mIVDishImage;

        public DishViewHolder(View v) {
            super(v);
            mTVDishName = (TextView) v.findViewById(R.id.tvDishName);
            mTVDishPrice = (TextView) v.findViewById(R.id.tvDishPrice);
         //   mIVDishImage = (ImageView) v.findViewById(R.id.ivDishImage);
        }
        public void bindView(Dish dish) {
            mTVDishName.setText(dish.getmDishName());
            mTVDishPrice.setText(String.valueOf(dish.getmPrice()));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FoodieResultsAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FoodieResultsAdapter.DishViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.foodie_result_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        DishViewHolder vh = new DishViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Dish dish = Dish.createDummyDish("Sambar", "Chennai Sambar", "boil water add powder", 150);
        holder.bindView(dish);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}