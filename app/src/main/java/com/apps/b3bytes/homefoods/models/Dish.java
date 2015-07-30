package com.apps.b3bytes.homefoods.models;

import com.apps.b3bytes.homefoods.utils.Address;
import com.apps.b3bytes.homefoods.utils.ContactDetails;

import java.util.ArrayList;

/**
 * Created by Pavan on 7/26/2015.
 *
 * Model representing Dish for rest of the UI code.
 */
public class Dish {
    public enum Unit {GRAMS, LITERS};
    private String mDishId;
    private String mDishName;
    private String mImageURL;
    private String mChefId;
    private String mDishInfo;
    // private ArrayList<Ingredient> mIngredients;
    private String PrepMethod;
    // private NutrionInfo mNutrition;
    private int mQty;
    private Unit mUnit;
    private double mPrice;
    private String mCusineId;
    private int mThumbsUp;
    private int mThumbsDown;

    public String getmDishId() {
        return mDishId;
    }

    public void setmDishId(String mDishId) {
        this.mDishId = mDishId;
    }

    public String getmDishName() {
        return mDishName;
    }

    public void setmDishName(String mDishName) {
        this.mDishName = mDishName;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public String getmChefId() {
        return mChefId;
    }

    public void setmChefId(String mChefId) {
        this.mChefId = mChefId;
    }

    public String getmDishInfo() {
        return mDishInfo;
    }

    public void setmDishInfo(String mDishInfo) {
        this.mDishInfo = mDishInfo;
    }

    public String getPrepMethod() {
        return PrepMethod;
    }

    public void setPrepMethod(String prepMethod) {
        PrepMethod = prepMethod;
    }

    public int getmQty() {
        return mQty;
    }

    public void setmQty(int mQty) {
        this.mQty = mQty;
    }

    public Unit getmUnit() {
        return mUnit;
    }

    public void setmUnit(Unit mUnit) {
        this.mUnit = mUnit;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public String getmCusineId() {
        return mCusineId;
    }

    public void setmCusineId(String mCusineId) {
        this.mCusineId = mCusineId;
    }

    public int getmThumbsUp() {
        return mThumbsUp;
    }

    public void setmThumbsUp(int mThumbsUp) {
        this.mThumbsUp = mThumbsUp;
    }

    public int getmThumbsDown() {
        return mThumbsDown;
    }

    public void setmThumbsDown(int mThumbsDown) {
        this.mThumbsDown = mThumbsDown;
    }
}
