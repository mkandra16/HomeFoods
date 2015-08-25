package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DishOnSale implements Parcelable {
    public enum Measure {Grams, Liters};
    private Dish mDish;
    private Measure mMeasure;
    // amount per single order 100 gms or 1 ltr etc...
    private double mQtyPerUnit; // TODO: why is this double???????
    private double mUnitPrice;
    private int mUnitsOnSale;
    private int mUnitsOrdered;
    private int mUnitsDelivered;
    private String mTag;
    private boolean mPickUp;
    private boolean mDelivery;
    private String mToDate;
    private String mToTime;

    public Measure getmMeasure() {
        return mMeasure;
    }

    public String getmToDate() {
        return mToDate;
    }

    public void setmToDate(String mToDate) {
        this.mToDate = mToDate;
    }

    public String getmToTime() {
        return mToTime;
    }

    public void setmToTime(String mToTime) {
        this.mToTime = mToTime;
    }


    public void setmMeasure(Measure mMeasure) {
        this.mMeasure = mMeasure;
    }

    public void setmMeasure(String mUnit) {
        if (mUnit != null && !mUnit.isEmpty()) {
             this.mMeasure = Measure.valueOf(mUnit);
        }
    }

    public double getmQtyPerUnit() {
        return mQtyPerUnit;
    }

    public void setmQtyPerUnit(double mQtyPerUnit) {
        this.mQtyPerUnit = mQtyPerUnit;
    }

    public boolean ismPickUp() {
        return mPickUp;
    }

    public void setmPickUp(boolean mPickUp) {
        this.mPickUp = mPickUp;
    }

    public boolean ismDelivery() {
        return mDelivery;
    }

    public void setmDelivery(boolean mDelivery) {
        this.mDelivery = mDelivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DishOnSale that = (DishOnSale) o;

        if (Double.compare(that.mUnitPrice, mUnitPrice) != 0) return false;
        if (mUnitsOnSale != that.mUnitsOnSale) return false;
        if (mUnitsOrdered != that.mUnitsOrdered) return false;
        if (mUnitsDelivered != that.mUnitsDelivered) return false;
        if (!mDish.equals(that.mDish)) return false;
        if (mToDate != null ? !mToDate.equals(that.mToDate) : that.mToDate != null) return false;
        return !(mTag != null ? !mTag.equals(that.mTag) : that.mTag != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mDish.hashCode();
        temp = Double.doubleToLongBits(mUnitPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + mUnitsOnSale;
        result = 31 * result + (mToDate != null ? mToDate.hashCode() : 0);
        result = 31 * result + mUnitsOrdered;
        result = 31 * result + mUnitsDelivered;
        result = 31 * result + (mTag != null ? mTag.hashCode() : 0);
        return result;
    }

    public DishOnSale() {
        mDish = new Dish();
        this.mMeasure = Measure.Grams;
        this.mQtyPerUnit = 0;
        this.mUnitPrice = 0.0;
        this.mUnitsOnSale = 0;
        this.mUnitsOrdered = 0;
        this.mUnitsDelivered = 0;
        this.mPickUp = false;
        this.mDelivery = false;

    }

    public DishOnSale(String dishName, int quantity, double unitPrice) {
        mDish = Dish.createDummyDish(dishName, "DishOnSale", "DishOnSale Method", 0);
        mDish.setmPrice(unitPrice);
        this.mMeasure = Measure.Grams;
        this.mQtyPerUnit = quantity;
        this.mUnitPrice = 0.0;
        this.mUnitsOnSale = quantity;
        this.mUnitsOrdered = 0;
        this.mUnitsDelivered = quantity;
        this.mPickUp = false;
        this.mDelivery = false;
    }


    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public Dish getmDish() {
        return mDish;
    }

    public void setmDish(Dish mDish) {
        this.mDish = mDish;
    }

    public double getmUnitPrice() {
        return mUnitPrice;
    }

    public void setmUnitPrice(double mUnitPrice) {
        this.mUnitPrice = mUnitPrice;
    }

    public int getmUnitsOnSale() {
        return mUnitsOnSale;
    }

    public void setmUnitsOnSale(int mUnitsOnSale) {
        this.mUnitsOnSale = mUnitsOnSale;
    }

    public int getmUnitsOrdered() {
        return mUnitsOrdered;
    }

    public void setmUnitsOrdered(int mUnitsOrdered) {
        this.mUnitsOrdered = mUnitsOrdered;
    }

    public int getmUnitsDelivered() {
        return mUnitsDelivered;
    }

    public void setmUnitsDelivered(int mUnitsDelivered) {
        this.mUnitsDelivered = mUnitsDelivered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mDish, flags);
        dest.writeInt(getmMeasure().ordinal());
        dest.writeDouble(getmQtyPerUnit());
        dest.writeDouble(getmUnitPrice());
        dest.writeInt(getmUnitsOnSale());
        dest.writeInt(getmUnitsOrdered());
        dest.writeInt(getmUnitsDelivered());
        dest.writeString(getmTag());
        dest.writeInt(ismPickUp() ? 1 : 0);
        dest.writeInt(ismDelivery() ? 1 : 0);
        dest.writeString(getmToDate());
        dest.writeString(getmToTime());
    }

    public static final Parcelable.Creator<DishOnSale> CREATOR = new Parcelable.Creator<DishOnSale>() {
        public DishOnSale createFromParcel(Parcel in) {
            DishOnSale dish = new DishOnSale(in);
            return dish;
        }

        public DishOnSale[] newArray(int size) {
            return new DishOnSale[size];
        }
    };

    public DishOnSale(Parcel in) {
        setmDish((Dish) in.readParcelable(Dish.class.getClassLoader()));
        setmMeasure(Measure.values()[in.readInt()]);
        setmQtyPerUnit(in.readDouble());
        setmUnitPrice(in.readDouble());
        setmUnitsOnSale(in.readInt());
        setmUnitsOrdered(in.readInt());
        setmUnitsDelivered(in.readInt());
        setmTag(in.readString());
        setmPickUp((in.readInt() == 1) ? true : false);
        setmDelivery((in.readInt() == 1) ? true : false);
        setmToDate(in.readString());
        setmToTime(in.readString());
    }

}
