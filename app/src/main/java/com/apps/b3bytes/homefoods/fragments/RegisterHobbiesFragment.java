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

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.Constants;
import com.apps.b3bytes.homefoods.models.Foodie;


public class RegisterHobbiesFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private Foodie mFoodie;
    private TextView tvCancel;
    private EditText etFavoriteFoods;
    private EditText etSpecialities;
    private EditText etHobbies;
    private Button bRegisterSubmit;
    private boolean mAlertDiscardChanges;

    FragmentActionRequestHandler mActionRequestCallback;

    public RegisterHobbiesFragment() {

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
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterHobbiesFragment_ID,
                Constants.ACTION_HOMEUP_RegisterHobbiesFragment_ID, args);

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
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterHobbiesFragment_ID,
                Constants.ACTION_HOMEUP_RegisterHobbiesFragment_ID, args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register_hobbies, container, false);
        tvCancel = (TextView) rootView.findViewById(R.id.tvCancel);
        etFavoriteFoods = (EditText) rootView.findViewById(R.id.etFavoriteFoods);
        etSpecialities = (EditText) rootView.findViewById(R.id.etSpecialities);
        etHobbies = (EditText) rootView.findViewById(R.id.etHobbies);
        bRegisterSubmit = (Button) rootView.findViewById(R.id.bRegisterSubmit);

        etFavoriteFoods.addTextChangedListener(textWatcher);
        etSpecialities.addTextChangedListener(textWatcher);
        etHobbies.addTextChangedListener(textWatcher);

        bRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean gotAllData = checkForMustData();
                if (gotAllData) {
                    Bundle args = new Bundle();
                    args.putParcelable("foodie", mFoodie);
                    mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterHobbiesFragment_ID,
                            Constants.ACTION_SUBMIT_RegisterHobbiesFragment_ID, args);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putBoolean("onChanged", mAlertDiscardChanges);
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterHobbiesFragment_ID,
                        Constants.ACTION_CANCEL_RegisterHobbiesFragment_ID, args);
            }
        });

        return rootView;
    }

    private boolean checkForMustData() {
        String favFoods = etFavoriteFoods.getText().toString();
        String specials = etSpecialities.getText().toString();
        String hobbies = etHobbies.getText().toString();

        //Not making any of the fields as mandatory
        if (favFoods != null || !favFoods.isEmpty()) {
            //Toast.makeText(mContext, "Please Enter your favorite foods", Toast.LENGTH_SHORT).show();
            //etFavoriteFoods.requestFocus();
            //return false;
            mFoodie.setmFavFoods(favFoods);
        }

        // TODO: support for specialities and hobbies
        // address 2 optional if address 1 is provided
        if (specials != null && !specials.isEmpty()) {
            //mFoodie.getmAddr().setmLine2(addr2);
        }

        if (hobbies != null || !hobbies.isEmpty()) {
            //mFoodie.getmAddr().setmCity(city);
        }

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
