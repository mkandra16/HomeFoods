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
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.State.Constants;
import com.apps.b3bytes.homefoods.adapters.NavDrawerRVAdapter;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.fragments.ChefDeliveryFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditAvailFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditImageFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditInfoFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditPriceFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishReadonlyFragment;
import com.apps.b3bytes.homefoods.fragments.ChefHomeFragment;
import com.apps.b3bytes.homefoods.fragments.ChefMenuFragment;
import com.apps.b3bytes.homefoods.fragments.ChefReviewFragment;
import com.apps.b3bytes.homefoods.fragments.DishDescFragment;
import com.apps.b3bytes.homefoods.fragments.DishReviewFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieAddBillingAddressFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieAddPaymentCardFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieCartFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieCheckoutFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieGiveDishReviewFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieHomeFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieOrderHistoryFragment;
import com.apps.b3bytes.homefoods.fragments.FoodieViewPastPendingOrderDetailsFragment;
import com.apps.b3bytes.homefoods.fragments.FragmentActionRequestHandler;
import com.apps.b3bytes.homefoods.fragments.LoginFragment;
import com.apps.b3bytes.homefoods.fragments.RegisterContactInfoFragment;
import com.apps.b3bytes.homefoods.fragments.RegisterHobbiesFragment;
import com.apps.b3bytes.homefoods.fragments.RegisterImageFragment;
import com.apps.b3bytes.homefoods.fragments.RegisterNameFragment;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.FoodieOrder;
import com.apps.b3bytes.homefoods.models.NavDrawerItem;
import com.apps.b3bytes.homefoods.utils.Utils;
import com.apps.b3bytes.homefoods.widgets.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements FragmentActionRequestHandler {
    public static final int DISH_SECTION_EDIT_SINGLE = 0;
    public static final int DISH_SECTION_EDIT_ALL = 1;

    // Will be set from integers.xml file dynamically.
    private int DRAWER_LOC_CHEF_HOME;
    private int DRAWER_LOC_CHEF_MENU;
    private int DRAWER_LOC_CHEF_DELIVER;
    private int DRAWER_LOC_CHEF_SIGN_OUT = -1;
    private int DRAWER_LOC_FOODIE_HOME;
    private int DRAWER_LOC_FOODIE_ORDER_HISTORY;
    private int DRAWER_LOC_FOODIE_SIGN_OUT = -1;

    private ChefDishEditInfoFragment infoFragment;
    private ChefDishEditPriceFragment priceFragment;
    private ChefDishEditAvailFragment availFragment;
    private ChefDishEditImageFragment saveFragment;
    private ChefDishReadonlyFragment readFragment;
    private DishReviewFragment dishReviewFragment;
    private FoodieCheckoutFragment checkoutFragment;
    private FoodieAddPaymentCardFragment addPaymentCardFragment;
    private FoodieAddBillingAddressFragment addBillingAddressFragment;
    private Fragment loginFailFragment;
    private Fragment loginSuccessFragment;
    private FoodieCartFragment foodieCartFragment;
    private LoginFragment loginFragment;

    Context context = this;
    private boolean chefMode = false;
    private DrawerLayout mDrawerLayout;
    private LinearLayout llSliderMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private Switch swChefFoodie;
    private TextView tvRegisterAsChef;
    private TextView tvSignIn;
    private int mEditMode;
    private DishOnSale mDish;
    private Foodie mFoodie;
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

    enum User{
        UNKOWN, ANONYMOUS, FOOODIE, CHEF;
    };

    private User currentUser = User.UNKOWN;
    public void moveToUser(User s) {
        tvRegisterAsChef.setVisibility(View.INVISIBLE);
        swChefFoodie.setVisibility(View.INVISIBLE);
        tvSignIn.setVisibility(View.INVISIBLE);
        chefMode = false;
        switch(s) {
            case ANONYMOUS:
                // unregistered user
                tvSignIn.setVisibility(View.VISIBLE);
                // go back to Anonymous home page.
                displayFoodieView(DRAWER_LOC_FOODIE_HOME);
                // Remove SignOut from foodie and chef drawer
                if (currentUser != User.UNKOWN) {
                    if (DRAWER_LOC_FOODIE_SIGN_OUT != -1) {
                        navFoodieDrawerItems.remove(DRAWER_LOC_FOODIE_SIGN_OUT);
                        DRAWER_LOC_FOODIE_SIGN_OUT = -1;
                        foodieAdapter.notifyDataSetChanged();
                    }
                    if (DRAWER_LOC_CHEF_SIGN_OUT != -1) {
                        navChefDrawerItems.remove(DRAWER_LOC_CHEF_SIGN_OUT);
                        DRAWER_LOC_CHEF_SIGN_OUT = -1;
                        chefAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case FOOODIE:
                //temporary
                swChefFoodie.setVisibility(View.VISIBLE);

                Utils._assert(currentUser == User.ANONYMOUS);
                tvRegisterAsChef.setVisibility(View.VISIBLE);
                // Add LogOut button
                navFoodieDrawerItems.add(new NavDrawerItem(getResources().getString(R.string.sign_out), R.mipmap.ic_back));
                DRAWER_LOC_FOODIE_SIGN_OUT = navFoodieDrawerItems.size() -1;
                foodieAdapter.notifyDataSetChanged();
                break;
            case CHEF:
                Utils._assert(currentUser == User.ANONYMOUS || currentUser == User.FOOODIE);
                swChefFoodie.setVisibility(View.VISIBLE);
                chefMode = swChefFoodie.isChecked();
                navChefDrawerItems.add(new NavDrawerItem(getResources().getString(R.string.sign_out), R.mipmap.ic_back));
                DRAWER_LOC_CHEF_SIGN_OUT = navChefDrawerItems.size() -1;
                chefAdapter.notifyDataSetChanged();
                break;
            default:
                Utils.notReached();
                break;
        }
        currentUser = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // This is first Screen, initialize global state of the App.
        AppGlobalState.initialize(getApplicationContext());

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
        // Expected Chef menu items
        DRAWER_LOC_CHEF_HOME = getResources().getInteger(R.integer.chef_home);
        DRAWER_LOC_CHEF_MENU = getResources().getInteger(R.integer.chef_menu);
        DRAWER_LOC_CHEF_DELIVER = getResources().getInteger(R.integer.chef_deliver);

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
        // Expected Chef menu items
        DRAWER_LOC_FOODIE_HOME = getResources().getInteger(R.integer.foodie_home);
        DRAWER_LOC_FOODIE_ORDER_HISTORY = getResources().getInteger(R.integer.foodie_order_history);

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
        tvRegisterAsChef = (TextView) findViewById(R.id.tvRegisterAsChef);
        tvSignIn = (TextView) findViewById(R.id.tvSignIn);

        tvRegisterAsChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Display Registration Page",
                        Toast.LENGTH_SHORT).show();
                RegisterNameFragment registerNameFragment = new RegisterNameFragment();
                if (mFoodie == null)
                    mFoodie = new Foodie();

                Bundle args = new Bundle();
                args.putParcelable("foodie", mFoodie);
                registerNameFragment.setArguments(args);
                replaceFragment(registerNameFragment);

                setTitle("Register");
                mDrawerLayout.closeDrawer(llSliderMenu);
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We just want to come to current fragment.
                loginFailFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                loginSuccessFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                loginFragment = new LoginFragment();
                replaceFragment(loginFragment);
                mDrawerLayout.closeDrawer(llSliderMenu);
            }
        });

        Foodie user = AppGlobalState.getmCurrentFoodie();
        if (user == null) {
            // anonymous user
            moveToUser(User.ANONYMOUS);
        } else if (user.getmChef() == null) {
            // Fooide
            moveToUser(User.FOOODIE);
        } else {
            // Chef
            moveToUser(User.CHEF);
        }

        swChefFoodie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "The Switch is " + (isChecked ? "on" : "off"),
                        Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    //do stuff when Switch is ON
                    chefMode = true;
                    mRecyclerView.swapAdapter(chefAdapter, false);
                    displayView(DRAWER_LOC_CHEF_HOME);
                } else {
                    //do stuff when Switch if OFF
                    chefMode = false;
                    mRecyclerView.swapAdapter(foodieAdapter, false);
                    displayFoodieView(DRAWER_LOC_FOODIE_HOME);
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
                displayView(DRAWER_LOC_CHEF_HOME);
            else
                displayFoodieView(DRAWER_LOC_FOODIE_HOME);
        }

        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackPressMode = 0;
                onBackPressed();
            }
        });
    }


    // http://stackoverflow.com/questions/18305945/how-to-resume-fragment-from-backstack-if-exists
    private void superOnBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (mBackPressMode == 1) {
            mBackPressMode = 0;
            superOnBackPressed();
            return;
        }

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (currentFragment instanceof ChefDishEditInfoFragment) {
            if (infoFragment.getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof ChefDishEditPriceFragment) {
            if ((mEditMode == DISH_SECTION_EDIT_SINGLE) && priceFragment.getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof ChefDishEditAvailFragment) {
            if ((mEditMode == DISH_SECTION_EDIT_SINGLE) && availFragment.getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof ChefDishEditImageFragment) {
            if ((mEditMode == DISH_SECTION_EDIT_SINGLE) && saveFragment.getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof FoodieGiveDishReviewFragment) {
            if (((FoodieGiveDishReviewFragment) currentFragment).getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof FoodieAddPaymentCardFragment) {
            if (((FoodieAddPaymentCardFragment) currentFragment).getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof FoodieAddBillingAddressFragment) {
            if (((FoodieAddBillingAddressFragment) currentFragment).getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(HomePage.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof RegisterNameFragment) {
            if (((RegisterNameFragment) currentFragment).getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
                builder.setMessage("Cancel Registration?").setPositiveButton("YES", dialogRegisterCancelListener)
                        .setNegativeButton("NO", dialogClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else {
            superOnBackPressed();
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
        if (position == DRAWER_LOC_FOODIE_HOME) {
            fragment = new FoodieHomeFragment();
        } else if (position == DRAWER_LOC_FOODIE_ORDER_HISTORY) {
            fragment = new FoodieOrderHistoryFragment();
        } else if (position == DRAWER_LOC_FOODIE_SIGN_OUT) {
            // We want to switch to FOODIE_HOME modify position accordingly
            position = DRAWER_LOC_FOODIE_HOME;
            fragment = new FoodieHomeFragment();
            AppGlobalState.signOut();
            moveToUser(User.ANONYMOUS);
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
        if (position == DRAWER_LOC_CHEF_HOME) {
            fragment = new ChefHomeFragment();
        } else if (position == DRAWER_LOC_CHEF_MENU) {
            fragment = new ChefMenuFragment();
        } else if (position == DRAWER_LOC_CHEF_DELIVER) {
            fragment = new ChefDeliveryFragment();
        } else if (position == DRAWER_LOC_CHEF_SIGN_OUT) {
            // We want to switch to CHEF_HOME modify position accordingly
            position = DRAWER_LOC_CHEF_HOME;
            fragment = new ChefHomeFragment();
            AppGlobalState.signOut();
            moveToUser(User.ANONYMOUS);
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

    public void ChefDishEditImageFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_BACK_ChefDishEditImageFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                onDishImageBackSelected(dish);
                break;
            }
            case Constants.ACTION_SAVE_ChefDishEditImageFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int mode = bundle.getInt("mode");
                onDishImageSaveSelected(dish, mode);
                break;
            }
            case Constants.ACTION_CANCEL_ChefDishEditImageFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                int mode = bundle.getInt("mode");
                OnDishEditCancelSelected(onChanged, mode);
                break;
            }
            case Constants.ACTION_HOMEUP_ChefDishEditImageFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void ChefDishEditInfoFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_NEXT_ChefDishEditInfoFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                onDishInfoNextSelected(dish);
                break;
            }
            case Constants.ACTION_SAVE_ChefDishEditInfoFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int mode = bundle.getInt("mode");
                onDishImageSaveSelected(dish, mode);
                break;
            }
            case Constants.ACTION_CANCEL_ChefDishEditInfoFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                int mode = bundle.getInt("mode");
                OnDishEditCancelSelected(onChanged, mode);
                break;
            }
            case Constants.ACTION_HOMEUP_ChefDishEditInfoFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void ChefDishEditPriceFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_NEXT_ChefDishEditPriceFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                onDishPriceNextSelected(dish);
                break;
            }
            case Constants.ACTION_BACK_ChefDishEditPriceFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                onDishPriceBackSelected(dish);
                break;
            }
            case Constants.ACTION_SAVE_ChefDishEditPriceFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int mode = bundle.getInt("mode");
                onDishImageSaveSelected(dish, mode);
                break;
            }
            case Constants.ACTION_CANCEL_ChefDishEditPriceFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                int mode = bundle.getInt("mode");
                OnDishEditCancelSelected(onChanged, mode);
                break;
            }
            case Constants.ACTION_HOMEUP_ChefDishEditPriceFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void ChefDishEditAvailFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_NEXT_ChefDishEditAvailFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                onDishAvailNextSelected(dish);
                break;
            }
            case Constants.ACTION_BACK_ChefDishEditAvailFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                onDishAvailBackSelected(dish);
                break;
            }
            case Constants.ACTION_SAVE_ChefDishEditAvailFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int mode = bundle.getInt("mode");
                onDishImageSaveSelected(dish, mode);
                break;
            }
            case Constants.ACTION_CANCEL_ChefDishEditAvailFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                int mode = bundle.getInt("mode");
                OnDishEditCancelSelected(onChanged, mode);
                break;
            }
            case Constants.ACTION_HOMEUP_ChefDishEditAvailFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void ChefMenuFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_DISH_ITEM_CLICK_ChefMenuFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                onChefDishItemClicked(dish);
                break;
            }
            case Constants.ACTION_DISH_ADD_ChefMenuFragment_ID: {
                onChefDishAddClicked();
                break;
            }
        }
    }


    public void ChefDishReadonlyFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_EDIT_ChefDishReadonlyFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int section = bundle.getInt("section");
                OnDishReadOnlyEditSelected(dish, section);
                break;
            }
            case Constants.ACTION_HOMEUP_ChefDishReadonlyFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void FoodieHomeFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_CHECKOUT_CART_FoodieHomeFragment_ID: {
                OnCheckoutCartClicked();
                break;
            }
            case Constants.ACTION_DISH_DESC_FoodieHomeFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                OnDishDescClicked(dish);
                break;
            }
            case Constants.ACTION_DISH_REVIEW_FoodieHomeFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                OnDishReviewsClicked(dish);
                break;
            }
        }
    }

    public void DishDescFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_CHECKOUT_CART_DishDescFragment_ID: {
                OnCheckoutCartClicked();
                break;
            }
            case Constants.ACTION_DISH_REVIEW_DishDescFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                OnDishReviewsClicked(dish);
                break;
            }
            case Constants.ACTION_CHEF_REVIEW_DishDescFragment_ID: {
                Foodie chef = (Foodie) bundle.getParcelable("chef");
                OnChefReviewsClicked(chef);
                break;
            }
            case Constants.ACTION_HOMEUP_DishDescFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void DishReviewFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_HOMEUP_DishReviewFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void ChefReviewFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_HOMEUP_ChefReviewFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void FoodieCartFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_PROCEED_PAYMENT_FoodieCartFragment_ID: {
                if (AppGlobalState.getmCurrentFoodie() != null) {
                    OnProceedToPaymentSelected();
                } else {
                    loginFailFragment = new FoodieCartFragment();
                    loginSuccessFragment = new FoodieCheckoutFragment();
                    loginFragment = new LoginFragment();
                    replaceFragment(loginFragment);
                }
                break;
            }
            case Constants.ACTION_HOMEUP_FoodieCartFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }


    public void FoodieCheckoutFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_ADD_CARD_FoodieCheckoutFragment_ID: {
                OnAddCardSelected();
                break;
            }
            case Constants.ACTION_PLACE_ORDER_FoodieCheckoutFragment_ID: {
                OnPlaceOrderSelected();
                break;
            }
            case Constants.ACTION_HOMEUP_FoodieCheckoutFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void FoodieAddPaymentCardFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_SAVE_CARD_FoodieAddPaymentCardFragment_ID: {
                OnSaveCardSelected();
                break;
            }
            case Constants.ACTION_ADD_BILLING_ADDRESS_FoodieAddPaymentCardFragment_ID: {
                OnAddBillingAddressSelected();
                break;
            }
            case Constants.ACTION_HOMEUP_FoodieAddPaymentCardFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void FoodieAddBillingAddressFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_SAVE_BILLING_ADDRESS_FoodieAddPaymentCardFragment_ID: {
                OnSaveBillingAddressSelected();
                break;
            }
            case Constants.ACTION_HOMEUP_FoodieAddBillingAddressFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void FoodiePastOrdersTabFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_ORDER_DETAILS_FoodiePastOrdersTabFragment_ID: {
                FoodieOrder foodieOrder = (FoodieOrder) bundle.getParcelable("foodieOrder");
                OnOrderDetailsClicked(foodieOrder);
                break;
            }
            case Constants.ACTION_BUY_DISH_AGAIN_FoodiePastOrdersTabFragment_ID: {
                DishOrder dishOrder = (DishOrder) bundle.getParcelable("dishOrder");
                OnBuyDishAgainClicked(dishOrder);
                break;
            }
            case Constants.ACTION_WRITE_DISH_REVIEW_FoodiePastOrdersTabFragment_ID: {
                DishOrder dishOrder = (DishOrder) bundle.getParcelable("dishOrder");
                OnWriteDishReviewClicked(dishOrder);
                break;
            }
        }
    }

    public void FoodiePendingOrdersTabFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_CANCEL_ORDER_FoodiePendingOrdersTabFragment_ID: {
                FoodieOrder foodieOrder = (FoodieOrder) bundle.getParcelable("foodieOrder");
                OnViewCancelOrderClicked(foodieOrder);
                break;
            }
        }
    }

    public void FoodieViewPastPendingOrderDetailsFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_CANCEL_ORDER_FoodieViewPastPendingOrderDetailsFragment_ID: {
                FoodieOrder foodieOrder = (FoodieOrder) bundle.getParcelable("foodieOrder");
                OnPendingOrderCancelClicked(foodieOrder);
                break;
            }
            case Constants.ACTION_BUY_DISH_AGAIN_FoodieViewPastPendingOrderDetailsFragment_ID: {
                DishOrder dishOrder = (DishOrder) bundle.getParcelable("dishOrder");
                OnBuyDishAgainClicked(dishOrder);
                break;
            }
            case Constants.ACTION_WRITE_DISH_REVIEW_FoodieViewPastPendingOrderDetailsFragment_ID: {
                DishOrder dishOrder = (DishOrder) bundle.getParcelable("dishOrder");
                OnWriteDishReviewClicked(dishOrder);
                break;
            }
            case Constants.ACTION_HOMEUP_FoodieViewPastPendingOrderDetailsFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void FoodieGiveDishReviewFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_HOMEUP_FoodieGiveDishReviewFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    DialogInterface.OnClickListener dialogRegisterCancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    // Display the last fragment that invoked the register fragment
                    superOnBackPressed();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    private void OnRegisterCancelSelected(boolean isChanged) {
        //TODO: display the last fragment from cancel registration from any page
        if (isChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
            builder.setMessage("Cancel Registration?").setPositiveButton("YES", dialogRegisterCancelListener)
                    .setNegativeButton("NO", dialogClickListener).show();
        } else {
            // Display the last fragment that invoked the register fragment
            superOnBackPressed();
        }

    }

    private void OnRegisterRegisterSelected() {
        // TODO: send to server.
        // wait for email verification. display verification page or progress bar.
        // For now, go to next page
        RegisterImageFragment registerImageFragment = new RegisterImageFragment();
        Bundle args = new Bundle();
        args.putParcelable("foodie", mFoodie);
        registerImageFragment.setArguments(args);
        replaceFragment(registerImageFragment);
    }

    private void RegisterNameFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_REGISTER_RegisterNameFragment_ID: {
                mFoodie = bundle.getParcelable("foodie");
                OnRegisterRegisterSelected();
                break;
            }
            case Constants.ACTION_CANCEL_RegisterNameFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                OnRegisterCancelSelected(onChanged);
                break;
            }
            case Constants.ACTION_HOMEUP_RegisterNameFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    private void OnRegisterImageNextSelected() {
        RegisterContactInfoFragment registerContactInfoFragment = new RegisterContactInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("foodie", mFoodie);
        registerContactInfoFragment.setArguments(args);
        replaceFragment(registerContactInfoFragment);
    }

    private void RegisterImageFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_NEXT_RegisterImageFragment_ID: {
                mFoodie = bundle.getParcelable("foodie");
                OnRegisterImageNextSelected();
                break;
            }
            case Constants.ACTION_CANCEL_RegisterImageFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                OnRegisterCancelSelected(onChanged);
                break;
            }
            case Constants.ACTION_HOMEUP_RegisterImageFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    private void OnRegisterContactInfoNextSelected() {
        RegisterHobbiesFragment registerHobbiesFragment = new RegisterHobbiesFragment();
        Bundle args = new Bundle();
        args.putParcelable("foodie", mFoodie);
        registerHobbiesFragment.setArguments(args);
        replaceFragment(registerHobbiesFragment);
    }

    private void RegisterContactInfoFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_NEXT_RegisterContactInfoFragment_ID: {
                mFoodie = bundle.getParcelable("foodie");
                OnRegisterContactInfoNextSelected();
                break;
            }
            case Constants.ACTION_CANCEL_RegisterContactInfoFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                OnRegisterCancelSelected(onChanged);
                break;
            }
            case Constants.ACTION_HOMEUP_RegisterContactInfoFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    private void OnRegisterHobbiesSubmitSelected() {
        JSONObject addr = new JSONObject();

        try {
            addr.put("AddrLine1", mFoodie.getmAddr().getmLine1())
                    .put("AddrZip", mFoodie.getmAddr().getmZip())
                    .put("AddrState", mFoodie.getmAddr().getmState())
                    .put("AddrCountry", mFoodie.getmAddr().getmCountry());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject contact = new JSONObject();
        try {
            contact.put("HomePh", mFoodie.getmContact().getmMoblie())
                    .put("Mobile", mFoodie.getmContact().getmMoblie()) /* support for home phone */
                    .put("EmailId", mFoodie.getmContact().getmEmailId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject foodie = new JSONObject();
        try {
            foodie.put("FoodieId", -1)
                    .put("FirstName", mFoodie.getmFirstName())
                    .put("LastName", mFoodie.getmLastName())
                    .put("UserName", mFoodie.getmUserName())
                    .put("Password", mFoodie.getmPassword())
                    .put("Address", addr)
                    .put("ContactDetails", contact)
                    .put("FavoriteFoods", mFoodie.getmFavFoods());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Foodie f = new Foodie(foodie);
        AppGlobalState.gDataLayer.registerFoodie(f, new DataLayer.RegistrationCallback() {
            @Override
            public void done(Foodie f, Exception e) {
                Toast.makeText(getApplicationContext(), "Resgistered Foodie" + f.getmUserName(), Toast.LENGTH_LONG).show();
                //TODO: go to chef home page?
                // hence goto home page
                swChefFoodie.setVisibility(View.VISIBLE);
                tvRegisterAsChef.setVisibility(View.INVISIBLE);
                swChefFoodie.setChecked(true);
                chefMode = swChefFoodie.isChecked();
                //displayView(DRAWER_LOC_CHEF_HOME); this is crashing now
                //displayFoodieView(DRAWER_LOC_FOODIE_HOME);
            }
        });
    }

    private void RegisterHobbiesFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_SUBMIT_RegisterHobbiesFragment_ID: {
                mFoodie = bundle.getParcelable("foodie");
                OnRegisterHobbiesSubmitSelected();
                break;
            }
            case Constants.ACTION_CANCEL_RegisterHobbiesFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                OnRegisterCancelSelected(onChanged);
                break;
            }
            case Constants.ACTION_HOMEUP_RegisterHobbiesFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    private void LoginFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_SIGN_IN_LoginFragment_ID: {
                String uid = bundle.getString("username");
                String password = bundle.getString("password");
                AppGlobalState.signIn(uid, password, new DataLayer.SignInCallback() {
                    public void done(Foodie f, Exception e) {
                        if (e == null) {
                            // login successful
                            if (loginSuccessFragment != null)
                                replaceFragment(loginSuccessFragment);
                            Foodie foodie = AppGlobalState.getmCurrentFoodie();
                            if (foodie.getmChef() != null) {
                                moveToUser(User.CHEF);
                            } else {
                                moveToUser(User.FOOODIE);
                            }
                            getSupportActionBar().show();
                        } else {
                            Toast t = Toast.makeText(getApplicationContext(), "SignIn failed", Toast.LENGTH_LONG);
                            t.show();
                            loginFragment.enableSignInButton();
                        }
                    }
                });
                break;
            }
            case Constants.ACTION_REGISTER_LoginFragment_ID: {
                RegisterNameFragment registerNameFragment = new RegisterNameFragment();

                mFoodie = new Foodie();
                Bundle args = new Bundle();
                args.putParcelable("foodie", mFoodie);
                registerNameFragment.setArguments(args);

                replaceFragment(registerNameFragment);
                break;
            }
            case Constants.ACTION_HOMEUP_LoginFragment_ID: {
                boolean canActivityHandle = bundle.getBoolean("canActivityHandle");
                FragmentHomeUpButton(canActivityHandle);
                break;
            }
        }
    }

    public void fragmentActionRequestHandler(int fragment_id, int action_id, Bundle bundle) {
        switch (fragment_id) {
            case Constants.FRAGMENT_ChefDishEditInfoFragment_ID: {
                ChefDishEditInfoFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefDishEditPriceFragment_ID: {
                ChefDishEditPriceFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefDishEditAvailFragment_ID: {
                ChefDishEditAvailFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefDishEditImageFragment_ID: {
                ChefDishEditImageFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefMenuFragment_ID: {
                ChefMenuFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefDishReadonlyFragment_ID: {
                ChefDishReadonlyFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_FoodieHomeFragment_ID: {
                FoodieHomeFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_DishDescFragment_ID: {
                DishDescFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_DishReviewFragment_ID: {
                DishReviewFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_FoodieCartFragment_ID: {
                FoodieCartFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_FoodieCheckoutFragment_ID: {
                FoodieCheckoutFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_FoodieAddPaymentCardFragment_ID: {
                FoodieAddPaymentCardFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_FoodieAddBillingAddressFragment_ID: {
                FoodieAddBillingAddressFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_FoodiePastOrdersTabFragment_ID: {
                FoodiePastOrdersTabFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_FoodiePendingOrdersTabFragment_ID: {
                FoodiePendingOrdersTabFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_FoodieViewPastPendingOrderDetailsFragment_ID: {
                FoodieViewPastPendingOrderDetailsFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_FoodieGiveDishReviewFragment_ID: {
                FoodieGiveDishReviewFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefReviewFragment_ID: {
                ChefReviewFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_RegisterNameFragment_ID: {
                RegisterNameFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_RegisterImageFragment_ID: {
                RegisterImageFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_RegisterContactInfoFragment_ID: {
                RegisterContactInfoFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_RegisterHobbiesFragment_ID: {
                RegisterHobbiesFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_LoginFragment_ID: {
                LoginFragmentRequestHandler(action_id, bundle);
                break;
            }
        }
    }

    public void FragmentHomeUpButton(boolean canActivityHandle) {
        // Enable/Disable the icon being used by the drawer
        if (canActivityHandle) {
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
                        displayView(DRAWER_LOC_CHEF_MENU);
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
                displayView(DRAWER_LOC_CHEF_MENU);
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
                    displayView(DRAWER_LOC_CHEF_MENU);
                }
            }
        });
    }

    public void OnDishDescClicked(DishOnSale dish) {
        DishDescFragment dishDescFragment = new DishDescFragment();

        Bundle args = new Bundle();
        args.putParcelable("dish", dish);

        dishDescFragment.setArguments(args);
        replaceFragment(dishDescFragment);
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
        FoodieCartFragment fragment = new FoodieCartFragment();
        replaceFragment(fragment);
    }

    public void OnDishReviewsClicked(DishOnSale dish) {
        dishReviewFragment = new DishReviewFragment();

        Bundle args = new Bundle();
        args.putParcelable("dish", dish);

        dishReviewFragment.setArguments(args);
        replaceFragment(dishReviewFragment);
    }

    public void OnChefReviewsClicked(Foodie chef) {
        ChefReviewFragment chefReviewFragment = new ChefReviewFragment();

        Bundle args = new Bundle();
        args.putParcelable("chef", chef);

        chefReviewFragment.setArguments(args);
        replaceFragment(chefReviewFragment);
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
        AppGlobalState.checkOutCart(new DataLayer.SaveCallback() {
            @Override
            public void done(String OrderId, Exception e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Placed order : " + OrderId, Toast.LENGTH_SHORT).show();
                    displayFoodieView(DRAWER_LOC_FOODIE_HOME);
                    AppGlobalState.gCart.clear();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void OnOrderDetailsClicked(FoodieOrder foodieOrder) {
        FoodieViewPastPendingOrderDetailsFragment fragment = new FoodieViewPastPendingOrderDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable("order", foodieOrder);
        args.putInt("mode", 1);

        fragment.setArguments(args);

        replaceFragment(fragment);
    }

    public void OnViewCancelOrderClicked(FoodieOrder foodieOrder) {
        FoodieViewPastPendingOrderDetailsFragment fragment = new FoodieViewPastPendingOrderDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable("order", foodieOrder);
        args.putInt("mode", 0);

        fragment.setArguments(args);

        replaceFragment(fragment);
    }

    DialogInterface.OnClickListener dialogCancelPendingOrderClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    //TODO: cancel the order. update in database. inform related chefs. any other actions
                    displayFoodieView(DRAWER_LOC_FOODIE_ORDER_HISTORY); // display FoodieOrderHistoryFragment
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    public void OnPendingOrderCancelClicked(FoodieOrder foodieOrder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        builder.setMessage("Are you sure to cancel order?").setPositiveButton("YES", dialogCancelPendingOrderClickListener)
                .setNegativeButton("NO", dialogCancelPendingOrderClickListener).show();
    }

    public void OnBuyDishAgainClicked(DishOrder dishOrder) {
        DishDescFragment fragment = new DishDescFragment();

        if (fragment != null) {

            Bundle args = new Bundle();
            args.putParcelable("dish", dishOrder.getmDishOnSale());

            fragment.setArguments(args);

            replaceFragment(fragment);
        }
    }

    public void OnWriteDishReviewClicked(DishOrder dishOrder) {
        FoodieGiveDishReviewFragment fragment = new FoodieGiveDishReviewFragment();

        if (fragment != null) {

            Bundle args = new Bundle();
            args.putParcelable("dish", dishOrder);

            fragment.setArguments(args);

            replaceFragment(fragment);
        }
    }

}
