package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.activities.HomePage;


public class LoginFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;

    private Button bSignIn;
    private Button bRegister;
    private EditText etUserId;
    private EditText etPassword;

    fragment_action_request_handler mActionRequestCallback;

    // Container Activity must implement this interface
    public interface fragment_action_request_handler {
        public void FragmentActionRequestHandler(int fragment_id, int action_id, Bundle bundle);
    }

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_LoginFragment_ID,
                HomePage.ACTION_HOMEUP_LoginFragment_ID, args);

        //actionBar.setTitle("Register");
    }


    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;

        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mActionRequestCallback = (fragment_action_request_handler) activity;
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
        mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_LoginFragment_ID,
                HomePage.ACTION_HOMEUP_LoginFragment_ID, args);
    }

    private boolean checkForMustData() {
        String name = etUserId.getText().toString();
        String pass = etPassword.getText().toString();

        if (name == null || name.isEmpty()) {
            Toast.makeText(mContext, "Please Enter User Name", Toast.LENGTH_SHORT).show();
            etUserId.requestFocus();
            return false;
        }

        if (pass == null || pass.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Password", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        }

        return true;
    }

    public void enableSignInButton() {
        bSignIn.setEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_log_in, container, false);
        bSignIn = (Button) rootView.findViewById(R.id.bSignIn);
        bRegister = (Button) rootView.findViewById(R.id.bRegister);
        etUserId = (EditText) rootView.findViewById(R.id.etUserId);
        etPassword = (EditText) rootView.findViewById(R.id.etPassword);

        // when we are navigating back, after the sign in,
        // display the fields as disabled, as the user is
        // not needed to enter the information again.
        if (AppGlobalState.getmCurrentFoodie() != null) {
            bSignIn.setEnabled(false);
            bRegister.setEnabled(false);
            etUserId.setEnabled(false);
            etPassword.setEnabled(false);
        }

        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean gotAllData = checkForMustData();
                if (gotAllData) {
                    bSignIn.setEnabled(false);
                    Bundle args = new Bundle();
                    args.putString("username", etUserId.getText().toString());
                    args.putString("password", etPassword.getText().toString());
                    mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_LoginFragment_ID,
                            HomePage.ACTION_SIGN_IN_LoginFragment_ID, args);
                }
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                mActionRequestCallback.FragmentActionRequestHandler(HomePage.FRAGMENT_LoginFragment_ID,
                        HomePage.ACTION_REGISTER_LoginFragment_ID, args);
            }
        });

        return rootView;
    }


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
