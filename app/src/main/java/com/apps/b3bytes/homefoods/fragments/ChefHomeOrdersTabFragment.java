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
import com.apps.b3bytes.homefoods.adapters.ChefTodaysOrdersRVAdapter;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.OneDishOrder;

import java.util.ArrayList;
import java.util.List;

public class ChefHomeOrdersTabFragment extends Fragment {
    protected RecyclerView rvChefHomePageOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chef_home_orders_tab, container, false);
        rvChefHomePageOrders = (RecyclerView) v.findViewById(R.id.rvChefHomePageOrders);

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChefTodaysOrdersRVAdapter adapter = new ChefTodaysOrdersRVAdapter();
        rvChefHomePageOrders.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvChefHomePageOrders.setLayoutManager(layoutManager);
        adapter.SetOnItemClickListener(new ChefTodaysOrdersRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ChefOrder item, int position) {
                // Do Nothing for now
            }
        });

    }

}
