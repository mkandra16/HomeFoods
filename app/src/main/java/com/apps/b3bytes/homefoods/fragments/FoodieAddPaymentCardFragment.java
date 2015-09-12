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

public class FoodieAddPaymentCardFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private TextView tvBillingAddress;
    private Button bAddCard;
    private LayoutInflater mInflater;

    FragmentHomeUpButtonHandler mHomeUpHandler;
    OnSaveCardSelectedListener mSaveCardCallback;
    OnAddBillingAddressSelectedListener mAddBillingAddressCallback;

    public interface FragmentHomeUpButtonHandler {
        public void FragmentHomeUpButton(boolean who);
    }

    public interface OnSaveCardSelectedListener {
        public void OnSaveCardSelected();
    }

    public interface OnAddBillingAddressSelectedListener {
        public void OnAddBillingAddressSelected();
    }

    public FoodieAddPaymentCardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_foodie_add_payment_card, container, false);
        bAddCard = (Button) rootView.findViewById(R.id.bAddCard);
        tvBillingAddress = (TextView) rootView.findViewById(R.id.tvBillingAddress);

        tvBillingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddBillingAddressCallback.OnAddBillingAddressSelected();
            }
        });

        bAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveCardCallback.OnSaveCardSelected();
            }
        });
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
            mSaveCardCallback = (OnSaveCardSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSaveCardSelectedListener");
        }

        try {
            mAddBillingAddressCallback = (OnAddBillingAddressSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddBillingAddressSelectedListener");
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

        actionBar.setTitle("Payment Method");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }
}
