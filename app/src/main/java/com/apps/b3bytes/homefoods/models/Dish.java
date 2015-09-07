package com.apps.b3bytes.homefoods.models;


import android.net.Uri;
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
    private int mDishId;
    private String mDishName;
    private Uri mImageUri;
    private String mImageURL;
    private String mDishInfo;
    private boolean mVegitarian;
    private boolean mGlutenFree;
    private boolean mRedMeat;
    private boolean mVegan;
    // private ArrayList<Ingredient> mIngredients;
    private String mPrepMethod;
    // private NutrionInfo mNutrition;
    private double mQty;
    private double mPrice;
    private int mCusineId;
    private String mCusine;
    private int mThumbsUp;
    private int mThumbsDown;

    private int mChefId;
    private Foodie mChef;
    private String mTag;

    public Uri getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(Uri mImageUri) {
        this.mImageUri = mImageUri;
    }

    public boolean ismVegitarian() {
        return mVegitarian;
    }

    public void setmVegitarian(boolean mVegitarian) {
        this.mVegitarian = mVegitarian;
    }

    public boolean ismGlutenFree() {
        return mGlutenFree;
    }

    public void setmGlutenFree(boolean mGlutenFree) {
        this.mGlutenFree = mGlutenFree;
    }

    public boolean ismRedMeat() {
        return mRedMeat;
    }

    public void setmRedMeat(boolean mRedMeat) {
        this.mRedMeat = mRedMeat;
    }

    public boolean ismVegan() {
        return mVegan;
    }

    public void setmVegan(boolean mVegan) {
        this.mVegan = mVegan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (mDishId != dish.mDishId) return false;
        if (mChefId != dish.mChefId) return false;
        if (Double.compare(dish.mQty, mQty) != 0) return false;
        if (Double.compare(dish.mPrice, mPrice) != 0) return false;
        if (mCusineId != dish.mCusineId) return false;
        if (mThumbsUp != dish.mThumbsUp) return false;
        if (mThumbsDown != dish.mThumbsDown) return false;
        if (mDishName != null ? !mDishName.equals(dish.mDishName) : dish.mDishName != null)
            return false;
        if (mImageURL != null ? !mImageURL.equals(dish.mImageURL) : dish.mImageURL != null)
            return false;
        if (mDishInfo != null ? !mDishInfo.equals(dish.mDishInfo) : dish.mDishInfo != null)
            return false;
        if (mPrepMethod != null ? !mPrepMethod.equals(dish.mPrepMethod) : dish.mPrepMethod != null)
            return false;
        if (mChef != null ? !mChef.equals(dish.mChef) : dish.mChef != null) return false;
        return !(mTag != null ? !mTag.equals(dish.mTag) : dish.mTag != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mDishId;
        result = 31 * result + (mDishName != null ? mDishName.hashCode() : 0);
        result = 31 * result + (mImageURL != null ? mImageURL.hashCode() : 0);
        result = 31 * result + mChefId;
        result = 31 * result + (mDishInfo != null ? mDishInfo.hashCode() : 0);
        result = 31 * result + (mPrepMethod != null ? mPrepMethod.hashCode() : 0);
        temp = Double.doubleToLongBits(mQty);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + mCusineId;
        result = 31 * result + mThumbsUp;
        result = 31 * result + mThumbsDown;
        result = 31 * result + (mChef != null ? mChef.hashCode() : 0);
        result = 31 * result + (mTag != null ? mTag.hashCode() : 0);
        return result;
    }


    public void setmTag(String mTag) {
        this.mTag = mTag;
    }


    public String getmTag() {
        return mTag;
    }

    public String getmCusine() {
        return mCusine;
    }

    public void setmCusine(String mCusine) {
        this.mCusine = mCusine;
    }



/*

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
*/

    public int getmDishId() {
        return mDishId;
    }

    public static Dish createDummyDish(String name, String info, String method, int price) {
        Dish d = new Dish();
        d.setmDishId(1);
        d.setmDishName(name);
        d.setmChefId(1);
        d.setmDishInfo(info);
        d.setmPrepMethod(method);
        d.setmQty(100);
        d.setmPrice(price);
        d.setmCusineId(1);
        d.setmThumbsUp(150);
        d.setmThumbsDown(10);
        return d;
    }
    public Dish() {
        mThumbsDown = 0;
        mThumbsUp = 0;
    }

 /*   public Dish(JSONObject object) {
        try {
            mDishId = object.getInt("DishId");
        mDishName = object.getString("DishName");
        mDishInfo =     object.getString("DishInfo");
//        mImageURL = object.getString("ImageURL");
        mQty = object.getDouble("Qty");
        mUnit = Measure.valueOf(object.getString("Measure"));
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

    public String getmPrepMethod() {
        return mPrepMethod;
    }

    public void setmPrepMethod(String mPrepMethod) {
        this.mPrepMethod = mPrepMethod;
    }

    public double getmQty() {
        return mQty;
    }

    public void setmQty(double mQty) {
        this.mQty = mQty;
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
        dest.writeString(getmPrepMethod());
        dest.writeDouble(getmQty());
        dest.writeDouble(getmPrice());
        dest.writeInt(getmCusineId());
        dest.writeInt(getmThumbsUp());
        dest.writeInt(getmThumbsDown());
        dest.writeString(getmTag());
        dest.writeParcelable(mChef, flags);
    }


    public static final Parcelable.Creator<Dish> CREATOR = new Parcelable.Creator<Dish>() {
        public Dish createFromParcel(Parcel in) {
            Dish dish = new Dish(in);
            return dish;
        }

        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    public Dish(Parcel in) {
        setmDishId(in.readInt());
        setmDishName(in.readString());
        setmImageURL(in.readString());
        setmChefId(in.readInt());
        setmDishInfo(in.readString());
        setmPrepMethod(in.readString());
        setmQty(in.readDouble());
        setmPrice(in.readDouble());
        setmCusineId(in.readInt());
        setmThumbsUp(in.readInt());
        setmThumbsDown(in.readInt());
        setmTag(in.readString());
        setmChef((Foodie) in.readParcelable(Foodie.class.getClassLoader()));
    }

}
