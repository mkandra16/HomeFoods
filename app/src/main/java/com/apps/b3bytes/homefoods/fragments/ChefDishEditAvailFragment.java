package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.activities.HomePage;
import com.apps.b3bytes.homefoods.models.DishOnSale;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChefDishEditAvailFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private DishOnSale mDish;
    private int mMode;

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

    OnDishAvailNextSelectedListener mNextCallback;
    OnDishAvailBackSelectedListener mBackCallback;
    OnDishImageSaveSelectedListener mSaveCallback;


    // Container Activity must implement this interface
    public interface OnDishAvailNextSelectedListener {
        public void onDishAvailNextSelected(DishOnSale mDish);
    }

    // Container Activity must implement this interface
    public interface OnDishAvailBackSelectedListener {
        public void onDishAvailBackSelected(DishOnSale mDish);
    }

    public interface OnDishImageSaveSelectedListener {
        public void onDishImageSaveSelected(DishOnSale mDish, int mode);
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;

        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mNextCallback = (OnDishAvailNextSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDishPriceNextSelectedListener");
        }

        try {
            mBackCallback = (OnDishAvailBackSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDishAvailBackSelectedListener");
        }

        try {
            mSaveCallback = (OnDishImageSaveSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDishImageSaveSelectedListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mDish = (DishOnSale) bundle.getParcelable("dish");
            mMode = bundle.getInt("mode", HomePage.DISH_SECTION_EDIT_ALL);
        }
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
        return rootView;
    }

    private void initTextView(TextView tvView, String text) {
        if (text != null && !text.isEmpty()) {
            tvView.setText(text);
        }
    }

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

        if (mMode == HomePage.DISH_SECTION_EDIT_ALL) {
            llDishAvailNavigationButtons.setVisibility(View.VISIBLE);
            llDishAvailSaveButtons.setVisibility(View.GONE);
        } else if (mMode == HomePage.DISH_SECTION_EDIT_SINGLE) {
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
                mNextCallback.onDishAvailNextSelected(mDish);
            }
        });

        bDishAvailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
                mBackCallback.onDishAvailBackSelected(mDish);
            }
        });

        bDishAvailSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
                mSaveCallback.onDishImageSaveSelected(mDish, HomePage.DISH_SECTION_EDIT_SINGLE);
            }
        });

    }
}

