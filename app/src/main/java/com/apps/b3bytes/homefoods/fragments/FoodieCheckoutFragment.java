package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.viewPagerChefHomeAdapter;
import com.apps.b3bytes.homefoods.widgets.SlidingTabLayout;

public class FoodieCheckoutFragment extends Fragment {
    private FragmentActivity mContext;

    ViewPager pager;
    viewPagerChefHomeAdapter viewPagerAdapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Orders", "Snapshot"};
    int Numboftabs = 2;

    public FoodieCheckoutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chef_home_page, container, false);
        pager = (ViewPager) rootView.findViewById(R.id.viewPagerChefHome);
        tabs = (SlidingTabLayout) rootView.findViewById(R.id.slidingTabs);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        viewPagerAdapter = new viewPagerChefHomeAdapter(mContext.getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(viewPagerAdapter);

        // Assiging the Sliding Tab Layout View
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.KPTheme_AdriftInDreams_colorAccent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }
}
