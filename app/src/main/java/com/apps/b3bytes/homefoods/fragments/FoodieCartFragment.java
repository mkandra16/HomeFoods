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
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;

import java.util.ArrayList;
import java.util.List;

public class FoodieCartFragment extends Fragment implements DishOrdersListAdapter.onDishQuantitiesUpdatedListener {
    private FragmentActivity mContext;
    private LayoutInflater mInflater;
    private LinearLayout llRoot;
    private TextView tvOrderSummary;
    private TextView tvOrderTotalPrice;

    private int currentId;

    OnProceedToPaymentSelectedListener mProceedToPaymentCallback;
    FragmentHomeUpButtonHandler mHomeUpHandler;

    // Container Activity must implement this interface
    public interface OnProceedToPaymentSelectedListener {
        public void OnProceedToPaymentSelected();
    }

    public interface FragmentHomeUpButtonHandler {
        public void FragmentHomeUpButton(boolean who);
    }

    public FoodieCartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_foodie_cart, container, false);
        llRoot = (LinearLayout) rootView.findViewById(R.id.llRoot);
        tvOrderSummary = (TextView) rootView.findViewById(R.id.tvOrderSummary);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mProceedToPaymentCallback = (OnProceedToPaymentSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnProceedToPaymentSelectedListener");
        }

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

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
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
                mProceedToPaymentCallback.OnProceedToPaymentSelected();
            }
        });
        return llOrderProceedToPayment;
    }

    private LinearLayout createOrderTotalLayout(int currentId, double totalPrice) {
        LinearLayout llOrderTotal = (LinearLayout) mInflater.inflate(R.layout.order_total, null, false);
        RelativeLayout rlOrderTotal = (RelativeLayout) llOrderTotal.findViewById(R.id.rlOrderTotal);

        tvOrderTotalPrice = (TextView) rlOrderTotal.findViewById(R.id.tvOrderTotalPrice);
        tvOrderTotalPrice.setText(mContext.getString(R.string.Rs) + " " + totalPrice);

        return llOrderTotal;
    }

    public void dishQuantitiesUpdated() {
        tvOrderTotalPrice.setText(mContext.getString(R.string.Rs) + " " + AppGlobalState.gCart.getGrandTotal());
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

        List<DishOnSale> list =
                new ArrayList<DishOnSale>();
        ListView lvChefOrders = (ListView) rlOneChefOrder.findViewById(R.id.lvChefOrders);
        ArrayAdapter<DishOnSale> aOneDishOrder =
                new DishOrdersListAdapter(mContext, list, lvChefOrders, llOneChefOrder);
        ((DishOrdersListAdapter) aOneDishOrder).setOnDishQuantitiesUpdatedListener(this);
        lvChefOrders.setAdapter(aOneDishOrder);

        for (DishOnSale d : AppGlobalState.gCart.chefDishesInCart(chef)) {
            list.add(d);
        }
        aOneDishOrder.notifyDataSetChanged();
        ListViewHelper.setListViewHeightBasedOnChildren(lvChefOrders);

        return llOneChefOrder;
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        // Tell the Activity to let fragments handle the menu events
        mHomeUpHandler.FragmentHomeUpButton(false);

        actionBar.setTitle("Cart");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }
}
