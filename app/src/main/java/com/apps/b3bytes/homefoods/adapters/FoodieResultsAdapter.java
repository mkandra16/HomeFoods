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
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.DishOnSale;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by sindhu on 8/8/2015.
 */

public class FoodieResultsAdapter extends RecyclerView.Adapter<FoodieResultsAdapter.DishViewHolder> {
    private ArrayList<DishOnSale> mdishes;
    private Context mContext;
    private ItemClickListener itemClickListener;

    onAddToCartClickListener addToCartClickListener;

    public void SetOnItemClickListener(final ItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public  interface onAddToCartClickListener {
        public void addToCartClicked();
    }

    public void setOnAddToCartClickListener(onAddToCartClickListener listener) {
        addToCartClickListener = listener;
    }

    public interface ItemClickListener {
        public void onItemClick(DishOnSale item, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class DishViewHolder extends RecyclerView.ViewHolder {
        private View parent;
        // each data item is just a string in this case
        private TextView mTVDishName;
        private TextView mTVDishPrice;
        private TextView mThumbsUp;
        private TextView mThumbsDown;
        private Button mOrderButton;
//        private View mView;
        private DishOnSale dishOnSale;
        private Context mContext;
       // private ImageView mIVDishImage;

        public DishViewHolder(View v, Context context) {
            super(v);
            this.parent = v;
            mTVDishName = (TextView) v.findViewById(R.id.tvDishName);
            mTVDishPrice = (TextView) v.findViewById(R.id.tvDishPrice);
            mThumbsUp = (TextView) v.findViewById(R.id.tvReviewsThumbsUp);
            mThumbsDown = (TextView) v.findViewById(R.id.tvReviewsThumbsDown);
            mOrderButton = (Button) v.findViewById(R.id.bAddToBag);
//            mView = v; // Save View to set tag later :)
            mContext = context;
         //   mIVDishImage = (ImageView) v.findViewById(R.id.ivDishImage);
        }
        public void bindView(DishOnSale dishOnSale) {
            Dish dish = dishOnSale.getmDish();
            mTVDishName.setText(dish.getmDishName());
            mTVDishPrice.setText(mContext.getString(R.string.Rs) + new DecimalFormat("#0.00").format(dishOnSale.getmUnitPrice()));
            mThumbsUp.setText(String.valueOf(dish.getmThumbsUp()));
            mThumbsDown.setText(String.valueOf(dish.getmThumbsDown()));//
            this.dishOnSale = dishOnSale;
            mOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Recived Click on Dish : " + DishViewHolder.this.dishOnSale.getmDish().getmDishName(), Toast.LENGTH_SHORT).show();
                    AppGlobalState.gCart.add_to_bag(DishViewHolder.this.dishOnSale);
                    addToCartClickListener.addToCartClicked();
                }
            });
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FoodieResultsAdapter(Context context) {
        mContext = context;
        Toast.makeText(mContext, "Querying Dishes", Toast.LENGTH_SHORT).show();
        mdishes = new ArrayList<DishOnSale>();
        // Fist query dishes from Parse
        // After response display Foodie results page.
        AppGlobalState.gDataLayer.getNearByDishes(10, new DataLayer.DishQueryCallback() {
            @Override
            public void done(ArrayList<DishOnSale> list, Exception e) {
                if (e == null) {
                    Toast t = Toast.makeText(mContext,
                            "Received Dishes, count " + list.size(), Toast.LENGTH_LONG);
                    t.show();
                    for (DishOnSale d : list) {
                        Toast.makeText(mContext, d.getmDish().getmDishName(), Toast.LENGTH_SHORT);
                    }
                    mdishes = list;
                    notifyDataSetChanged();
                } else {
                    Toast t = Toast.makeText(mContext,
                            "Failed to Retrieve dishes", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });

    }

    // Create new views (invoked by the layout manager)
    @Override
    public FoodieResultsAdapter.DishViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                   int viewType) {
        // create a new view
        View parent = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.foodie_result_card, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
//http://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener-and-how-recyclerview-is-dif
        
        DishViewHolder vh = new DishViewHolder(parent, mContext);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
    //    Dish dish = Dish.createDummyDish("Sambar", "Chennai Sambar", "boil water add powder", 150);
        final DishOnSale item = mdishes.get(position);
        final int pos = position;
        holder.bindView(item);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(item, pos);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mdishes.size();
    }
}