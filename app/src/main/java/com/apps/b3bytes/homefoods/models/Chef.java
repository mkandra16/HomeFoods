package com.apps.b3bytes.homefoods.models;

import com.apps.b3bytes.homefoods.utils.Address;

import java.util.ArrayList;

/**
 * Created by sindhu on 7/26/2015.
 */
public class Chef {
    private String mChefId;
    private ArrayList<String> mPayrollOptions;
    private Address mAddr;

    public String getmChefId() {
        return mChefId;
    }

    public void setmChefId(String mChefId) {
        this.mChefId = mChefId;
    }

    public ArrayList<String> getmPayrollOptions() {
        return mPayrollOptions;
    }

    public void setmPayrollOptions(ArrayList<String> mPayrollOptions) {
        this.mPayrollOptions = mPayrollOptions;
    }

    public Address getmAddr() {
        return mAddr;
    }

    public void setmAddr(Address mAddr) {
        this.mAddr = mAddr;
    }
}
