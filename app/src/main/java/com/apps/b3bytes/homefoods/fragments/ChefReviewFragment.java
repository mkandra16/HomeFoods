package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.activities.HomePage;
import com.apps.b3bytes.homefoods.adapters.ChefReviewsRVAdapter;
import com.apps.b3bytes.homefoods.models.ChefReview;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.widgets.DividerItemDecoration;

import java.util.ArrayList;


public class ChefReviewFragment extends Fragment {
    /* TODO: TEST DATA */
    float[] chefRatingsArray = {4.0f, 4.5f, 5.0f, 3.5f};
    String[] dateArray = {"Apr 14, 2015", "Mar 09, 2015", "Aug 15, 2015", "Sep 01, 2015"};
    String[] foodieName = {"Foodie Name1", "Foodie Name2", "Foodie Name3", "Foodie Name4"};
    String[] oneLine = {
            "Always fantastic",
            "Marvelous, will recommend this",
            "Out of world, 10 stars",
            "could be made better"
    };
    String[] multiLine = {
            "Foodie Name1's details of the review. Description with full details. can span multiple lines, though as an item will be limited to four lines. But the full description can be viewed by selecting the review item which will show in new page. To see complete review click on the review item.",
            "Foodie Name2's details of the review. Description with full details. can span multiple lines, though as an item will be limited to four lines. But the full description can be viewed by selecting the review item which will show in new page. To see complete review click on the review item.",
            "Foodie Name3's details of the review. Description with full details. can span multiple lines, though as an item will be limited to four lines. But the full description can be viewed by selecting the review item which will show in new page. To see complete review click on the review item.",
            "Foodie Name4's details of the review. Description with full details. can span multiple lines, though as an item will be limited to four lines. But the full description can be viewed by selecting the review item which will show in new page. To see complete review click on the review item."
    };

    /* TODO: END TEST DATA */

    private FragmentActivity mContext;
    private View rootView;
    private RecyclerView rvChefReviews;
    private ChefReviewsRVAdapter rvAdapter;
    private Foodie mChef;

    fragment_action_request_handler mActionRequestCallback;

    // Container Activity must implement this interface
    public interface fragment_action_request_handler {
        public void FragmentActionRequestHandler(int fragment_id, int action_id, Bundle bundle);
    }

    public ChefReviewFragment() {
        mChef = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
            mChef = (Foodie) bundle.getParcelable("chef");

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        // Tell the Activity to let fragments handle the menu events
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", false);
        mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_ChefReviewFragment_ID,
                HomePage.ACTION_HOMEUP_ChefReviewFragment_ID, args);

        actionBar.setTitle(mChef.getmUserName() + " Reviews");
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
    public void onDetach() {
        super.onDetach();

        // Tell the Activity that it can now handle menu events once again
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", true);
        mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_ChefReviewFragment_ID,
                HomePage.ACTION_HOMEUP_ChefReviewFragment_ID, args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chef_reviews, container, false);
        rvChefReviews = (RecyclerView) rootView.findViewById(R.id.rvChefReviews);


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

        ArrayList<ChefReview> items = new ArrayList<ChefReview>();
        for (int i = 0; i < chefRatingsArray.length; i++) {
            ChefReview review = new ChefReview();

            review.setmRating(chefRatingsArray[i]);
            review.setmDate(dateArray[i]);
            review.setmFoodieName(foodieName[i]);
            review.setmOneLine(oneLine[i]);
            review.setmMultiLine(multiLine[i]);

            items.add(review);
        }

        rvAdapter = new ChefReviewsRVAdapter(mContext, items);
        rvChefReviews.setAdapter(rvAdapter);
        rvAdapter.notifyDataSetChanged();
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        rvChefReviews.addItemDecoration(itemDecoration);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvChefReviews.setLayoutManager(layoutManager);
        rvAdapter.SetOnItemClickListener(new ChefReviewsRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ChefReview review, int position) {
                // Do Nothing for now
            }
        });

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }
}
