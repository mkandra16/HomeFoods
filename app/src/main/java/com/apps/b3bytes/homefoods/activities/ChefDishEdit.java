package com.apps.b3bytes.homefoods.activities;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
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

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.fragments.DatePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ChefDishEdit extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {
    private RelativeLayout lDishEditFromDatePicker;
    private RelativeLayout lDishEditToDatePicker;
    private int datePickerInput;
    public static final int DATE_DIALOG_FRAGMENT = 1;

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
                newFragment.show(getSupportFragmentManager(), "From");
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
                newFragment.show(getSupportFragmentManager(), "To");
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
