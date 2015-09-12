package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.adapters.DishOrdersListAdapter;
import com.apps.b3bytes.homefoods.adapters.DishReviewsRVAdapter;
import com.apps.b3bytes.homefoods.adapters.FoodieOrderHistoryRVAdapter;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.DishReview;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;
import com.apps.b3bytes.homefoods.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FoodieOrderHistoryFragment extends Fragment {
    /* TODO: TEST DATA */
    String[] dateArray = {"Apr 14, 2015", "Mar 09, 2015", "Aug 15, 2015", "Sep 01, 2015"};
    String[] dishNameArray = {"Dish Name1", "Dish Name2", "Dish Name3", "Dish Name4"};
    String[] chefNameArray = {"Chef Name1", "Chef Name2", "Chef Name3", "Chef Name4"};
    /* TODO: END TEST DATA */

    private FragmentActivity mContext;
    private View rootView;
    private RecyclerView rvOrderHistory;
    private LayoutInflater mInflater;
    private FoodieOrderHistoryRVAdapter rvAdapter;

    public FoodieOrderHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_foodie_order_history, container, false);
        rvOrderHistory = (RecyclerView) rootView.findViewById(R.id.rvOrderHistory);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<DishOrder> items = new ArrayList<DishOrder>();
        for (int i = 0; i < dishNameArray.length; i++) {
            DishOrder order = new DishOrder();

//            order.getmDishOnSale().getmDish().setmDishName(dishNameArray[i]);
  //          order.getmDishOnSale().getmDish().getmChef().setmUserName(chefNameArray[i]);

            items.add(order);
        }

        rvAdapter = new FoodieOrderHistoryRVAdapter(mContext, items);
        rvOrderHistory.setAdapter(rvAdapter);
        rvAdapter.notifyDataSetChanged();
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        rvOrderHistory.addItemDecoration(itemDecoration);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvOrderHistory.setLayoutManager(layoutManager);
        rvAdapter.SetOnItemClickListener(new FoodieOrderHistoryRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(DishOrder order, int position) {
                // Do Nothing for now
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("Order history");
    }
}
