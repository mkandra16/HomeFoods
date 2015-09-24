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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.Constants;
import com.apps.b3bytes.homefoods.models.Foodie;


public class RegisterContactInfoFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private Foodie mFoodie;
    private TextView tvCancel;
    private EditText etAddress1;
    private EditText etAddress2;
    private EditText etCity;
    private EditText etState;
    private EditText etZipCode;
    private EditText etCountry;
    private Button bRegisterContactInfoNext;
    private boolean mAlertDiscardChanges;

    FragmentActionRequestHandler mActionRequestCallback;

    public RegisterContactInfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
            mFoodie = (Foodie) bundle.getParcelable("foodie");

        // getActivity().invalidateOptionsMenu();
        //setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.hide();
        // Tell the Activity to let fragments handle the menu events
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", false);
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterContactInfoFragment_ID,
                Constants.ACTION_HOMEUP_RegisterContactInfoFragment_ID, args);

        //actionBar.setTitle("Register");
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
    public void onDetach() {
        super.onDetach();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.show();
        // Tell the Activity that it can now handle menu events once again
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", true);
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterContactInfoFragment_ID,
                Constants.ACTION_HOMEUP_RegisterContactInfoFragment_ID, args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register_contact_info, container, false);
        tvCancel = (TextView) rootView.findViewById(R.id.tvCancel);
        etAddress1 = (EditText) rootView.findViewById(R.id.etAddress1);
        etAddress2 = (EditText) rootView.findViewById(R.id.etAddress2);
        etCity = (EditText) rootView.findViewById(R.id.etCity);
        etState = (EditText) rootView.findViewById(R.id.etState);
        etZipCode = (EditText) rootView.findViewById(R.id.etZipCode);
        etCountry = (EditText) rootView.findViewById(R.id.etCountry);
        bRegisterContactInfoNext = (Button) rootView.findViewById(R.id.bRegisterContactInfoNext);

        etAddress1.addTextChangedListener(textWatcher);
        etAddress2.addTextChangedListener(textWatcher);
        etCity.addTextChangedListener(textWatcher);
        etState.addTextChangedListener(textWatcher);
        etZipCode.addTextChangedListener(textWatcher);
        etCountry.addTextChangedListener(textWatcher);

        bRegisterContactInfoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean gotAllData = checkForMustData();
                if (gotAllData) {
                    Bundle args = new Bundle();
                    args.putParcelable("foodie", mFoodie);
                    mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterContactInfoFragment_ID,
                            Constants.ACTION_NEXT_RegisterContactInfoFragment_ID, args);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putBoolean("onChanged", mAlertDiscardChanges);
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterContactInfoFragment_ID,
                        Constants.ACTION_CANCEL_RegisterContactInfoFragment_ID, args);
            }
        });

        return rootView;
    }

    private boolean checkForMustData() {
        String addr1 = etAddress1.getText().toString();
        String addr2 = etAddress2.getText().toString();
        String city = etCity.getText().toString();
        String state = etState.getText().toString();
        String zip = etZipCode.getText().toString();
        String country = etCountry.getText().toString();

        if (addr1 == null || addr1.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Street/Home number", Toast.LENGTH_SHORT).show();
            etAddress1.requestFocus();
            return false;
        }
        mFoodie.getmAddr().setmLine1(addr1);

        // address 2 optional if address 1 is provided
        if (addr2 != null && !addr2.isEmpty()) {
            //TODO support for addr2
            //mFoodie.getmAddr().setmLine2(addr2);
        }

        // TODO: validate city and state, country
        if (city == null || city.isEmpty()) {
            Toast.makeText(mContext, "Please Enter City", Toast.LENGTH_SHORT).show();
            etCity.requestFocus();
            return false;
        }
        mFoodie.getmAddr().setmCity(city);

        if (state == null || state.isEmpty()) {
            Toast.makeText(mContext, "Please Enter State", Toast.LENGTH_SHORT).show();
            etState.requestFocus();
            return false;
        }
        mFoodie.getmAddr().setmState(state);

        if (zip == null || zip.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Zip code", Toast.LENGTH_SHORT).show();
            etZipCode.requestFocus();
            return false;
        }
        mFoodie.getmAddr().setmZip(Integer.parseInt(zip));

        if (country == null || country.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Country", Toast.LENGTH_SHORT).show();
            etCountry.requestFocus();
            return false;
        }
        mFoodie.getmAddr().setmCountry(country);

        return true;
    }

    public boolean getmAlertDiscardChanges() {
        return mAlertDiscardChanges;
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_register_fragment, menu);

    }
}
