package com.apps.b3bytes.homefoods.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pavan on 7/26/2015.
 */
public class ContactDetails implements Parcelable {
    private String mHomePh;
    private String mOfficePh;
    private String mMoblie;
    private String mEmailId;
    private boolean mEmailVerified;

    public boolean ismEmailVerified() {
        return mEmailVerified;
    }

    public void setmEmailVerified(boolean mEmailVerified) {
        this.mEmailVerified = mEmailVerified;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getmHomePh());
        dest.writeString(getmOfficePh());
        dest.writeString(getmMoblie());
        dest.writeString(getmEmailId());
    }

    public static final Parcelable.Creator<ContactDetails> CREATOR = new Parcelable.Creator<ContactDetails>() {
        public ContactDetails createFromParcel(Parcel in) {
            ContactDetails cnt = new ContactDetails(in);
            return cnt;
        }

        public ContactDetails[] newArray(int size) {
            return new ContactDetails[size];
        }
    };

    public ContactDetails(Parcel in) {
        setmHomePh(in.readString());
        setmOfficePh(in.readString());
        setmMoblie(in.readString());
        setmEmailId(in.readString());
    }

}
