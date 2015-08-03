package com.apps.b3bytes.homefoods.models;

/**
 * Created by sindhu on 7/29/2015.
 */

public class Listener<T> {
    public void done(T d, Exception e) {
        if (e != null) {
            e.printStackTrace();
            throw new RuntimeException("Crash!");
        }
    }
}


