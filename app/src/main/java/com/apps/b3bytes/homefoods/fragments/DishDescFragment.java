package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.State.Constants;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.squareup.picasso.Picasso;

public class DishDescFragment extends Fragment {
    private FragmentActivity mContext;
    private DishOnSale mDish;
    private TabHost tabHost;
    private TextView tvDishName;
    private TextView tvDishPrice;
    private ImageView ivDishImage;
    private TextView tvReviewsThumbsUp;
    private TextView tvReviewsThumbsDown;
    private TextView tvDishReviews;
    private Button bAddToBag;
    private TextView tvDishDetails;
    private ImageView ivChefImage;
    private TextView tvChefName;
    private TextView tvChefDesc;
    private TextView tvDishPrepTab;
    private TextView tvDishIngredientsTab;
    private TextView tvDishNutritionTab;
    private Button bCartItemsCount;

    FragmentActionRequestHandler mActionRequestCallback;

    public DishDescFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mActionRequestCallback = (FragmentActionRequestHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentActionRequestHandler");
        }
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

        Bundle bundle = getArguments();
        if (bundle != null)
            mDish = (DishOnSale) bundle.getParcelable("dish");

        View rootView = inflater.inflate(R.layout.fragment_dish_desc, container, false);
        tabHost = (TabHost) rootView.findViewById(R.id.tabDishDesc);
        tvDishName = (TextView) rootView.findViewById(R.id.tvDishName);
        tvDishPrice = (TextView) rootView.findViewById(R.id.tvDishPrice);
        ivDishImage = (ImageView) rootView.findViewById(R.id.ivDishImage);
        tvReviewsThumbsUp = (TextView) rootView.findViewById(R.id.tvReviewsThumbsUp);
        tvReviewsThumbsDown = (TextView) rootView.findViewById(R.id.tvReviewsThumbsDown);
        tvDishReviews = (TextView) rootView.findViewById(R.id.tvDishReviews);
        bAddToBag = (Button) rootView.findViewById(R.id.bAddToBag);
        tvDishDetails = (TextView) rootView.findViewById(R.id.tvDishDetails);
        ivChefImage = (ImageView) rootView.findViewById(R.id.ivChefImage);
        tvChefName = (TextView) rootView.findViewById(R.id.tvChefName);
        tvChefDesc = (TextView) rootView.findViewById(R.id.tvChefDesc);
        tvDishPrepTab = (TextView) rootView.findViewById(R.id.tvDishPrepTab);
        tvDishIngredientsTab = (TextView) rootView.findViewById(R.id.tvDishIngredientsTab);
        tvDishNutritionTab = (TextView) rootView.findViewById(R.id.tvDishNutritionTab);

        // TODO: using ivChefImage to see the chef reviews for now.
        ivChefImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putParcelable("chef", mDish.getmDish().getmChef());
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_DishDescFragment_ID,
                        Constants.ACTION_CHEF_REVIEW_DishDescFragment_ID, args);
            }
        });

        bAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Recived Click on Dish : " + mDish.getmDish().getmDishName(), Toast.LENGTH_SHORT).show();
                AppGlobalState.gCart.add_to_bag(mDish);
                bCartItemsCount.setText(String.valueOf(AppGlobalState.gCart.getNumDishesInCart()));
            }
        });

        tvDishReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putParcelable("dish", mDish);
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_DishDescFragment_ID,
                        Constants.ACTION_DISH_REVIEW_DishDescFragment_ID, args);
            }
        });
        return rootView;
    }

    private void initTextView(TextView tvView, String text) {
        if (text != null && !text.isEmpty()) {
            tvView.setText(text);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // create the TabHost that will contain the Tabs
        tabHost.setup();

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Tab One");
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Tab Two");
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("Tab Three");

        // Set the Tab name and the content
        tabSpec1.setIndicator("Prep");
        tabSpec1.setContent(R.id.tab1);


        tabSpec2.setIndicator("Ingredients");
        tabSpec2.setContent(R.id.tab2);


        tabSpec3.setIndicator("Nutrition");
        tabSpec3.setContent(R.id.tab3);

        // Add the tabs to the TabHost to display
        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);

        TextView x = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        x.setTextSize(11);
        x.setAllCaps(false);
        x = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        x.setTextSize(11);
        x.setAllCaps(false);
        x = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        x.setTextSize(11);
        x.setAllCaps(false);

        //http://stackoverflow.com/questions/9826130/tabhost-inside-a-scrollview-forces-it-it-to-scroll-to-the-bottom
        //http://stackoverflow.com/questions/2014305/tabhost-inside-of-a-scrollview-always-scrolls-down-when-a-tab-is-clicked
        // Get the first component and make sure it is focusable. Note you have to use setFocusableInTouchMode and not setFocusable for this to work.
        tvDishName.setFocusableInTouchMode(true);
        tvDishName.requestFocus();

        populateDish();
    }

    private void populateDish() {
        if (mDish != null) {
            initTextView(tvDishName, mDish.getmDish().getmDishName());
            // TODO: display currency
            initTextView(tvDishPrice, "" + mDish.getmUnitPrice());
            // TODO: display image
            if (mDish.getmDish().getmImageUri() != null) {
                Picasso.with(getActivity()).load(mDish.getmDish().getmImageUri()).into(ivDishImage);
               // ivDishImage.setImageURI(mDish.getmDish().getmImageUri());
            } else if (mDish.getmDish().getmImageURL() != null) {
                Picasso.with(getActivity()).load(mDish.getmDish().getmImageURL()).into(ivDishImage);
            }

            initTextView(tvReviewsThumbsUp, "" + mDish.getmDish().getmThumbsUp());
            initTextView(tvReviewsThumbsDown, "" + mDish.getmDish().getmThumbsDown());
            // TODO: add number of reviews
            //tvDishReviews
            initTextView(tvDishDetails, mDish.getmDish().getmDishInfo());
            // TODO: display chef image
            //ivChefImage
            initTextView(tvChefName, mDish.getmDish().getmChef().getmUserName());
            // TODO: display chef desc
            // tvChefDesc
            initTextView(tvDishPrepTab, mDish.getmDish().getmPrepMethod());
            // TODO: ingredients and nutrition info
            initTextView(tvDishIngredientsTab, mDish.getmDish().getmPrepMethod());
            initTextView(tvDishNutritionTab, mDish.getmDish().getmPrepMethod());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tell the Activity to let fragments handle the menu events
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", false);
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_DishDescFragment_ID,
                Constants.ACTION_HOMEUP_DishDescFragment_ID, args);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (mDish != null)
            actionBar.setTitle(mDish.getmDish().getmDishName());
    }


    @Override
    public void onDetach() {
        super.onDetach();

        // Tell the Activity that it can now handle menu events once again
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", true);
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_DishDescFragment_ID,
                Constants.ACTION_HOMEUP_DishDescFragment_ID, args);
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
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_DishDescFragment_ID,
                        Constants.ACTION_CHECKOUT_CART_DishDescFragment_ID, args);
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
