package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.activities.HomePage;
import com.apps.b3bytes.homefoods.adapters.FoodieResultsAdapter;
import com.apps.b3bytes.homefoods.models.DishOnSale;

public class FoodieHomeFragment extends Fragment implements FoodieResultsAdapter.onAddToCartClickListener {
    private FragmentActivity mContext;
    private RecyclerView mRecyclerView;
    private FoodieResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button bCartItemsCount;

    fragment_action_request_handler mActionRequestCallback;

    // Container Activity must implement this interface
    public interface fragment_action_request_handler {
        public void FragmentActionRequestHandler(int fragment_id, int action_id, Bundle bundle);
    }

    public FoodieHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_foodie_results, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.results_recycler_view);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mActionRequestCallback = (fragment_action_request_handler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement fragment_action_request_handler");
        }
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
        mAdapter.setOnAddToCartClickListener(this);
        mAdapter.SetOnItemClickListener(new FoodieResultsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(DishOnSale item, int position) {
                displayDishDesc(item, position);
            }
        });
    }

    public void addToCartClicked() {
        bCartItemsCount.setText(String.valueOf(AppGlobalState.gCart.getNumDishesInCart()));
    }

    /**
     * Displaying fragment view for selected nav drawer list item
     */
    private void displayDishDesc(DishOnSale item, int position) {
        // update the main content by replacing fragments
        Fragment fragment = new DishDescFragment();

        if (fragment != null) {
            FragmentManager fragmentManager = mContext.getSupportFragmentManager();
            Bundle args = new Bundle();
            args.putParcelable("dish", item);
            fragment.setArguments(args);
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

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("Home");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_foodie_fragment_home_page, menu);

        MenuItem count = menu.findItem(R.id.action_checkout_cart);
        MenuItemCompat.setActionView(count, R.layout.shopping_cart_update_count);

        bCartItemsCount = (Button) MenuItemCompat.getActionView(count);
        bCartItemsCount.setText(String.valueOf(AppGlobalState.gCart.getNumDishesInCart()));
        bCartItemsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_FoodieHomeFragment_ID,
                        HomePage.ACTION_CHECKOUT_CART_FoodieHomeFragment_ID, args);
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // this is not getting invoked now. hence added a clicklistener to button
            case R.id.action_checkout_cart:
                //mCheckoutCartCallback.OnCheckoutCartClicked();
                return true;
            default:
                break;
        }
        return false;
    }
}
