package com.apps.b3bytes.homefoods.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;

    public DatePickerDialogFragment() {
        // nothing to see here, move along
    }

    public void setCallback(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        return new DatePickerDialog(getActivity(), ondateSet,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

}