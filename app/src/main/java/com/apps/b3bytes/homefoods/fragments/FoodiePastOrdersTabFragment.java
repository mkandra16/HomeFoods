package com.apps.b3bytes.homefoods.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.FoodiePastOrdersRVAdapter;
import com.apps.b3bytes.homefoods.models.DishOrder;

public class FoodiePastOrdersTabFragment extends Fragment {
    private View rootView;
    private RecyclerView rvFoodiePastOrders;
    private FoodiePastOrdersRVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.foodie_past_orders_tab, container, false);
        rvFoodiePastOrders = (RecyclerView) rootView.findViewById(R.id.rvFoodiePastOrders);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FoodiePastOrdersRVAdapter();
        rvFoodiePastOrders.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFoodiePastOrders.setLayoutManager(layoutManager);
        adapter.SetOnItemClickListener(new FoodiePastOrdersRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(DishOrder item, int position) {
                // Do Nothing for now
            }
        });

    }

}
