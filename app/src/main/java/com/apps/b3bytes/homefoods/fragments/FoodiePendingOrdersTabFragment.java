package com.apps.b3bytes.homefoods.fragments;

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
import com.apps.b3bytes.homefoods.models.DishOrder;

public class FoodiePendingOrdersTabFragment extends Fragment {
    private View rootView;
    private RecyclerView rvFoodiePendingOrders;
    private FoodiePendingOrdersRVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.foodie_pending_orders_tab, container, false);
        rvFoodiePendingOrders = (RecyclerView) rootView.findViewById(R.id.rvFoodiePendingOrders);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FoodiePendingOrdersRVAdapter();
        rvFoodiePendingOrders.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFoodiePendingOrders.setLayoutManager(layoutManager);
        adapter.SetOnItemClickListener(new FoodiePendingOrdersRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(DishOrder item, int position) {
                // Do Nothing for now
            }
        });

    }

}
