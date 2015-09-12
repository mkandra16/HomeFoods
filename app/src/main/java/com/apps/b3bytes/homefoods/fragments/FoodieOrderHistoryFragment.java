package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.viewPagerChefHomeAdapter;
import com.apps.b3bytes.homefoods.adapters.viewPagerFoodieOrdersHistoryAdapter;
import com.apps.b3bytes.homefoods.widgets.SlidingTabLayout;

public class FoodieOrderHistoryFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;

    private ViewPager viewPagerOrdersHistory;
    private viewPagerFoodieOrdersHistoryAdapter viewPagerAdapter;
    private SlidingTabLayout ordersHistorySlidingTabs;
    private CharSequence Titles[] = {"Pending", "Past"};
    private int Numboftabs = 2;

    public FoodieOrderHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_foodie_order_history, container, false);
        viewPagerOrdersHistory = (ViewPager) rootView.findViewById(R.id.viewPagerOrdersHistory);
        ordersHistorySlidingTabs = (SlidingTabLayout) rootView.findViewById(R.id.ordersHistorySlidingTabs);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("Order history");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
        // http://stackoverflow.com/questions/15588120/fragmentpageradapter-getitem-is-not-being-triggered
        viewPagerAdapter = new viewPagerFoodieOrdersHistoryAdapter(mContext, getChildFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        viewPagerOrdersHistory.setAdapter(viewPagerAdapter);

        // Assiging the Sliding Tab Layout View
        ordersHistorySlidingTabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        ordersHistorySlidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.KPTheme_AdriftInDreams_colorAccent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        ordersHistorySlidingTabs.setViewPager(viewPagerOrdersHistory);

    }
}
