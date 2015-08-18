package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.apps.b3bytes.homefoods.utils.Address;

import java.util.ArrayList;

/**
 * Created by sindhu on 7/26/2015.
 */
public class Chef implements Parcelable {
    private int mChefId;
    //private ArrayList<String> mPayrollOptions;
    private Address mAddr;

/*

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Chef)) {
            return false;
        }

        Chef that = (Chef) other;

        // Custom equality check here.
        return getmChefId().equals(that.getmChefId());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;

        hashCode = hashCode * 37 + this.mChefId.hashCode();

        return hashCode;
    }
*/

    public int getmChefId() {
        return mChefId;
    }

    public void setmChefId(int mChefId) {
        this.mChefId = mChefId;
    }

/*    public ArrayList<String> getmPayrollOptions() {
        return mPayrollOptions;
    }

    public void setmPayrollOptions(ArrayList<String> mPayrollOptions) {
        this.mPayrollOptions = mPayrollOptions;
    }*/

    public Address getmAddr() {
        return mAddr;
    }

    public void setmAddr(Address mAddr) {
        this.mAddr = mAddr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getmChefId());
        //dest.writeStringList(getmPayrollOptions()); // TODO: Check this? will it work for ArrayList?
        dest.writeParcelable(getmAddr(), flags);
    }

}
