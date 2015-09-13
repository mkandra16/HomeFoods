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
import com.apps.b3bytes.homefoods.adapters.FoodiePendingOrdersRVAdapter;
import com.apps.b3bytes.homefoods.models.FoodieOrder;

public class FoodiePendingOrdersTabFragment extends Fragment implements FoodiePendingOrdersRVAdapter.onViewCancelOrderClickListener {
    private View rootView;
    private RecyclerView rvFoodiePendingOrders;
    private FoodiePendingOrdersRVAdapter adapter;
    private Context mContext;

    OnViewCancelOrderListener mViewCancelOrderCallback;

    // Container Activity must implement this interface
    public interface OnViewCancelOrderListener {
        public void OnViewCancelOrderClicked(FoodieOrder foodieOrder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.foodie_pending_orders_tab, container, false);
        rvFoodiePendingOrders = (RecyclerView) rootView.findViewById(R.id.rvFoodiePendingOrders);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = activity;
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mViewCancelOrderCallback = (OnViewCancelOrderListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnViewCancelOrderListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FoodiePendingOrdersRVAdapter(mContext);
        rvFoodiePendingOrders.setAdapter(adapter);
        adapter.setOnViewCancelOrderClickListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFoodiePendingOrders.setLayoutManager(layoutManager);
        adapter.SetOnItemClickListener(new FoodiePendingOrdersRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(FoodieOrder item, int position) {
                // Do Nothing for now
            }
        });
    }

    public void viewCancelOrderClicked(FoodieOrder foodieOrder) {
        mViewCancelOrderCallback.OnViewCancelOrderClicked(foodieOrder);
    }
}
