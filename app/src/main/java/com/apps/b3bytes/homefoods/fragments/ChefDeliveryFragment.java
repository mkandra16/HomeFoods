package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.activities.ChefDishDesc;
import com.apps.b3bytes.homefoods.adapters.ChefMenuGridViewAdapter;
import com.apps.b3bytes.homefoods.models.DishOnSale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavan on 8/30/2015.
 */
public class ChefDeliveryFragment extends Fragment {
    private FragmentActivity mContext;
    private EditText etFoodieOrder;
    private Button bRetrieve;
    public ChefDeliveryFragment() {    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chef_delivery_input_fragment, container, false);
        etFoodieOrder = (EditText) v.findViewById(R.id.etFoodieOrder);
        bRetrieve = (Button) v.findViewById(R.id.bRetrieve);

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
        bRetrieve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String foodieOrderNo = etFoodieOrder.getText().toString();
                Toast.makeText(mContext, "Retrieving Foodie Order " + foodieOrderNo, Toast.LENGTH_SHORT).show();

                // Create new fragment and transaction
                Bundle bundle = new Bundle();
                bundle.putString("orderStr", foodieOrderNo);
                Fragment newFragment = new ChefOrderDeliveryFragment();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
                transaction.replace(R.id.frame_container, newFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

            }
        });
    }
}
