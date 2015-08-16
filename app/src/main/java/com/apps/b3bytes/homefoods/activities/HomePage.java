package com.apps.b3bytes.homefoods.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.NavDrawerRVAdapter;
import com.apps.b3bytes.homefoods.fragments.ChefHomeFragment;
import com.apps.b3bytes.homefoods.fragments.ChefMenuFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieCheckoutFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieHomeFragment;
import com.apps.b3bytes.homefoods.models.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    Context context = this;
    private boolean chefMode = false;
    private DrawerLayout mDrawerLayout;
    private LinearLayout llSliderMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private Switch swChefFoodie;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navChefMenuTitles;
    private TypedArray navChefMenuIcons;
    private List<NavDrawerItem> navChefDrawerItems;
    private String[] navFoodieMenuTitles;
    private TypedArray navFoodieMenuIcons;
    private List<NavDrawerItem> navFoodieDrawerItems;

    private NavDrawerRVAdapter chefAdapter;
    private NavDrawerRVAdapter foodieAdapter;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Chef Home");
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navChefMenuTitles = getResources().getStringArray(R.array.nav_drawer_chef_items);
        // nav drawer icons from resources
        navChefMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_chef_icons);
        navChefDrawerItems = new ArrayList<NavDrawerItem>();
        // adding nav drawer items to array
        for (int i = 0; i < navChefMenuTitles.length; i++) {
            navChefDrawerItems.add(new NavDrawerItem(navChefMenuTitles[i], navChefMenuIcons.getResourceId(i, -1)));
        }
        // Recycle the typed array
        navChefMenuIcons.recycle();

        // load slide menu items
        navFoodieMenuTitles = getResources().getStringArray(R.array.nav_drawer_foodie_items);
        // nav drawer icons from resources
        navFoodieMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_foodie_icons);
        navFoodieDrawerItems = new ArrayList<NavDrawerItem>();
        // adding nav drawer items to array
        for (int i = 0; i < navFoodieMenuTitles.length; i++) {
            navFoodieDrawerItems.add(new NavDrawerItem(navFoodieMenuTitles[i], navFoodieMenuIcons.getResourceId(i, -1)));
        }
        // Recycle the typed array
        navFoodieMenuIcons.recycle();

        chefAdapter = new NavDrawerRVAdapter(navChefDrawerItems);
        foodieAdapter = new NavDrawerRVAdapter(navFoodieDrawerItems);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_slidermenu);
        llSliderMenu = (LinearLayout) findViewById(R.id.llSliderMenu);
        swChefFoodie = (Switch) findViewById(R.id.swChefFoodie);

        chefMode = swChefFoodie.isChecked();
        swChefFoodie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "The Switch is " + (isChecked ? "on" : "off"),
                        Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    //do stuff when Switch is ON
                    chefMode = true;
                    mRecyclerView.swapAdapter(chefAdapter, false);
                } else {
                    //do stuff when Switch if OFF
                    chefMode = false;
                    mRecyclerView.swapAdapter(foodieAdapter, false);
                }
            }
        });

        // setting the nav drawer list adapter
        if (chefMode == true)
            mRecyclerView.setAdapter(chefAdapter);
        else
            mRecyclerView.setAdapter(foodieAdapter);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        chefAdapter.SetOnItemClickListener(new NavDrawerRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(NavDrawerItem item, int position) {
                displayView(position);
            }
        });

        foodieAdapter.SetOnItemClickListener(new NavDrawerRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(NavDrawerItem item, int position) {
                displayFoodieView(position);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolBar, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            if (chefMode == true)
                displayView(0);
            else
                displayFoodieView(0);
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayFoodieView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FoodieHomeFragment();
                break;
            case 1:
                fragment = new FoodieCheckoutFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
/*            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);*/
            setTitle(navFoodieMenuTitles[position]);
            mDrawerLayout.closeDrawer(llSliderMenu);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ChefHomeFragment();
                break;
            case 1:
                fragment = new ChefMenuFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
/*            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);*/
            setTitle(navChefMenuTitles[position]);
            mDrawerLayout.closeDrawer(llSliderMenu);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chef_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(llSliderMenu);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
