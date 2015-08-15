package com.apps.b3bytes.homefoods.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment {
    TimePickerDialog.OnTimeSetListener onTimeSet;

    public TimePickerDialogFragment() {
        // nothing to see here, move along
    }

    public void setCallback(TimePickerDialog.OnTimeSetListener onTimeSet) {
        this.onTimeSet = onTimeSet;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(), onTimeSet, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

}