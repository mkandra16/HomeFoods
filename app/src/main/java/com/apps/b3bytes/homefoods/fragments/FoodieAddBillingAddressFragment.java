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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.apps.b3bytes.homefoods.R;

public class FoodieAddBillingAddressFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private LayoutInflater mInflater;
    private EditText etCardHolderName;
    private EditText etAddress1;
    private EditText etAddress2;
    private EditText etCity;
    private EditText etState;
    private EditText etZipCode;
    private EditText etCountry;
    private EditText etPhoneNumber;

    private boolean mAlertDiscardChanges;

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
        etCardHolderName = (EditText) rootView.findViewById(R.id.etCardHolderName);
        etAddress1 = (EditText) rootView.findViewById(R.id.etAddress1);
        etAddress2 = (EditText) rootView.findViewById(R.id.etAddress2);
        etCity = (EditText) rootView.findViewById(R.id.etCity);
        etState = (EditText) rootView.findViewById(R.id.etState);
        etZipCode = (EditText) rootView.findViewById(R.id.etZipCode);
        etCountry = (EditText) rootView.findViewById(R.id.etCountry);
        etPhoneNumber = (EditText) rootView.findViewById(R.id.etPhoneNumber);

        etCardHolderName.addTextChangedListener(textWatcher);
        etAddress1.addTextChangedListener(textWatcher);
        etAddress2.addTextChangedListener(textWatcher);
        etCity.addTextChangedListener(textWatcher);
        etState.addTextChangedListener(textWatcher);
        etZipCode.addTextChangedListener(textWatcher);
        etCountry.addTextChangedListener(textWatcher);
        etPhoneNumber.addTextChangedListener(textWatcher);

        mAlertDiscardChanges = false;

        return rootView;
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

    public boolean getmAlertDiscardChanges() {
        return mAlertDiscardChanges;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_foodie_fragment_add_billing_address, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_addr:
                mSaveBillingAddressCallback.OnSaveBillingAddressSelected();
                return true;
            default:
                break;
        }
        return false;
    }
}
