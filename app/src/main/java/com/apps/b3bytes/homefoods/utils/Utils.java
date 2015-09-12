package com.apps.b3bytes.homefoods.utils;

/**
 * Created by sindhu on 9/11/2015.
 */
public class Utils {
    public static void _assert(boolean condition) {
        if (! condition) {
            throw new RuntimeException();
        }
    }
}
