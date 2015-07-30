package com.apps.b3bytes.homefoods.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pavan on 7/26/2015.
 */
public class ContactDetails {
    private String mHomePh;
    private String mOfficePh;
    private String mMoblie;
    private String mEmailId;

    public ContactDetails(JSONObject cd) {
        try {
            mHomePh = cd.getString("HomePh");
        } catch (JSONException e) {
            // Nothing todo, these are not mandatory.
            mHomePh = new String();
        }
        try {
            mOfficePh = cd.getString("OfficePh");
        } catch (JSONException e) {
            mOfficePh = new String();
        }

        try {
            mMoblie = cd.getString("Mobile");
            mEmailId = cd.getString("EmailId");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Crash!");
        }
    }

    public String getmEmailId() {

        return mEmailId;
    }

    public void setmEmailId(String mEmailId) {
        this.mEmailId = mEmailId;
    }

    public String getmHomePh() {
        return mHomePh;
    }

    public void setmHomePh(String mHomePh) {
        this.mHomePh = mHomePh;
    }

    public String getmOfficePh() {
        return mOfficePh;
    }

    public void setmOfficePh(String mOfficePh) {
        this.mOfficePh = mOfficePh;
    }

    public String getmMoblie() {
        return mMoblie;
    }

    public void setmMoblie(String mMoblie) {
        this.mMoblie = mMoblie;
    }

}
