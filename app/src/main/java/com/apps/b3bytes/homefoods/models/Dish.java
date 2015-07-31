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
    private double mQty;
    private Unit mUnit;
    private double mPrice;
    private String mCusineId;
    private int mThumbsUp;
    private int mThumbsDown;

    public String getmDishId() {
        return mDishId;
    }

    public static Dish createDummyDish() {
        Dish d = new Dish();
        d.setmDishId("1");
        d.setmDishName("Gongura");
        d.setmChefId("1");
        d.setmDishInfo("Andhra mata Gongura pachhadi");
        d.setPrepMethod("Boil Gongura. fry onions and chilli. Grind boiled Gongura with chillies and onion. Finally add tadka!!!");
        d.setmQty(100);
        d.setmUnit(Unit.GRAMS);
        d.setmPrice(20);
        d.setmCusineId("Andhra");
        d.setmThumbsUp(150);
        d.setmThumbsDown(10);
        return d;
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

    public double getmQty() {
        return mQty;
    }

    public void setmQty(double mQty) {
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
