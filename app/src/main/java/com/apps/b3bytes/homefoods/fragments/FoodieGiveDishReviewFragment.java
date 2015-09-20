package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.activities.HomePage;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.utils.Utils;

public class FoodieGiveDishReviewFragment extends Fragment {
    private FragmentActivity mContext;
    private LayoutInflater mInflater;
    private DishOrder mDishOrder;
    private View rootView;
    private boolean mAlertDiscardChanges;
    private EditText etDishReviewOneLine;
    private EditText etDishDetailedReview;
    private RatingBar rbDishReviewRateVal;

    FragmentActionRequestHandler mActionRequestCallback;

    public FoodieGiveDishReviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_foodie_give_dish_review, container, false);
        etDishReviewOneLine = (EditText) rootView.findViewById(R.id.etDishReviewOneLine);
        etDishDetailedReview = (EditText) rootView.findViewById(R.id.etDishDetailedReview);
        rbDishReviewRateVal = (RatingBar) rootView.findViewById(R.id.rbDishReviewRateVal);

        mAlertDiscardChanges = false;

        etDishReviewOneLine.addTextChangedListener(textWatcher);
        etDishDetailedReview.addTextChangedListener(textWatcher);
        rbDishReviewRateVal.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                mAlertDiscardChanges = true;
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);

        try {
            mActionRequestCallback = (FragmentActionRequestHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement fragment_action_request_handler");
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            mAlertDiscardChanges = true;
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onDetach() {
        super.onDetach();

        // Tell the Activity that it can now handle menu events once again
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", true);
        mActionRequestCallback.fragmentActionRequestHandler(HomePage.FRAGMENT_FoodieGiveDishReviewFragment_ID,
                HomePage.ACTION_HOMEUP_FoodieGiveDishReviewFragment_ID, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
            mDishOrder = (DishOrder) bundle.getParcelable("dish");

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvDishReviewDishNameVal = (TextView) rootView.findViewById(R.id.tvDishReviewDishNameVal);
        //TODO: what if this dish is not on sale at the time of review?
        Utils.initTextView(tvDishReviewDishNameVal, mDishOrder.getmDishOnSale().getmDish().getmDishName());
    }

    public boolean getmAlertDiscardChanges() {
        return mAlertDiscardChanges;
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        // Tell the Activity to let fragments handle the menu events
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", false);
        mActionRequestCallback.fragmentActionRequestHandler(HomePage.FRAGMENT_FoodieGiveDishReviewFragment_ID,
                HomePage.ACTION_HOMEUP_FoodieGiveDishReviewFragment_ID, args);

        actionBar.setTitle("Dish Review");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }
}
