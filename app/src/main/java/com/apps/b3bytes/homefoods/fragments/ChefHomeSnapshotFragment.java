package com.apps.b3bytes.homefoods.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.ChefHomePageOrdersListAdapter;
import com.apps.b3bytes.homefoods.adapters.ChefTodaysOrdersSnapshotRVAdapter;
import com.apps.b3bytes.homefoods.models.OneDishOrder;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;

import java.util.ArrayList;
import java.util.List;

public class ChefHomeSnapshotFragment extends Fragment {
    /* TODO: TEST DATA */
    String[] dishNamesArray = {"Roti Paratha", "Curd Rice", "South Indian Breakfast", "Salad", "Chicken Tikka", "Biryani", "Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"};
    int[] dishQuantitiesArray = {2, 1, 3, 1, 2, 4, 1, 12, 3, 4, 10};
    double[] dishUnitPriceArray = {75, 120, 175, 90, 125, 150, 250, 25, 75, 80, 40};
    /* TODO: END TEST DATA */

    protected RecyclerView rvChefOrdersSnapshot;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.chef_home_snapshot_tab, container, false);
        rvChefOrdersSnapshot = (RecyclerView) v.findViewById(R.id.rvChefOrdersSnapshot);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<OneDishOrder> list = new ArrayList<OneDishOrder>();
        int numOrders = dishNamesArray.length;
        for (int i = 0; i < numOrders; i++) {
            list.add(new OneDishOrder(dishNamesArray[i], dishQuantitiesArray[i], dishUnitPriceArray[i]));
        }


        ChefTodaysOrdersSnapshotRVAdapter adapter = new ChefTodaysOrdersSnapshotRVAdapter(list);
        rvChefOrdersSnapshot.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvChefOrdersSnapshot.setLayoutManager(layoutManager);
        adapter.SetOnItemClickListener(new ChefTodaysOrdersSnapshotRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(OneDishOrder item, int position) {
                // Do Nothing for now
            }
        });
    }
}
