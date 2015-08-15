package com.apps.b3bytes.homefoods.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.b3bytes.homefoods.R;

public class ChefDishReadonlyFragment extends Fragment {
    public ChefDishReadonlyFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chef_dish_read_only, container, false);

        updateFields();

        return rootView;
    }

    public void updateFields() {
        // TODO
    }
}
