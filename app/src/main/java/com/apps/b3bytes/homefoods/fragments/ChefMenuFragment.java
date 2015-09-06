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

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.ChefMenuGridViewAdapter;
import com.apps.b3bytes.homefoods.models.DishOnSale;

import java.util.ArrayList;
import java.util.List;

public class ChefMenuFragment extends Fragment {
    /* TODO: TEST DATA */
    //Info
    String[] dishNamesArray = {"Roti Paratha", "Curd Rice", "South Indian Breakfast", "Salad", "Chicken Tikka", "Biryani", "Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"};
    String[] dishInfosArray = {"Roti Paratha", "Curd Rice", "South Indian Breakfast", "Salad", "Chicken Tikka", "Biryani", "Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"};
    String[] dishPrepsArray = {"Roti Paratha", "Curd Rice", "South Indian Breakfast", "Salad", "Chicken Tikka", "Biryani", "Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"};
    String[] dishCuisineArray = {"Roti Paratha", "Curd Rice", "South Indian Breakfast", "Salad", "Chicken Tikka", "Biryani", "Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"};
    boolean[] dishVeganArray = {true, true, true, true, false, false, false, true, false, false, true};

    //Price
    String[] dishMeasureArray = {"Grams", "Grams", "Grams", "Grams", "Grams", "Grams", "Grams", "Grams", "Grams", "Grams", "Grams"};
    double[] dishQtyPerUnit = {75, 120, 175, 90, 125, 150, 250, 25, 75, 80, 40};
    int[] dishQuantitiesArray = {2, 1, 3, 1, 2, 4, 1, 12, 3, 4, 10};
    double[] dishUnitPriceArray = {75, 120, 175, 90, 125, 150, 250, 25, 75, 80, 40};

    //Availability
    String[] dishFromDateArray = {"08/30/2015", "08/30/2015", "08/30/2015", "08/30/2015", "08/30/2015", "08/30/2015", "08/30/2015", "08/30/2015", "08/30/2015", "08/30/2015", "08/30/2015"};
    String[] dishFromTimeArray = {"11:00 AM", "11:00 AM", "11:00 AM", "11:00 AM", "11:00 AM", "11:00 AM", "11:00 AM", "11:00 AM", "11:00 AM", "11:00 AM", "11:00 AM"};
    String[] dishToDateArray = {"09/25/2015", "09/25/2015", "09/25/2015", "09/25/2015", "09/25/2015", "09/25/2015", "09/25/2015", "09/25/2015", "09/25/2015", "09/25/2015", "09/25/2015"};
    String[] dishToTimeArray = {"09:00 PM", "09:00 PM", "09:00 PM", "09:00 PM", "09:00 PM", "09:00 PM", "09:00 PM", "09:00 PM", "09:00 PM", "09:00 PM", "09:00 PM"};
    boolean[] dishPickupArray = {true, true, true, true, false, false, false, true, false, false, true};
    boolean[] dishDeliveryArray = {true, true, true, true, false, false, false, true, false, false, true};

    //Image and misc
    String[] dishAddInfoArray = {"Roti Paratha", "Curd Rice", "South Indian Breakfast", "Salad", "Chicken Tikka", "Biryani", "Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"};

    /* TODO: END TEST DATA */

    private FragmentActivity mContext;
    private View rootView;

    private GridView gvChefMenu;
    private FloatingActionButton fabAddDish;

    private ChefMenuGridViewAdapter aChefMenuGridView;

    OnChefDishItemClickedListener mDishClickCallback;
    OnChefDishItemAddListener mDishAddCallback;


    public ChefMenuFragment() {
    }

    // Container Activity must implement this interface
    public interface OnChefDishItemClickedListener {
        public void onChefDishItemClicked(DishOnSale mDish);
    }

    // Container Activity must implement this interface
    public interface OnChefDishItemAddListener {
        public void onChefDishAddClicked();
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
            mDishClickCallback = (OnChefDishItemClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnChefDishItemClickedListener");
        }

        try {
            mDishAddCallback = (OnChefDishItemAddListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnChefDishItemAddListener");
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<DishOnSale> list = new ArrayList<DishOnSale>();
        int numDishes = dishNamesArray.length;
        for (int i = 0; i < numDishes; i++) {

            DishOnSale dish = new DishOnSale();

            dish.getmDish().setmDishName(dishNamesArray[i]);
            dish.getmDish().setmDishInfo(dishInfosArray[i]);
            dish.getmDish().setmPrepMethod(dishPrepsArray[i]);
            dish.getmDish().setmCusine(dishCuisineArray[i]);
            dish.getmDish().setmVegan(dishVeganArray[i]);

            dish.setmMeasure(dishMeasureArray[i]);
            dish.setmQtyPerUnit(dishQtyPerUnit[i]);
            dish.setmUnitPrice(dishUnitPriceArray[i]);
            dish.setmUnitsOnSale(dishQuantitiesArray[i]);

            dish.setmFromDate(dishFromDateArray[i]);
            dish.setmFromTime(dishFromTimeArray[i]);
            dish.setmToDate(dishToDateArray[i]);
            dish.setmToTime(dishToTimeArray[i]);
            dish.setmPickUp(dishPickupArray[i]);
            dish.setmDelivery(dishDeliveryArray[i]);

            dish.setmDishAddInfo(dishAddInfoArray[i]);

            list.add(dish);
        }

        aChefMenuGridView = new ChefMenuGridViewAdapter(mContext, list, gvChefMenu);
        gvChefMenu.setAdapter(aChefMenuGridView);
        aChefMenuGridView.notifyDataSetChanged();

        gvChefMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DishOnSale dish = aChefMenuGridView.getItem(position);
                mDishClickCallback.onChefDishItemClicked(dish);
            }
        });

        fabAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDishAddCallback.onChefDishAddClicked();
            }
        });
    }

}
