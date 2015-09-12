package com.apps.b3bytes.homefoods.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.ContextThemeWrapper;
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
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.adapters.NavDrawerRVAdapter;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditAvailFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditImageFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditInfoFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditPriceFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishReadonlyFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDeliveryFragment;
import com.apps.b3bytes.homefoods.fragments.ChefHomeFragment;
import com.apps.b3bytes.homefoods.fragments.ChefMenuFragment;
import com.apps.b3bytes.homefoods.fragments.DishDescFragment;
import com.apps.b3bytes.homefoods.fragments.DishReviewFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieAddBillingAddressFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieAddPaymentCardFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieCartFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieCheckoutFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieHomeFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieOrderHistoryFragment;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.NavDrawerItem;
import com.apps.b3bytes.homefoods.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements
        ChefMenuFragment.OnChefDishItemAddListener,
        ChefMenuFragment.OnChefDishItemClickedListener,
        ChefDishEditInfoFragment.OnDishInfoNextSelectedListener,
        ChefDishEditInfoFragment.OnDishImageSaveSelectedListener,
        ChefDishEditInfoFragment.OnDishEditCancelSelectedListener,
        ChefDishEditInfoFragment.FragmentHomeUpButtonHandler,
        ChefDishEditPriceFragment.OnDishPriceBackSelectedListener,
        ChefDishEditPriceFragment.OnDishPriceNextSelectedListener,
        ChefDishEditPriceFragment.OnDishImageSaveSelectedListener,
        ChefDishEditPriceFragment.OnDishEditCancelSelectedListener,
        ChefDishEditPriceFragment.FragmentHomeUpButtonHandler,
        ChefDishEditAvailFragment.OnDishAvailBackSelectedListener,
        ChefDishEditAvailFragment.OnDishAvailNextSelectedListener,
        ChefDishEditAvailFragment.OnDishImageSaveSelectedListener,
        ChefDishEditAvailFragment.OnDishEditCancelSelectedListener,
        ChefDishEditAvailFragment.FragmentHomeUpButtonHandler,
        ChefDishEditImageFragment.OnDishImageBackSelectedListener,
        ChefDishEditImageFragment.OnDishImageSaveSelectedListener,
        ChefDishEditImageFragment.OnDishEditCancelSelectedListener,
        ChefDishEditImageFragment.FragmentHomeUpButtonHandler,
        ChefDishReadonlyFragment.OnDishReadOnlyEditSelectedListener,
        ChefDishReadonlyFragment.FragmentHomeUpButtonHandler,
        FoodieHomeFragment.OnCheckoutCartClickedListener,
        DishDescFragment.OnCheckoutCartClickedListener,
        DishDescFragment.OnDishReviewsClickedListener,
        DishDescFragment.FragmentHomeUpButtonHandler,
        DishReviewFragment.FragmentHomeUpButtonHandler,
        FoodieCartFragment.FragmentHomeUpButtonHandler,
        FoodieCartFragment.OnProceedToPaymentSelectedListener,
        FoodieCheckoutFragment.FragmentHomeUpButtonHandler,
        FoodieCheckoutFragment.OnAddCardSelectedListener,
        FoodieCheckoutFragment.OnPlaceOrderSelectedListener,
        FoodieAddPaymentCardFragment.FragmentHomeUpButtonHandler,
        FoodieAddPaymentCardFragment.OnSaveCardSelectedListener,
        FoodieAddPaymentCardFragment.OnAddBillingAddressSelectedListener,
        FoodieAddBillingAddressFragment.FragmentHomeUpButtonHandler,
        FoodieAddBillingAddressFragment.OnSaveBillingAddressSelectedListener {

    public static final int DISH_SECTION_EDIT_SINGLE = 0;
    public static final int DISH_SECTION_EDIT_ALL = 1;
    private int mEditMode;
    private DishOnSale mDish;
    private ChefDishEditInfoFragment infoFragment;
    private ChefDishEditPriceFragment priceFragment;
    private ChefDishEditAvailFragment availFragment;
    private ChefDishEditImageFragment saveFragment;
    private ChefDishReadonlyFragment readFragment;
    private DishReviewFragment dishReviewFragment;
    private FoodieCheckoutFragment checkoutFragment;
    private FoodieAddPaymentCardFragment addPaymentCardFragment;
    private FoodieAddBillingAddressFragment addBillingAddressFragment;


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

    private int mBackPressMode = 0;

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
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
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
                    displayView(0);
                } else {
                    //do stuff when Switch if OFF
                    chefMode = false;
                    mRecyclerView.swapAdapter(foodieAdapter, false);
                    displayFoodieView(0);
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

        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackPressMode = 0;
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mBackPressMode == 1) {
            mBackPressMode = 0;
            super.onBackPressed();
            return;
        }

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (currentFragment instanceof ChefDishEditInfoFragment) {
            if (infoFragment.getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                super.onBackPressed();
            }
        } else if (currentFragment instanceof ChefDishEditPriceFragment) {
            if ((mEditMode == DISH_SECTION_EDIT_SINGLE) && priceFragment.getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                super.onBackPressed();
            }
        } else if (currentFragment instanceof ChefDishEditAvailFragment) {
            if ((mEditMode == DISH_SECTION_EDIT_SINGLE) && availFragment.getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                super.onBackPressed();
            }
        } else if (currentFragment instanceof ChefDishEditImageFragment) {
            if ((mEditMode == DISH_SECTION_EDIT_SINGLE) && saveFragment.getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    DialogInterface.OnClickListener dialogToolbarBackClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    mBackPressMode = 1;
                    onBackPressed();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    /**
     * Displaying fragment view for selected nav drawer list item
     */
    private void displayFoodieView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FoodieHomeFragment();
                break;
            case 1:
                fragment = new FoodieCartFragment();
                break;
            case 2:
                fragment = new FoodieOrderHistoryFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            replaceFragment(fragment);

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
            case 2:
                fragment = new ChefDeliveryFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            replaceFragment(fragment);

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
    public void FragmentHomeUpButton(boolean useDrawer) {
        // Enable/Disable the icon being used by the drawer
        if (useDrawer) {
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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

    // http://stackoverflow.com/questions/18305945/how-to-resume-fragment-from-backstack-if-exists
    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_container, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    if (mEditMode == DISH_SECTION_EDIT_ALL)
                        displayView(1);
                    else {
                        Bundle args = readFragment.getArguments();
                        //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
                        if (args == null) {
                            args = new Bundle();
                            args.putParcelable("dish", mDish);
                            readFragment.setArguments(args);
                        } else {
                            args.putParcelable("dish", mDish);
                        }
                        replaceFragment(readFragment);
                    }

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    public void OnDishEditCancelSelected(boolean isChanged, int mode) {
        if (isChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
            builder.setMessage("Discard changes?").setPositiveButton("YES", dialogClickListener)
                    .setNegativeButton("NO", dialogClickListener).show();
        } else {
            if (mode == DISH_SECTION_EDIT_ALL)
                displayView(1);
            else {
                Bundle args = readFragment.getArguments();
                //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
                if (args == null) {
                    args = new Bundle();
                    args.putParcelable("dish", mDish);
                    readFragment.setArguments(args);
                } else {
                    args.putParcelable("dish", mDish);
                }
                replaceFragment(readFragment);
            }
        }
    }

    public void onChefDishItemClicked(DishOnSale dish) {
        mDish = dish;
        readFragment = new ChefDishReadonlyFragment();

        Bundle args = readFragment.getArguments();
        //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
        if (args == null) {
            args = new Bundle();
            args.putParcelable("dish", dish);
            readFragment.setArguments(args);
        } else {
            args.putParcelable("dish", dish);
        }
        replaceFragment(readFragment);
    }

    public void onChefDishAddClicked() {
        mEditMode = DISH_SECTION_EDIT_ALL;
        mDish = new DishOnSale();
        infoFragment = new ChefDishEditInfoFragment();
        priceFragment = new ChefDishEditPriceFragment();
        availFragment = new ChefDishEditAvailFragment();
        saveFragment = new ChefDishEditImageFragment();

        Bundle args = new Bundle();
        args.putParcelable("dish", mDish);
        args.putInt("mode", DISH_SECTION_EDIT_ALL);

        infoFragment.setArguments(args);
        replaceFragment(infoFragment);
    }

    public void onDishInfoNextSelected(DishOnSale dish) {
        mDish = dish;

        Bundle args = priceFragment.getArguments();
        //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
        if (args == null) {
            args = new Bundle();
            args.putParcelable("dish", mDish);
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            priceFragment.setArguments(args);
        } else {
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            args.putParcelable("dish", mDish);
        }
        replaceFragment(priceFragment);
    }

    public void onDishPriceBackSelected(DishOnSale dish) {
        mDish = dish;
        Bundle args = infoFragment.getArguments();
        //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
        if (args == null) {
            args = new Bundle();
            args.putParcelable("dish", mDish);
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            infoFragment.setArguments(args);
        } else {
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            args.putParcelable("dish", mDish);
        }
        replaceFragment(infoFragment);
    }

    public void onDishPriceNextSelected(DishOnSale dish) {
        mDish = dish;
        Bundle args = availFragment.getArguments();
        //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
        if (args == null) {
            args = new Bundle();
            args.putParcelable("dish", mDish);
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            availFragment.setArguments(args);
        } else {
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            args.putParcelable("dish", mDish);
        }
        replaceFragment(availFragment);
    }

    public void onDishAvailBackSelected(DishOnSale dish) {
        mDish = dish;
        Bundle args = priceFragment.getArguments();
        //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
        if (args == null) {
            args = new Bundle();
            args.putParcelable("dish", mDish);
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            priceFragment.setArguments(args);
        } else {
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            args.putParcelable("dish", mDish);
        }
        replaceFragment(priceFragment);
    }

    public void onDishAvailNextSelected(DishOnSale dish) {
        mDish = dish;
        Bundle args = saveFragment.getArguments();
        //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
        if (args == null) {
            args = new Bundle();
            args.putParcelable("dish", mDish);
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            saveFragment.setArguments(args);
        } else {
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            args.putParcelable("dish", mDish);
        }
        replaceFragment(saveFragment);
    }

    public void onDishImageBackSelected(DishOnSale dish) {
        mDish = dish;
        Bundle args = availFragment.getArguments();
        //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
        if (args == null) {
            args = new Bundle();
            args.putParcelable("dish", mDish);
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            availFragment.setArguments(args);
        } else {
            args.putInt("mode", DISH_SECTION_EDIT_ALL);
            args.putParcelable("dish", mDish);
        }
        replaceFragment(availFragment);
    }

    public void onDishImageSaveSelected(DishOnSale dish, int mode) {
        mDish = dish;
        final int saveMode = mode;
        AppGlobalState.gDataLayer.putDishOnSale(dish, new DataLayer.PublishCallback() {
            @Override
            public void done(Exception e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),
                            "Posted Dish for sale. Name : " + mDish.getmDish().getmDishName(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Failed to post Dish for sale. Name : " + mDish.getmDish().getmDishName(),
                            Toast.LENGTH_SHORT).show();
                }

                if (saveMode == HomePage.DISH_SECTION_EDIT_SINGLE) {
                    Bundle args = readFragment.getArguments();
                    //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
                    if (args == null) {
                        args = new Bundle();
                        args.putParcelable("dish", mDish);
                        readFragment.setArguments(args);
                    } else {
                        args.putParcelable("dish", mDish);
                    }
                    replaceFragment(readFragment);
                } else {
                    // display the menu view
                    displayView(1);
                }
            }
        });
    }

    public void OnDishReadOnlyEditSelected(DishOnSale dish, int section) {
        mEditMode = DISH_SECTION_EDIT_SINGLE;
        mDish = dish;

        Fragment fragment = null;

        switch (section) {
            case ChefDishReadonlyFragment.DISH_EDIT_SECTION_INFO:
                fragment = new ChefDishEditInfoFragment();
                infoFragment = (ChefDishEditInfoFragment) fragment;
                break;
            case ChefDishReadonlyFragment.DISH_EDIT_SECTION_PRICE:
                fragment = new ChefDishEditPriceFragment();
                priceFragment = (ChefDishEditPriceFragment) fragment;
                break;
            case ChefDishReadonlyFragment.DISH_EDIT_SECTION_AVAIL:
                fragment = new ChefDishEditAvailFragment();
                availFragment = (ChefDishEditAvailFragment) fragment;
                break;
            case ChefDishReadonlyFragment.DISH_EDIT_SECTION_IMAGE:
                fragment = new ChefDishEditImageFragment();
                saveFragment = (ChefDishEditImageFragment) fragment;
                break;
            default:
        }

        if (fragment != null) {
            Bundle args = fragment.getArguments();
            //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
            if (args == null) {
                args = new Bundle();
                args.putParcelable("dish", mDish);
                args.putInt("mode", DISH_SECTION_EDIT_SINGLE);
                fragment.setArguments(args);
            } else {
                args.putInt("mode", DISH_SECTION_EDIT_SINGLE);
                args.putParcelable("dish", mDish);
            }
            replaceFragment(fragment);
        }
    }

    public void OnCheckoutCartClicked() {
        displayFoodieView(1);
    }

    public void OnDishReviewsClicked(DishOnSale dish) {
        dishReviewFragment = new DishReviewFragment();

        Bundle args = new Bundle();
        args.putParcelable("dish", dish);

        dishReviewFragment.setArguments(args);
        replaceFragment(dishReviewFragment);
    }

    public void OnProceedToPaymentSelected() {
        checkoutFragment = new FoodieCheckoutFragment();

        replaceFragment(checkoutFragment);
    }

    public void OnAddCardSelected() {
        addPaymentCardFragment = new FoodieAddPaymentCardFragment();

        replaceFragment(addPaymentCardFragment);
    }

    public void OnSaveCardSelected() {
        replaceFragment(checkoutFragment);
    }

    public void OnAddBillingAddressSelected() {
        addBillingAddressFragment = new FoodieAddBillingAddressFragment();

        replaceFragment(addBillingAddressFragment);
    }

    public void OnSaveBillingAddressSelected() {
        replaceFragment(addPaymentCardFragment);
    }

    public void OnPlaceOrderSelected() {
        AppGlobalState.checkOutCart();
        //TODO (clear the cart)
        displayFoodieView(0);
    }
}
