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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.Constants;
import com.apps.b3bytes.homefoods.models.Foodie;


public class RegisterImageFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private Foodie mFoodie;
    private TextView tvCancel;
    private TextView tvDisplayNameRule;
    private EditText etDisplayName;
    private RelativeLayout rlProfileImage;
    private ImageView ivProfileImage;
    private TextView tvChangeProfileImage;
    private EditText etPhoneNumber;
    private Button bRegisterImageNext;

    private boolean mAlertDiscardChanges;

    FragmentActionRequestHandler mActionRequestCallback;

    public RegisterImageFragment() {

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
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterImageFragment_ID,
                Constants.ACTION_HOMEUP_RegisterImageFragment_ID, args);

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
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterImageFragment_ID,
                Constants.ACTION_HOMEUP_RegisterImageFragment_ID, args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register_image_info, container, false);
        tvCancel = (TextView) rootView.findViewById(R.id.tvCancel);
        tvDisplayNameRule = (TextView) rootView.findViewById(R.id.tvDisplayNameRule);
        etDisplayName = (EditText) rootView.findViewById(R.id.etDisplayName);
        rlProfileImage = (RelativeLayout) rootView.findViewById(R.id.rlProfileImage);
        ivProfileImage = (ImageView) rootView.findViewById(R.id.ivProfileImage);
        tvChangeProfileImage = (TextView) rootView.findViewById(R.id.tvChangeProfileImage);
        etPhoneNumber = (EditText) rootView.findViewById(R.id.etPhoneNumber);
        bRegisterImageNext = (Button) rootView.findViewById(R.id.bRegisterImageNext);

        etDisplayName.addTextChangedListener(textWatcher);
        etPhoneNumber.addTextChangedListener(textWatcher);
        //ivProfileImage.

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putBoolean("onChanged", mAlertDiscardChanges);
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterImageFragment_ID,
                        Constants.ACTION_CANCEL_RegisterImageFragment_ID, args);
            }
        });

        bRegisterImageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean gotAllData = checkForMustData();
                if (gotAllData) {
                    Bundle args = new Bundle();
                    args.putParcelable("foodie", mFoodie);
                    mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_RegisterImageFragment_ID,
                            Constants.ACTION_NEXT_RegisterImageFragment_ID, args);
                }
            }
        });
        return rootView;
    }

    private boolean checkForMustData() {
        String displayName = etDisplayName.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();

        if (displayName == null || displayName.isEmpty()) {
            Toast.makeText(mContext, "Please choose a Display Name", Toast.LENGTH_SHORT).show();
            etDisplayName.requestFocus();
            return false;
        }
        mFoodie.setmUserName(displayName);

        // TODO: check for valid phone number
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            Toast.makeText(mContext, "Please Enter a valid phone number", Toast.LENGTH_SHORT).show();
            etPhoneNumber.requestFocus();
            return false;
        }
        mFoodie.getmContact().setmMoblie(phoneNumber);

        // TODO
        // if (ivProfileImage.)
        // mFoodie.setmImageURL();

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
