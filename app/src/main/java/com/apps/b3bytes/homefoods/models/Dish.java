package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.apps.b3bytes.homefoods.utils.Address;
import com.apps.b3bytes.homefoods.utils.ContactDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pavan on 7/26/2015.
 *
 * Model representing Dish for rest of the UI code.
 */
public class Dish implements Parcelable {
    public enum Unit {GRAMS, LITERS};
    private int mDishId;
    private String mDishName;
    private String mImageURL;
    private int mChefId;
    private String mDishInfo;
    // private ArrayList<Ingredient> mIngredients;
    private String PrepMethod;
    // private NutrionInfo mNutrition;
    private double mQty;
    private Unit mUnit;
    private double mPrice;
    private int mCusineId;
    private int mThumbsUp;
    private int mThumbsDown;
    private Foodie mChef;
    private String mTag;

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }


    public String getmTag() {
        return mTag;
    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Dish)) {
            return false;
        }

        Dish that = (Dish) other;

        // Custom equality check here.
        return this.mDishName.equals(that.mDishName)
                && this.mChef.getmUserName().equals(that.mChef.getmUserName());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;

        hashCode = hashCode * 37 + this.mDishName.hashCode();
        hashCode = hashCode * 37 + this.mChef.getmUserName().hashCode();

        return hashCode;
    }

    public int getmDishId() {
        return mDishId;
    }

    public static Dish createDummyDish(String name, String info, String method, int price) {
        Dish d = new Dish();
        d.setmDishId(1);
        d.setmDishName(name);
        d.setmChefId(1);
        d.setmDishInfo(info);
        d.setPrepMethod(method);
        d.setmQty(100);
        d.setmUnit(Unit.GRAMS);
        d.setmPrice(price);
        d.setmCusineId(1);
        d.setmThumbsUp(150);
        d.setmThumbsDown(10);
        return d;
    }
    public Dish() {}

 /*   public Dish(JSONObject object) {
        try {
            mDishId = object.getInt("DishId");
        mDishName = object.getString("DishName");
        mDishInfo =     object.getString("DishInfo");
//        mImageURL = object.getString("ImageURL");
        mQty = object.getDouble("Qty");
        mUnit = Unit.valueOf(object.getString("Unit"));
        mPrice = object.getDouble("Price");
        mThumbsUp = object.getInt("ThumbsUp");
        mThumbsDown = object.getInt("ThumbsDown");
        mCusineId = object.getInt("CusineId");
        mChefId = object.getInt("ChefId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
    public void setmDishId(int mDishId) {
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

    public int getmChefId() {
        return mChefId;
    }

    public void setmChefId(int mChefId) {
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
    public void setmUnit(String mUnit) {
        this.mUnit = Unit.valueOf(mUnit);
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public int getmCusineId() {
        return mCusineId;
    }

    public void setmCusineId(int mCusineId) {
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

    public void setmChef(Foodie f) {
        mChef = f;
    }

    public Foodie getmChef() { return mChef; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getmDishId());
        dest.writeString(getmDishName());
        dest.writeString(getmImageURL());
        dest.writeInt(getmChefId());
        dest.writeString(getmDishInfo());
        dest.writeString(getPrepMethod());
        dest.writeDouble(getmQty());
        dest.writeString("" + getmUnit());
        dest.writeDouble(getmPrice());
        dest.writeInt(getmCusineId());
        dest.writeInt(getmThumbsUp());
        dest.writeInt(getmThumbsDown());
        dest.writeString(getmTag());
    }

}
