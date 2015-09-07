package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.adapters.ChefDeliveryOrdersListAdapter;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.FoodieOrder;
import com.apps.b3bytes.homefoods.models.OneDishOrder;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindhu on 8/30/2015.
 */
public class ChefOrderDeliveryFragment extends Fragment {
    private Context mContext;
    private String mOrderStr;
    private TextView tvOrderNum;
    private TextView tvChefDeliveryTotalNumDishes;
    private TextView tvChefDeliveryTotalPriceVal;
    private ListView lvChefDeliveryOrders;
    private Button bChefDeliveryDone;
    private ArrayAdapter<OneDishOrder> aOneDishOrder;
    private List<OneDishOrder> list;

    public ChefOrderDeliveryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mOrderStr = this.getArguments().getString("orderStr");

        View v = inflater.inflate(R.layout.activity_chef_order_delivery, container, false);

        tvOrderNum = (TextView) v.findViewById(R.id.tvOrderNumVal);
        tvChefDeliveryTotalNumDishes = (TextView) v.findViewById(R.id.tvChefDeliveryTotalNumDishes);
        tvChefDeliveryTotalPriceVal = (TextView) v.findViewById(R.id.tvChefDeliveryTotalPriceVal);
        bChefDeliveryDone = (Button) v.findViewById(R.id.bChefDeliveryDone);
        list = new ArrayList<OneDishOrder>();
        lvChefDeliveryOrders = (ListView) v.findViewById(R.id.lvChefDeliveryOrders);
        aOneDishOrder = new ChefDeliveryOrdersListAdapter(getActivity(), list, lvChefDeliveryOrders);
        lvChefDeliveryOrders.setAdapter(aOneDishOrder);

        tvOrderNum.setText(mOrderStr);
        return v;
    }
    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Query Parse for Foodie Order
        AppGlobalState.gDataLayer.getFoodieOrder(mOrderStr, new DataLayer.GetFoodieOrderCallback() {
            @Override
            public void done(final FoodieOrder foodieOrder, Exception e) {
                if (e != null) {
                    Toast.makeText(mContext, "Failed to retrie foodie order " + e.toString(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    final ChefOrder chefOrder = foodieOrder.getChefOrder(AppGlobalState.getmCurrentFoodie());
                    if (chefOrder != null) {
                        int numDishes = 0;
                        for (DishOrder dishOrder : chefOrder.getmDishOrders()) {
                            numDishes += dishOrder.getmQty();
                            list.add(new OneDishOrder(dishOrder.getmDishOnSale().getmDish().getmDishName(),
                                    dishOrder.getmQty(), dishOrder.getmDishOnSale().getmUnitPrice()));
                        }
                        aOneDishOrder.notifyDataSetChanged();
                        ListViewHelper.setListViewHeightBasedOnChildren(lvChefDeliveryOrders);

                        tvChefDeliveryTotalNumDishes.setText("" + numDishes);
                        tvChefDeliveryTotalPriceVal.setText(mContext.getString(R.string.Rs) + " " +
                                chefOrder.getmTotal());
                        //tvOrderNum.setText(chefOrder.getmTag());
                        bChefDeliveryDone.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                AppGlobalState.gDataLayer.deliverFoodieOrder(foodieOrder, chefOrder, new DataLayer.SaveCallback() {
                                    @Override
                                    public void done(String OrderId, Exception e) {
                                        if (e == null) {
                                            Toast.makeText(mContext, "Delivered Order " + mOrderStr, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, "Failed to update status of Delivered Order " + mOrderStr, Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(mContext, "Oops... no order for this chef in " + mOrderStr, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
