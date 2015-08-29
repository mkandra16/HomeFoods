package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.FoodieResultsAdapter;
import com.apps.b3bytes.homefoods.models.DishOnSale;

public class FoodieHomeFragment extends Fragment {
    private FragmentActivity mContext;
    private RecyclerView mRecyclerView;
    private FoodieResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FoodieHomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_foodie_results, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.results_recycler_view);


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


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FoodieResultsAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new FoodieResultsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(DishOnSale item, int position) {
                displayDishDesc(item, position);
            }
        });
    }


    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayDishDesc(DishOnSale item, int position) {
        // update the main content by replacing fragments
        Fragment fragment = new DishDescFragment();

        if (fragment != null) {
            FragmentManager fragmentManager = mContext.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).addToBackStack(null).commit();

            // update selected item and title, then close the drawer
/*            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);*/
            //setTitle(item.getmDish().getmDishName());
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


}
