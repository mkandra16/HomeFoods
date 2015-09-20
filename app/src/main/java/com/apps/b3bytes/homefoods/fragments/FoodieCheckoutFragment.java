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
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.activities.HomePage;

public class FoodieCheckoutFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private TextView tvAddPaymentMethod;
    private Button bPlaceOrder;
    private LayoutInflater mInflater;

    FragmentActionRequestHandler mActionRequestCallback;

    public FoodieCheckoutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_foodie_checkout, container, false);
        tvAddPaymentMethod = (TextView) rootView.findViewById(R.id.tvAddPaymentMethod);
        bPlaceOrder = (Button) rootView.findViewById(R.id.bPlaceOrder);

        tvAddPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                mActionRequestCallback.fragmentActionRequestHandler(HomePage.FRAGMENT_FoodieCheckoutFragment_ID,
                        HomePage.ACTION_ADD_CARD_FoodieCheckoutFragment_ID, args);
            }
        });

        bPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Placing Order", Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                mActionRequestCallback.fragmentActionRequestHandler(HomePage.FRAGMENT_FoodieCheckoutFragment_ID,
                        HomePage.ACTION_PLACE_ORDER_FoodieCheckoutFragment_ID, args);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);

        try {
            mActionRequestCallback = (FragmentActionRequestHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement fragment_action_request_handler");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Tell the Activity that it can now handle menu events once again
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", true);
        mActionRequestCallback.fragmentActionRequestHandler(HomePage.FRAGMENT_FoodieCheckoutFragment_ID,
                HomePage.ACTION_HOMEUP_FoodieCheckoutFragment_ID, args);
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

    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        // Tell the Activity to let fragments handle the menu events
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", false);
        mActionRequestCallback.fragmentActionRequestHandler(HomePage.FRAGMENT_FoodieCheckoutFragment_ID,
                HomePage.ACTION_HOMEUP_FoodieCheckoutFragment_ID, args);

        actionBar.setTitle("Checkout");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }
}
