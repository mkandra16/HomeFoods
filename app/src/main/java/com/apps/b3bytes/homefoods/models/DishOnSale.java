package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.apps.b3bytes.homefoods.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String mFromDate;
    private String mFromTime;
    private Date mFromDate_date;
    private Date mToDate_date;
    private String mToDate;
    private String mToTime;
    private String mDishAddInfo;

    public Date getmFromDate_date() {
        return mFromDate_date;
    }

    public void setmFromDate_date(Date mFromDate_date) {
        this.mFromDate_date = mFromDate_date;
    }

    public Date getmToDate_date() {
        return mToDate_date;
    }

    public void setmToDate_date(Date mToDate_date) {
        this.mToDate_date = mToDate_date;
    }

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

    public String getmFromDate() {
        return mFromDate;
    }

    public void setmFromDate(String mFromDate) {
        this.mFromDate = mFromDate;
    }

    public String getmFromTime() {
        return mFromTime;
    }

    public String getmDishAddInfo() {
        return mDishAddInfo;
    }

    public void setmDishAddInfo(String mDishAddInfo) {
        this.mDishAddInfo = mDishAddInfo;
    }

    public void setmFromTime(String mFromTime) {
        this.mFromTime = mFromTime;
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
        dest.writeString(getmFromDate());
        dest.writeString(getmFromTime());
        dest.writeString(getmToDate());
        dest.writeString(getmToTime());
        dest.writeString(getmDishAddInfo());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy h : mm a");
        dest.writeString(dateFormat.format(getmFromDate_date()));
        dest.writeString(dateFormat.format(getmToDate_date()));

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
        setmFromDate(in.readString());
        setmFromTime(in.readString());
        setmToDate(in.readString());
        setmToTime(in.readString());
        setmDishAddInfo(in.readString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy h : mm a");
        try {
            setmFromDate_date(dateFormat.parse(in.readString()));
        } catch (ParseException e) {
            e.printStackTrace();
            Utils.notReached(e.getMessage());
        }

        try {
            setmToDate_date(dateFormat.parse(in.readString()));
        } catch (ParseException e) {
            e.printStackTrace();
            Utils.notReached(e.getMessage());
        }
    }

}
