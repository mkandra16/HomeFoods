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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.Constants;

public class FoodieAddPaymentCardFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private LayoutInflater mInflater;
    private EditText etCreditOrDebitCard;
    private EditText etExpirationMonth;
    private EditText etExpirationYear;
    private EditText etSecurityCode;
    private TextView tvBillingAddress;
    private Button bAddCard;

    private boolean mAlertDiscardChanges;

    FragmentActionRequestHandler mActionRequestCallback;

    public FoodieAddPaymentCardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_foodie_add_payment_card, container, false);
        etCreditOrDebitCard = (EditText) rootView.findViewById(R.id.etCreditOrDebitCard);
        etExpirationMonth = (EditText) rootView.findViewById(R.id.etExpirationMonth);
        etExpirationYear = (EditText) rootView.findViewById(R.id.etExpirationYear);
        etSecurityCode = (EditText) rootView.findViewById(R.id.etSecurityCode);
        tvBillingAddress = (TextView) rootView.findViewById(R.id.tvBillingAddress);
        bAddCard = (Button) rootView.findViewById(R.id.bAddCard);

        etCreditOrDebitCard.addTextChangedListener(textWatcher);
        etExpirationMonth.addTextChangedListener(textWatcher);
        etExpirationYear.addTextChangedListener(textWatcher);
        etSecurityCode.addTextChangedListener(textWatcher);
        tvBillingAddress.addTextChangedListener(textWatcher);

        tvBillingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_FoodieAddPaymentCardFragment_ID,
                        Constants.ACTION_ADD_BILLING_ADDRESS_FoodieAddPaymentCardFragment_ID, args);
            }
        });

        bAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_FoodieAddPaymentCardFragment_ID,
                        Constants.ACTION_SAVE_CARD_FoodieAddPaymentCardFragment_ID, args);
            }
        });

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
            mActionRequestCallback = (FragmentActionRequestHandler) activity;
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
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_FoodieAddPaymentCardFragment_ID,
                Constants.ACTION_HOMEUP_FoodieAddPaymentCardFragment_ID, args);
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
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", false);
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_FoodieAddPaymentCardFragment_ID,
                Constants.ACTION_HOMEUP_FoodieAddPaymentCardFragment_ID, args);
        
        actionBar.setTitle("Payment Method");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }
}
