package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.FoodieOrder;
import com.apps.b3bytes.homefoods.utils.Utils;

import java.util.Set;

public class FoodieViewPastPendingOrderDetailsFragment extends Fragment {
    private FragmentActivity mContext;
    private FoodieOrder mFoodieOrder;
    private int mMode;
    private LayoutInflater mInflater;
    private LinearLayout llRoot;
    private RelativeLayout rlFoodieViewPastPendingOrderDetailsMainHdr;

    private int currentId;

    FragmentHomeUpButtonHandler mHomeUpHandler;

    public interface FragmentHomeUpButtonHandler {
        public void FragmentHomeUpButton(boolean who);
    }

    public FoodieViewPastPendingOrderDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_foodie_view_past_pending_order_details, container, false);
        llRoot = (LinearLayout) rootView.findViewById(R.id.llRoot);
        rlFoodieViewPastPendingOrderDetailsMainHdr = (RelativeLayout) rootView.findViewById(R.id.rlFoodieViewPastPendingOrderDetailsMainHdr);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mHomeUpHandler = (FragmentHomeUpButtonHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentHomeUpButtonHandler");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Tell the Activity that it can now handle menu events once again
        mHomeUpHandler.FragmentHomeUpButton(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mFoodieOrder = (FoodieOrder) bundle.getParcelable("order");
            mMode = bundle.getInt("mode", 1); // 1 : past orders, 0 - pending orders
        }

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentId = (int) rlFoodieViewPastPendingOrderDetailsMainHdr.getId();
        TextView tvOrderNumberVal = (TextView) rlFoodieViewPastPendingOrderDetailsMainHdr.findViewById(R.id.tvOrderNumberVal);
        TextView tvOrderTotalVal = (TextView) rlFoodieViewPastPendingOrderDetailsMainHdr.findViewById(R.id.tvOrderTotalVal);
        TextView tvOrderDateVal = (TextView) rlFoodieViewPastPendingOrderDetailsMainHdr.findViewById(R.id.tvOrderDateVal);
        Utils.initTextView(tvOrderNumberVal, mFoodieOrder.getmTag());
        Utils.initTextView(tvOrderTotalVal, String.valueOf(mFoodieOrder.getmTotal()));
        Utils.initTextView(tvOrderDateVal, mFoodieOrder.getmOrderedDate());

        Set<ChefOrder> chefOrders = mFoodieOrder.getmChefOrders();
        for (ChefOrder chefOrder : chefOrders) {
            // Chef header
            llRoot.addView(createChefHeaderLayout(chefOrder));
            currentId++;

            // Dishes from this Chef
            Set<DishOrder> dishOrders = chefOrder.getmDishOrders();
            for (DishOrder dishOrder : dishOrders) {
                llRoot.addView(createOneDishOrderLayout(dishOrder));
                currentId++;
            }
        }
    }

    private RelativeLayout createOneDishOrderLayout(DishOrder dishOrder) {
        RelativeLayout rlOneDish = (RelativeLayout) mInflater.inflate(R.layout.foodie_view_past_pending_order_details_chef_one_dish, null, false);
        ImageView ivDishOrderedImage = (ImageView) rlOneDish.findViewById(R.id.ivDishOrderedImage);
        TextView tvDishOrderedName = (TextView) rlOneDish.findViewById(R.id.tvDishOrderedName);
        TextView tvDishUnitPriceVal = (TextView) rlOneDish.findViewById(R.id.tvDishUnitPriceVal);
        TextView tvDishOrderedQtyVal = (TextView) rlOneDish.findViewById(R.id.tvDishOrderedQtyVal);
        Button bBuyAgain = (Button) rlOneDish.findViewById(R.id.bBuyAgain);
        Button bWriteReview = (Button) rlOneDish.findViewById(R.id.bWriteReview);
        //TODO: set dish image
        Utils.initTextView(tvDishOrderedName, dishOrder.getmDishOnSale().getmDish().getmDishName());
        Utils.initTextView(tvDishUnitPriceVal, String.valueOf(dishOrder.getmDishOnSale().getmUnitPrice()));
        Utils.initTextView(tvDishOrderedQtyVal, String.valueOf(dishOrder.getmQty()));

        bBuyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: handle this
            }
        });

        bWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: handle this
            }
        });

        return rlOneDish;
    }

    private RelativeLayout createChefHeaderLayout(ChefOrder chefOrder) {
        RelativeLayout rlChefHeader = (RelativeLayout) mInflater.inflate(R.layout.foodie_view_past_pending_order_details_chef_header, null, false);
        TextView tvChefNameVal = (TextView) rlChefHeader.findViewById(R.id.tvChefNameVal);
        TextView tvOrderTotalVal = (TextView) rlChefHeader.findViewById(R.id.tvOrderTotalVal);
        TextView tvOrderDateVal = (TextView) rlChefHeader.findViewById(R.id.tvOrderDateVal);
        Utils.initTextView(tvChefNameVal, chefOrder.getmFoodie().getmUserName());
        Utils.initTextView(tvOrderTotalVal, String.valueOf(chefOrder.getmTotal()));
        //TODO: populate chef delivery date
        //Utils.initTextView(tvOrderDateVal, chefOrder.getmDeliveryDate());

        return rlChefHeader;
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        // Tell the Activity to let fragments handle the menu events
        mHomeUpHandler.FragmentHomeUpButton(false);

        actionBar.setTitle("Order Details");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }
}
