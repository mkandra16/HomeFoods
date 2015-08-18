package com.apps.b3bytes.homefoods.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.OneDishOrder;

import org.w3c.dom.Text;

public class ChefDishReadonlyFragment extends Fragment {
    private OneDishOrder mDish;


    public ChefDishReadonlyFragment(){
        mDish = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            mDish = (OneDishOrder) bundle.getParcelable("dish");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chef_dish_read_only, container, false);

        updateFields(rootView);

        return rootView;
    }

    public void updateFields(View rootView) {
        // TODO
        if (mDish != null) {
            TextView tvDishEditDishName = (TextView)(rootView.findViewById(R.id.tvDishEditDishName));
            TextView TvDishEditCuisine = (TextView)(rootView.findViewById(R.id.TvDishEditCuisine));
            //TODO: set remaining fileds
            tvDishEditDishName.setText(mDish.getmDish().getmDishName());
            TvDishEditCuisine.setText("" + mDish.getmDish().getmCusineId()); // TODO: Why is cuisineID int?

        }
    }

    public OneDishOrder getDish() {
        return mDish;
    }
}
