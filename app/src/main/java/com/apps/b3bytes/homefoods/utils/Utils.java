package com.apps.b3bytes.homefoods.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by sindhu on 9/11/2015.
 */
public class Utils {
    public static void _assert(boolean condition) {
        _assert(condition, new String());
    }

    public static void _assert(boolean condition, String failText) {
        if (!condition) {
            throw new RuntimeException(failText);
        }
    }

    public static void notReached() {
        _assert(false);
    }

    public static void notReached(String reason) {
        _assert(false, reason);
    }

    public static void initTextView(TextView tvView, String text) {
        _assert(tvView != null);
        if (text != null && !text.isEmpty()) {
            tvView.setText(text);
        }
    }

    public static void initAutoCompleteTextView(AutoCompleteTextView acTvView, String text) {
        _assert(acTvView != null);
        if (text != null && !text.isEmpty()) {
            acTvView.setText(text);
        }
    }

    public static void initEditTextView(EditText etView, String text) {
        _assert(etView != null);
        if (text != null && !text.isEmpty()) {
            etView.setText(text);
        }
    }

    public static void replaceFragment(FragmentManager manager, int viewId, Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(viewId, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
}
