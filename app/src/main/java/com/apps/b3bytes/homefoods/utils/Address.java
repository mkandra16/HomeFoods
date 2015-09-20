package com.apps.b3bytes.homefoods.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Pavan on 7/26/2015.
 */
public class Address implements Parcelable {
    private String mLine1;
    private String mCity;
    private int mZip;
    private String mState;
    private String mCountry;

    public Address() {
    }

    public Address(JSONObject addr) {
        try {
            mCity = addr.getString("AddrLine2");
        } catch (JSONException e) {
            // nothing to do...
            mCity = new String();
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

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getmLine1());
        dest.writeString(getmCity());
        dest.writeInt(getmZip());
        dest.writeString(getmState());
        dest.writeString(getmCountry());
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        public Address createFromParcel(Parcel in) {
            Address addr = new Address(in);
            return addr;
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public Address(Parcel in) {
        setmLine1(in.readString());
        setmCity(in.readString());
        setmZip(in.readInt());
        setmState(in.readString());
        setmCountry(in.readString());
    }



}
