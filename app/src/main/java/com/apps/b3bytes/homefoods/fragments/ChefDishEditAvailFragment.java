package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.Constants;
import com.apps.b3bytes.homefoods.activities.ChefDishDesc;
import com.apps.b3bytes.homefoods.activities.HomePage;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ChefDishEditAvailFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private DishOnSale mDish;
    private int mMode;
    private boolean mAlertDiscardChanges;

    private RelativeLayout rlDishEditFromDatePicker;
    private TextView tvDishEditFromDatePicker;
    private TextView tvDishEditFromTimePicker;
    private RelativeLayout rlDishEditToDatePicker;
    private TextView tvDishEditToDatePicker;
    private TextView tvDishEditToTimePicker;
    private LinearLayout llDishEditPickupDelivery;
    private CheckBox cbDishEditPickUp;
    private CheckBox cbDishEditDelivery;
    private LinearLayout llDishAvailNavigationButtons;
    private Button bDishAvailNext;
    private Button bDishAvailBack;
    private LinearLayout llDishAvailSaveButtons;
    private Button bDishAvailSave;

    private int datePickerInput;
    private int timePickerInput;

    FragmentActionRequestHandler mActionRequestCallback;

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

        // Tell the Activity that it can now handle menu events once again
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", true);
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_ChefDishEditAvailFragment_ID,
                Constants.ACTION_HOMEUP_ChefDishEditAvailFragment_ID, args);
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        // Tell the Activity to let fragments handle the menu events
        Bundle args = new Bundle();
        args.putBoolean("canActivityHandle", false);
        mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_ChefDishEditAvailFragment_ID,
                Constants.ACTION_HOMEUP_ChefDishEditAvailFragment_ID, args);

        if (mMode == HomePage.DISH_SECTION_EDIT_ALL)
            actionBar.setTitle("Add Dish");
        else
            actionBar.setTitle(mDish.getmDish().getmDishName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mDish = (DishOnSale) bundle.getParcelable("dish");
            mMode = bundle.getInt("mode", HomePage.DISH_SECTION_EDIT_ALL);
        }

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chef_dish_edit_avail, container, false);
        rlDishEditFromDatePicker = (RelativeLayout) rootView.findViewById(R.id.rlDishEditFromDatePicker);
        tvDishEditFromDatePicker = (TextView) (rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDatePicker));
        tvDishEditFromTimePicker = (TextView) (rlDishEditFromDatePicker.findViewById(R.id.tvDishEditTimePicker));
        rlDishEditToDatePicker = (RelativeLayout) rootView.findViewById(R.id.rlDishEditToDatePicker);
        tvDishEditToDatePicker = (TextView) (rlDishEditToDatePicker.findViewById(R.id.tvDishEditDatePicker));
        tvDishEditToTimePicker = (TextView) (rlDishEditToDatePicker.findViewById(R.id.tvDishEditTimePicker));
        llDishEditPickupDelivery = (LinearLayout) rootView.findViewById(R.id.llDishEditPickupDelivery);
        cbDishEditPickUp = (CheckBox) llDishEditPickupDelivery.findViewById(R.id.cbDishEditPickUp);
        cbDishEditDelivery = (CheckBox) llDishEditPickupDelivery.findViewById(R.id.cbDishEditDelivery);
        llDishAvailNavigationButtons = (LinearLayout) rootView.findViewById(R.id.llDishAvailNavigationButtons);
        bDishAvailNext = (Button) rootView.findViewById(R.id.bDishAvailNext);
        bDishAvailBack = (Button) rootView.findViewById(R.id.bDishAvailBack);
        llDishAvailSaveButtons = (LinearLayout) rootView.findViewById(R.id.llDishAvailSaveButtons);
        bDishAvailSave = (Button) rootView.findViewById(R.id.bDishAvailSave);

        initFields();
        // previous fragments has data
        if (mMode == HomePage.DISH_SECTION_EDIT_ALL)
            mAlertDiscardChanges = true;
        else
            mAlertDiscardChanges = false;

        return rootView;
    }

    public boolean getmAlertDiscardChanges() {
        return mAlertDiscardChanges;
    }

    private void initTextView(TextView tvView, String text) {
        if (text != null && !text.isEmpty()) {
            tvView.setText(text);
        }
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

    private void initFields() {
        //populate  fields if applicable. i.e. mDish != null
        if (mDish != null) {
            initTextView(tvDishEditFromDatePicker, mDish.getmFromDate());
            initTextView(tvDishEditFromTimePicker, mDish.getmFromTime());
            initTextView(tvDishEditToDatePicker, mDish.getmToDate());
            initTextView(tvDishEditToTimePicker, mDish.getmToTime());
            cbDishEditPickUp.setChecked(mDish.ismPickUp());
            cbDishEditDelivery.setChecked(mDish.ismDelivery());
        }

        tvDishEditFromDatePicker.addTextChangedListener(textWatcher);
        tvDishEditFromTimePicker.addTextChangedListener(textWatcher);
        tvDishEditToDatePicker.addTextChangedListener(textWatcher);
        tvDishEditToTimePicker.addTextChangedListener(textWatcher);
        cbDishEditPickUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mAlertDiscardChanges = true;
            }
        });
        cbDishEditDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mAlertDiscardChanges = true;
            }
        });

        if (mMode == HomePage.DISH_SECTION_EDIT_ALL) {
            llDishAvailNavigationButtons.setVisibility(View.VISIBLE);
            llDishAvailSaveButtons.setVisibility(View.GONE);
        } else if (mMode == ChefDishDesc.DISH_SECTION_EDIT_SINGLE) {
            llDishAvailSaveButtons.setVisibility(View.VISIBLE);
            llDishAvailNavigationButtons.setVisibility(View.GONE);
        }
    }

    private void readFields() {
        if (mDish != null) {
            mDish.setmFromDate(tvDishEditFromDatePicker.getText().toString());
            mDish.setmFromTime(tvDishEditFromTimePicker.getText().toString());
            mDish.setmToDate(tvDishEditToDatePicker.getText().toString());
            mDish.setmToTime(tvDishEditToTimePicker.getText().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy h : mm a");
            TimeZone tz = TimeZone.getDefault();
            dateFormat.setTimeZone(TimeZone.getDefault());
            String fromDateStr = tvDishEditFromDatePicker.getText().toString()
                    + " " + tvDishEditFromTimePicker.getText().toString();
            try {
                Date fromDate = dateFormat.parse(fromDateStr);
                mDish.setmFromDate_date(fromDate);
            } catch (ParseException e) {
                e.printStackTrace();
                Utils.notReached(e.getMessage());
            }

            String toDateStr = tvDishEditToDatePicker.getText().toString() + " "
                    + tvDishEditToTimePicker.getText().toString();
            try {
                Date toDate = dateFormat.parse(fromDateStr);
                mDish.setmToDate_date(toDate);
            } catch (ParseException e) {
                e.printStackTrace();
                Utils.notReached(e.getMessage());
            }

            mDish.setmPickUp(cbDishEditPickUp.isChecked());
            mDish.setmDelivery(cbDishEditDelivery.isChecked());
        }
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            cal.set(Calendar.MONTH, monthOfYear);
            String formattedDate = new SimpleDateFormat("E, MMM d, yyyy").format(cal.getTime());

            if (datePickerInput == rlDishEditFromDatePicker.getId())
                ((TextView) rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDatePicker)).setText(formattedDate);
            else if (datePickerInput == rlDishEditToDatePicker.getId())
                ((TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditDatePicker)).setText(formattedDate);

        }
    };

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        //onTimeSet() callback method
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //Get the AM or PM for current time
            String aMpM = "AM";
            if (hourOfDay > 11) {
                aMpM = "PM";
            }

            //Make the 24 hour time format to 12 hour time format
            int currentHour;
            if (hourOfDay > 11) {
                currentHour = hourOfDay - 12;
            } else {
                currentHour = hourOfDay;
            }

            //Display the user changed time on TextView
            if (timePickerInput == rlDishEditFromDatePicker.getId())
                ((TextView) rlDishEditFromDatePicker.findViewById(R.id.tvDishEditTimePicker)).setText(String.valueOf(currentHour)
                        + " : " + String.valueOf(minute) + " " + aMpM);
            else if (timePickerInput == rlDishEditToDatePicker.getId())
                ((TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditTimePicker)).setText(String.valueOf(currentHour)
                        + " : " + String.valueOf(minute) + " " + aMpM);
        }
    };

    private boolean checkForMustData() {
        String fromDate = tvDishEditFromDatePicker.getText().toString();
        String fromTime = tvDishEditFromTimePicker.getText().toString();
        String toDate = tvDishEditToDatePicker.getText().toString();
        String toTime = tvDishEditToTimePicker.getText().toString();
        boolean pickUpOrDelivery = cbDishEditPickUp.isChecked() || cbDishEditDelivery.isChecked();

        if (fromDate == null || fromDate.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Available from Date", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (fromTime == null || fromTime.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Available from Time", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (toDate == null || toDate.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Available till Date", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (toTime == null || toTime.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Available till Time", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pickUpOrDelivery) {
            Toast.makeText(mContext, "Please select either pickup or delivery", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvDishEditFromDateHdr = (TextView) rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDateHdr);
        tvDishEditFromDateHdr.setText("AVILABLE FROM");
        tvDishEditFromDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerInput = rlDishEditFromDatePicker.getId();
                DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
                newFragment.setCallback(ondate);
                newFragment.show(mContext.getSupportFragmentManager(), "DatePicker");
            }
        });

        tvDishEditFromTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerInput = rlDishEditFromDatePicker.getId();
                TimePickerDialogFragment newFragment = new TimePickerDialogFragment();
                newFragment.setCallback(onTime);
                newFragment.show(mContext.getSupportFragmentManager(), "TimePicker");
            }
        });

        TextView tvDishEditToDateHdr = (TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditDateHdr);
        tvDishEditToDateHdr.setText("TO");
        tvDishEditToDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerInput = rlDishEditToDatePicker.getId();
                DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
                newFragment.setCallback(ondate);
                newFragment.show(mContext.getSupportFragmentManager(), "DatePicker");
            }
        });

        tvDishEditToTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerInput = rlDishEditToDatePicker.getId();
                TimePickerDialogFragment newFragment = new TimePickerDialogFragment();
                newFragment.setCallback(onTime);
                newFragment.show(mContext.getSupportFragmentManager(), "TimePicker");
            }
        });

        bDishAvailNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
                boolean gotAllData = checkForMustData();
                if (gotAllData) {
                    Bundle args = new Bundle();
                    args.putParcelable("dish", mDish);
                    mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_ChefDishEditAvailFragment_ID,
                            Constants.ACTION_NEXT_ChefDishEditAvailFragment_ID, args);
                }
            }
        });

        bDishAvailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
                Bundle args = new Bundle();
                args.putParcelable("dish", mDish);
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_ChefDishEditAvailFragment_ID,
                        Constants.ACTION_BACK_ChefDishEditAvailFragment_ID, args);
            }
        });

        bDishAvailSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
                boolean gotAllData = checkForMustData();
                if (gotAllData) {
                    Bundle args = new Bundle();
                    args.putParcelable("dish", mDish);
                    args.putInt("mode", ChefDishDesc.DISH_SECTION_EDIT_SINGLE);
                    mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_ChefDishEditAvailFragment_ID,
                            Constants.ACTION_SAVE_ChefDishEditAvailFragment_ID, args);
                }
            }
        });

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_chef_fragment_dish_edit, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel_edit:
                Bundle args = new Bundle();
                args.putBoolean("onChanged", mAlertDiscardChanges);
                args.putInt("mode", mMode);
                mActionRequestCallback.fragmentActionRequestHandler(Constants.FRAGMENT_ChefDishEditAvailFragment_ID,
                        Constants.ACTION_CANCEL_ChefDishEditAvailFragment_ID, args);
                return true;
            default:
                break;
        }
        return false;
    }

}

