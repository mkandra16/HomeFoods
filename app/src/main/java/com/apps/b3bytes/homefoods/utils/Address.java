package com.apps.b3bytes.homefoods.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pavan on 7/26/2015.
 */
public class Address {
    private String mLine1;
    private String mLine2;
    private int mZip;
    private String mState;
    private String mCountry;

    public Address(JSONObject addr) {
        try {
            mLine2 = addr.getString("AddrLine2");
        } catch (JSONException e) {
            // nothing to do...
            mLine2 = new String();
        }

        try {
            mLine1 = addr.getString("AddrLine1");
            mZip = addr.getInt("AddrZip");
            mState = addr.getString("AddrState");
            mCountry = addr.getString("AddrCountry");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Crash!");
        }
    }

    public String getmCountry() {

        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmLine1() {
        return mLine1;
    }

    public void setmLine1(String mLine1) {
        this.mLine1 = mLine1;
    }

    public String getmLine2() {
        return mLine2;
    }

    public void setmLine2(String mLine2) {
        this.mLine2 = mLine2;
    }

    public int getmZip() {
        return mZip;
    }

    public void setmZip(int mZip) {
        this.mZip = mZip;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }


}
