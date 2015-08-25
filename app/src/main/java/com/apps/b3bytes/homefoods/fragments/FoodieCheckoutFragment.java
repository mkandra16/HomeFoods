package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.adapters.DishOrdersListAdapter;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.FoodieDishOrder;
import com.apps.b3bytes.homefoods.models.OneDishOrder;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;

import java.util.ArrayList;
import java.util.List;

public class FoodieCheckoutFragment extends Fragment {
    private FragmentActivity mContext;
    private LayoutInflater mInflater;

    private LinearLayout llRoot;
    private TextView tvOrderSummary;
    private int currentId;


    public FoodieCheckoutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_order_review, container, false);
        llRoot = (LinearLayout) rootView.findViewById(R.id.llRoot);
        tvOrderSummary = (TextView) rootView.findViewById(R.id.tvOrderSummary);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentId = (int) tvOrderSummary.getId();

        // Identify chef count.
        // Create ChefOrderLayout for each chef.
        // Shouldn't the delivery layout be different for each chef?
        //

        for (Foodie c : AppGlobalState.gCart.chefsInCart()) {
            llRoot.addView(createOneChefOrderLayout(c));
            currentId++;
        }

        llRoot.addView(createOrderTotalLayout(currentId, AppGlobalState.gCart.getGrandTotal()));
        currentId++;

        llRoot.addView(createDeliveryAddrLayout(currentId));
        currentId++;

        llRoot.addView(createProceedToPaymentLayout(currentId));
        currentId++;

    }

    private LinearLayout createProceedToPaymentLayout(int currentId) {
        LinearLayout llOrderProceedToPayment = (LinearLayout) mInflater.inflate(R.layout.order_proceed_to_payment, null, false);
        Button bCheckOut = (Button) llOrderProceedToPayment.findViewById(R.id.bOrderProceedToPayment);
        bCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext, "Requested CheckOut", Toast.LENGTH_SHORT).show();
                AppGlobalState.checkOutCart();
            }
        });
        return llOrderProceedToPayment;
    }

    private LinearLayout createOrderTotalLayout(int currentId, double totalPrice) {
        LinearLayout llOrderTotal = (LinearLayout) mInflater.inflate(R.layout.order_total, null, false);
        RelativeLayout rlOrderTotal = (RelativeLayout) llOrderTotal.findViewById(R.id.rlOrderTotal);

        TextView tvOrderTotalPrice = (TextView) rlOrderTotal.findViewById(R.id.tvOrderTotalPrice);
        tvOrderTotalPrice.setText(mContext.getString(R.string.Rs) + " " + totalPrice);

        return llOrderTotal;
    }

    private LinearLayout createDeliveryAddrLayout(int currentId) {
        LinearLayout llOrderTotal = (LinearLayout) mInflater.inflate(R.layout.order_delivery_addr, null, false);

        /* TODO: populate address */

        return llOrderTotal;
    }

    private LinearLayout createOneChefOrderLayout(Foodie chef) {

        LinearLayout llOneChefOrder = (LinearLayout) mInflater.inflate(R.layout.one_chef_order, null, false);
        RelativeLayout rlOneChefOrder = (RelativeLayout) llOneChefOrder.findViewById(R.id.rlOneChefOrder);

        TextView tvChefName = (TextView) rlOneChefOrder.findViewById(R.id.tvChefName);
        tvChefName.setText(chef.getmUserName());

        List<FoodieDishOrder> list =
                new ArrayList<FoodieDishOrder>();
        ListView lvChefOrders = (ListView) rlOneChefOrder.findViewById(R.id.lvChefOrders);
        ArrayAdapter<FoodieDishOrder> aOneDishOrder =
                new DishOrdersListAdapter(mContext, list, lvChefOrders, llOneChefOrder);
        lvChefOrders.setAdapter(aOneDishOrder);

        for (DishOnSale d : AppGlobalState.gCart.chefDishesInCart(chef)) {
            int qty = AppGlobalState.gCart.dishQtyInCart(d);
            list.add(new FoodieDishOrder(d, qty));
        }
        aOneDishOrder.notifyDataSetChanged();
        ListViewHelper.setListViewHeightBasedOnChildren(lvChefOrders);

        return llOneChefOrder;
    }

}
