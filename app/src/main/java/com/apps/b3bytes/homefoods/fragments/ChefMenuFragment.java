package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.activities.ChefDishDesc;
import com.apps.b3bytes.homefoods.adapters.ChefMenuGridViewAdapter;
import com.apps.b3bytes.homefoods.models.OneDishOrder;
import com.apps.b3bytes.homefoods.utils.Address;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChefMenuFragment extends Fragment {
    private FragmentActivity mContext;
    private GridView gvChefMenu;
    private FloatingActionButton fabAddDish;
    private ChefMenuGridViewAdapter aChefMenuGridView;
    public static final int DISH_EDIT_CHILD = 1005;
    public static final int DISH_GRID_VIEW_CHILD = 1005;

    /* TODO: TEST DATA */
    String[] dishNamesArray = {"Roti Paratha", "Curd Rice", "South Indian Breakfast", "Salad", "Chicken Tikka", "Biryani", "Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"};
    int[] dishQuantitiesArray = {2, 1, 3, 1, 2, 4, 1, 12, 3, 4, 10};
    double[] dishUnitPriceArray = {75, 120, 175, 90, 125, 150, 250, 25, 75, 80, 40};
    /* TODO: END TEST DATA */

    public ChefMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chef_menu, container, false);
        gvChefMenu = (GridView) rootView.findViewById(R.id.gvChefMenu);
        fabAddDish = (FloatingActionButton) rootView.findViewById(R.id.fabAddDish);


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<OneDishOrder> list = new ArrayList<OneDishOrder>();
        int numDishes = dishNamesArray.length;
        for (int i = 0; i < numDishes; i++) {
            list.add(new OneDishOrder(dishNamesArray[i], dishQuantitiesArray[i], dishUnitPriceArray[i]));
        }

        aChefMenuGridView = new ChefMenuGridViewAdapter(mContext, list, gvChefMenu);
        gvChefMenu.setAdapter(aChefMenuGridView);
        aChefMenuGridView.notifyDataSetChanged();

        gvChefMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent in = new Intent(getActivity(), ChefDishDesc.class);
                OneDishOrder dish = aChefMenuGridView.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putBoolean("mode", false);
                bundle.putParcelable("dish", dish);
                in.putExtras(bundle);

                startActivityForResult(in, DISH_GRID_VIEW_CHILD);
            }
        });

        fabAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),
                        ChefDishDesc.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("mode", true);
                bundle.putParcelable("dish", null);
                i.putExtras(bundle);

                startActivityForResult(i, DISH_EDIT_CHILD);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DISH_GRID_VIEW_CHILD) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(mContext, "Successfully Viewed/Edited", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == DISH_EDIT_CHILD) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(mContext, "Successfully Added", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
