package com.apps.b3bytes.homefoods.utils;

import android.widget.TextView;

/**
 * Created by sindhu on 9/11/2015.
 */
public class Utils {
    public static void _assert(boolean condition) {
        if (!condition) {
            throw new RuntimeException();
        }
    }

    public static void notReached() {
        _assert(false);
    }

    public static void initTextView(TextView tvView, String text) {
        _assert(tvView != null);
        if (text != null && !text.isEmpty()) {
            tvView.setText(text);
        }
    }
}
