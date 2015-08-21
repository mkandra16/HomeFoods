package com.apps.b3bytes.homefoods.models;

/**
 * Created by sindhu on 8/16/2015.
 */
public class DishOnSale {
    public enum Measure {Grams, Liters};
    private Dish mDish;
    private Measure mMeasure;
    // amount per single order 100 gms or 1 ltr etc...
    private double mQtyPerUnit;
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

    public double getmQtyPerUnit() {
        return mQtyPerUnit;
    }

    public void setmQtyPerUnit(double mQtyPerUnit) {
        this.mQtyPerUnit = mQtyPerUnit;
    }

    public void setmUnit(String mUnit) {
        this.mMeasure = Measure.valueOf(mUnit);
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
}
