package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.FoodiePastOrdersRVAdapter;
import com.apps.b3bytes.homefoods.adapters.FoodieResultsAdapter;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.FoodieOrder;

public class FoodiePastOrdersTabFragment extends Fragment implements FoodiePastOrdersRVAdapter.onOrderDetailsClickListener {
    private Context mContext;
    private View rootView;
    private RecyclerView rvFoodiePastOrders;
    private FoodiePastOrdersRVAdapter adapter;

    OnOrderDetailsListener mOrderDetailsCallback;

    // Container Activity must implement this interface
    public interface OnOrderDetailsListener {
        public void OnOrderDetailsClicked(FoodieOrder foodieOrder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.foodie_past_orders_tab, container, false);
        rvFoodiePastOrders = (RecyclerView) rootView.findViewById(R.id.rvFoodiePastOrders);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        mContext = activity;
        super.onAttach(activity);


        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mOrderDetailsCallback = (OnOrderDetailsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnOrderDetailsListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FoodiePastOrdersRVAdapter(mContext);
        rvFoodiePastOrders.setAdapter(adapter);
        adapter.setOnAddToCartClickListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFoodiePastOrders.setLayoutManager(layoutManager);
        adapter.SetOnItemClickListener(new FoodiePastOrdersRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(FoodieOrder item, int position) {
                // Do Nothing for now
            }
        });

    }

    public void orderDetailsClicked(FoodieOrder foodieOrder) {
        mOrderDetailsCallback.OnOrderDetailsClicked(foodieOrder);
    }
}
