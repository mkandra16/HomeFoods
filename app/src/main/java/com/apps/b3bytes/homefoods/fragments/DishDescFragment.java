package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.DishOnSale;

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
    private TextView tvDishDetails;
    private ImageView ivChefImage;
    private TextView tvChefName;
    private TextView tvChefDesc;
    private TextView tvDishPrepTab;
    private TextView tvDishIngredientsTab;
    private TextView tvDishNutritionTab;


    public DishDescFragment() {
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
        tvDishDetails = (TextView) rootView.findViewById(R.id.tvDishDetails);
        ivChefImage = (ImageView) rootView.findViewById(R.id.ivChefImage);
        tvChefName = (TextView) rootView.findViewById(R.id.tvChefName);
        tvChefDesc = (TextView) rootView.findViewById(R.id.tvChefDesc);
        tvDishPrepTab = (TextView) rootView.findViewById(R.id.tvDishPrepTab);
        tvDishIngredientsTab = (TextView) rootView.findViewById(R.id.tvDishIngredientsTab);
        tvDishNutritionTab = (TextView) rootView.findViewById(R.id.tvDishNutritionTab);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
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
            initTextView(tvDishPrice, ""+mDish.getmUnitPrice());
            // TODO: display image
            //ivDishImage
            initTextView(tvReviewsThumbsUp, ""+mDish.getmDish().getmThumbsUp());
            initTextView(tvReviewsThumbsDown, ""+mDish.getmDish().getmThumbsDown());
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

}
