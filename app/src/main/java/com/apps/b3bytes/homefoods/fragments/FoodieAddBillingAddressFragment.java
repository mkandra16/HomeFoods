package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;

public class FoodieAddBillingAddressFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private LayoutInflater mInflater;

    FragmentHomeUpButtonHandler mHomeUpHandler;
    OnSaveBillingAddressSelectedListener mSaveBillingAddressCallback;

    public interface FragmentHomeUpButtonHandler {
        public void FragmentHomeUpButton(boolean who);
    }

    public interface OnSaveBillingAddressSelectedListener {
        public void OnSaveBillingAddressSelected();
    }

    public FoodieAddBillingAddressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_foodie_billing_address, container, false);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);

        try {
            mHomeUpHandler = (FragmentHomeUpButtonHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentHomeUpButtonHandler");
        }

        try {
            mSaveBillingAddressCallback = (OnSaveBillingAddressSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSaveBillingAddressSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Tell the Activity that it can now handle menu events once again
        mHomeUpHandler.FragmentHomeUpButton(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        // Tell the Activity to let fragments handle the menu events
        mHomeUpHandler.FragmentHomeUpButton(false);

        actionBar.setTitle("Billing Address");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }
}
