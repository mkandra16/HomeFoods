package com.apps.b3bytes.homefoods.activities;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.fragments.DatePickerDialogFragment;
import com.apps.b3bytes.homefoods.fragments.TimePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ChefDishEdit extends ActionBarActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private RelativeLayout lDishEditFromDatePicker;
    private RelativeLayout lDishEditToDatePicker;
    private int datePickerInput;
    private int timePickerInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_dish_edit);

        Spinner food = (Spinner) findViewById(R.id.spDishEditCuisine);
        ArrayAdapter<CharSequence> foodadapter = ArrayAdapter.createFromResource(
                this, R.array.cuisine_picker_array, R.layout.spinner_layout);
        foodadapter.setDropDownViewResource(R.layout.spinner_layout);
        food.setAdapter(foodadapter);

        lDishEditFromDatePicker = (RelativeLayout) findViewById(R.id.lDishEditFromDatePicker);
        TextView tvDishEditFromDateHdr = (TextView) lDishEditFromDatePicker.findViewById(R.id.tvDishEditDateHdr);
        tvDishEditFromDateHdr.setText("AVILABLE FROM");

        TextView tvDishEditFromDatePicker = (TextView) lDishEditFromDatePicker.findViewById(R.id.tvDishEditDatePicker);
        tvDishEditFromDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerInput = lDishEditFromDatePicker.getId();
                DialogFragment newFragment = new DatePickerDialogFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        TextView tvDishEditFromTimePicker = (TextView) lDishEditFromDatePicker.findViewById(R.id.tvDishEditTimePicker);
        tvDishEditFromTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerInput = lDishEditFromDatePicker.getId();
                DialogFragment newFragment = new TimePickerDialogFragment();
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });


        lDishEditToDatePicker = (RelativeLayout) findViewById(R.id.lDishEditToDatePicker);
        TextView tvDishEditToDateHdr = (TextView) lDishEditToDatePicker.findViewById(R.id.tvDishEditDateHdr);
        tvDishEditToDateHdr.setText("TO");
        TextView tvDishEditToDatePicker = (TextView) lDishEditToDatePicker.findViewById(R.id.tvDishEditDatePicker);
        tvDishEditToDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerInput = lDishEditToDatePicker.getId();
                DialogFragment newFragment = new DatePickerDialogFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        TextView tvDishEditToTimePicker = (TextView) lDishEditToDatePicker.findViewById(R.id.tvDishEditTimePicker);
        tvDishEditToTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerInput = lDishEditToDatePicker.getId();
                DialogFragment newFragment = new TimePickerDialogFragment();
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.MONTH, monthOfYear);
        String formattedDate = new SimpleDateFormat("E, MMM d, yyyy").format(cal.getTime());

        if (datePickerInput == lDishEditFromDatePicker.getId())
            ((TextView) lDishEditFromDatePicker.findViewById(R.id.tvDishEditDatePicker)).setText(formattedDate);
        else if (datePickerInput == lDishEditToDatePicker.getId())
            ((TextView) lDishEditToDatePicker.findViewById(R.id.tvDishEditDatePicker)).setText(formattedDate);

    }

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
        if (datePickerInput == lDishEditFromDatePicker.getId())
            ((TextView) lDishEditFromDatePicker.findViewById(R.id.tvDishEditTimePicker)).setText(String.valueOf(currentHour)
                    + " : " + String.valueOf(minute) + " " + aMpM);
        else if (datePickerInput == lDishEditToDatePicker.getId())
            ((TextView) lDishEditToDatePicker.findViewById(R.id.tvDishEditTimePicker)).setText(String.valueOf(currentHour)
                    + " : " + String.valueOf(minute) + " " + aMpM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chef_dish_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
