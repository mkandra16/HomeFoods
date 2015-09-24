package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.State.Constants;
import com.apps.b3bytes.homefoods.adapters.ChefMenuGridViewAdapter;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.DishOnSale;

import java.util.ArrayList;
import java.util.List;

public class ChefMenuFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;

    private GridView gvChefMenu;
    private FloatingActionButton fabAddDish;

    private ChefMenuGridViewAdapter aChefMenuGridView;

    FragmentActionRequestHandler mActionRequestCallback;

    public ChefMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chef_menu, container, false);
        gvChefMenu = (GridView) rootView.findViewById(R.id.gvChefMenu);
        fabAddDish = (FloatingActionButton) rootView.findViewById(R.id.fabAddDish);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Menu");
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
                    + " must implement fragment_action_request_handler");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final List<DishOnSale> list = new ArrayList<DishOnSale>();
        // Query Backend for data.
        AppGlobalState.gDataLayer.getChefPublishedDishes(new DataLayer.GetListCallback<DishOnSale>() {
            @Override
            public void done(ArrayList<DishOnSale> results, Exception e) {
                if (e == null) {
                    list.addAll(results);
                    aChefMenuGridView.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Failed to retrieve Chef published dishes", Toast.LENGTH_SHORT).show();
                }
            }
        });

        aChefMenuGridView = new ChefMenuGridViewAdapter(mContext, list, gvChefMenu);
        gvChefMenu.setAdapter(aChefMenuGridView);
        aChefMenuGridView.notifyDataSetChanged();

        gvChefMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DishOnSale dish = aChefMenuGridView.getItem(position);
                Bundle args = new Bundle();
                args.putParcelable("dish", dish);
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_ChefMenuFragment_ID,
                        Constants.ACTION_DISH_ITEM_CLICK_ChefMenuFragment_ID, args);
            }
        });

        fabAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_ChefMenuFragment_ID,
                        Constants.ACTION_DISH_ADD_ChefMenuFragment_ID, args);
            }
        });
    }

}
