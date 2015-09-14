package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.activities.HomePage;
import com.apps.b3bytes.homefoods.adapters.FoodiePastOrdersRVAdapter;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.FoodieOrder;

public class FoodiePastOrdersTabFragment extends Fragment implements
        FoodiePastOrdersRVAdapter.onOrderDetailsClickListener,
        FoodiePastOrdersRVAdapter.OnBuyDishAgainClickListener,
        FoodiePastOrdersRVAdapter.OnWriteDishReviewClickListener {
    private Context mContext;
    private View rootView;
    private RecyclerView rvFoodiePastOrders;
    private FoodiePastOrdersRVAdapter adapter;

    fragment_action_request_handler mActionRequestCallback;

    // Container Activity must implement this interface
    public interface fragment_action_request_handler {
        public void FragmentActionRequestHandler(int fragment_id, int action_id, Bundle bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.foodie_past_orders_tab, container, false);
        rvFoodiePastOrders = (RecyclerView) rootView.findViewById(R.id.rvFoodiePastOrders);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        mContext = activity;
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

        adapter = new FoodiePastOrdersRVAdapter(mContext);
        rvFoodiePastOrders.setAdapter(adapter);
        adapter.setOnAddToCartClickListener(this);
        adapter.setOnBuyDishAgainClickListener(this);
        adapter.setOnWriteDishReviewClickListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFoodiePastOrders.setLayoutManager(layoutManager);
        adapter.SetOnItemClickListener(new FoodiePastOrdersRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(FoodieOrder item, int position) {
                // Do Nothing for now
            }
        });

    }

    public void orderDetailsClicked(FoodieOrder foodieOrder) {
        Bundle args = new Bundle();
        args.putParcelable("foodieOrder", foodieOrder);
        mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_FoodiePastOrdersTabFragment_ID,
                HomePage.ACTION_ORDER_DETAILS_FoodiePastOrdersTabFragment_ID, args);
    }

    public void buyDishAgainClicked(DishOrder dishOrder) {
        Bundle args = new Bundle();
        args.putParcelable("dishOrder", dishOrder);
        mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_FoodiePastOrdersTabFragment_ID,
                HomePage.ACTION_BUY_DISH_AGAIN_FoodiePastOrdersTabFragment_ID, args);
    }

    public void writeDishReviewClicked(DishOrder dishOrder) {
        Bundle args = new Bundle();
        args.putParcelable("dishOrder", dishOrder);
        mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_FoodiePastOrdersTabFragment_ID,
                HomePage.ACTION_WRITE_DISH_REVIEW_FoodiePastOrdersTabFragment_ID, args);
    }
}
