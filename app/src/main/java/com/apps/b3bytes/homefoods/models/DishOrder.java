package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sindhu on 8/15/2015.
 */
public class DishOrder implements Parcelable{
    private DishOnSale mDishOnSale;
    private int mQty;
    private Foodie mFoodie;
    private String mTag;

    public DishOrder() {
        // nothing to do.
    }
    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public DishOnSale getmDishOnSale() {
        return mDishOnSale;
    }

    public void setmDishOnSale(DishOnSale mDishOnSale) {
        this.mDishOnSale = mDishOnSale;
    }

    public int getmQty() {
        return mQty;
    }

    public void setmQty(int mQty) {
        this.mQty = mQty;
    }

    public Foodie getmFoodie() {
        return mFoodie;
    }

    public void setmFoodie(Foodie mFoodie) {
        this.mFoodie = mFoodie;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getmQty());
        dest.writeString(getmTag());
        dest.writeParcelable(getmDishOnSale(), flags);
        dest.writeParcelable(getmFoodie(), flags);
    }


    public static final Parcelable.Creator<DishOrder> CREATOR = new Parcelable.Creator<DishOrder>() {
        public DishOrder createFromParcel(Parcel in) {
            DishOrder dishOrder = new DishOrder(in);
            return dishOrder;
        }

        public DishOrder[] newArray(int size) {
            return new DishOrder[size];
        }
    };

    public DishOrder(Parcel in) {
        setmQty(in.readInt());
        setmTag(in.readString());
        setmDishOnSale((DishOnSale) in.readParcelable(DishOnSale.class.getClassLoader()));
        setmFoodie((Foodie) in.readParcelable(Foodie.class.getClassLoader()));
    }

}
