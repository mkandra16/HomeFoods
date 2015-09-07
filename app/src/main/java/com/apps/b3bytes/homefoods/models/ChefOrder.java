package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pavan on 8/15/2015.
 */
public class ChefOrder implements Parcelable{
    private Foodie mChef;
    private double mTotal;
    private Set<DishOrder> mDishOrders;
    private String mTag;
    private Foodie mFoodie;
    private FoodieOrder.OrderStatus mOrderStatus;

    public FoodieOrder.OrderStatus getmOrderStatus() {
        return mOrderStatus;
    }

    public void setmOrderStatus(FoodieOrder.OrderStatus mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ArrayList<DishOrder> aLDishOrder= new ArrayList<DishOrder>();
        aLDishOrder.addAll(getmDishOrders());

        dest.writeParcelable(getmChef(), flags);
        dest.writeDouble(getmTotal());
        dest.writeList(aLDishOrder);
        dest.writeString(getmTag());
        dest.writeParcelable(getmFoodie(), flags);
    }


    public static final Parcelable.Creator<ChefOrder> CREATOR = new Parcelable.Creator<ChefOrder>() {
        public ChefOrder createFromParcel(Parcel in) {
            ChefOrder chefOrder = new ChefOrder(in);
            return chefOrder;
        }

        public ChefOrder[] newArray(int size) {
            return new ChefOrder[size];
        }
    };

    public ChefOrder(Parcel in) {
        setmChef((Foodie) in.readParcelable(Foodie.class.getClassLoader()));
        setmTotal(in.readDouble());
        ArrayList<DishOrder> aLDishOrder = new ArrayList<DishOrder>();
        in.readList(aLDishOrder, DishOrder.class.getClassLoader());
        mDishOrders.addAll(aLDishOrder);
        setmTag(in.readString());
        setmFoodie((Foodie) in.readParcelable(Foodie.class.getClassLoader()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChefOrder order = (ChefOrder) o;

        if (Double.compare(order.mTotal, mTotal) != 0) return false;
        if (!mChef.equals(order.mChef)) return false;
        if (mDishOrders != null ? !mDishOrders.equals(order.mDishOrders) : order.mDishOrders != null)
            return false;
        if (!mTag.equals(order.mTag)) return false;
        return !(mFoodie != null ? !mFoodie.equals(order.mFoodie) : order.mFoodie != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mChef.hashCode();
        temp = Double.doubleToLongBits(mTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (mDishOrders != null ? mDishOrders.hashCode() : 0);
        result = 31 * result + mTag.hashCode();
        result = 31 * result + (mFoodie != null ? mFoodie.hashCode() : 0);
        return result;
    }

    public ChefOrder() {
        mDishOrders = new HashSet<>();
    }

    public Foodie getmFoodie() {
        return mFoodie;
    }

    public void setmFoodie(Foodie mFoodie) {
        this.mFoodie = mFoodie;
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public Foodie getmChef() {
        return mChef;
    }

    public void setmChef(Foodie mChef) {
        this.mChef = mChef;
    }

    public double getmTotal() {
        return mTotal;
    }

    public void setmTotal(double mTotal) {
        this.mTotal = mTotal;
    }

    public Set<DishOrder> getmDishOrders() {
        return mDishOrders;
    }

    public void setmDishOrders(Set<DishOrder> mDishOrders) {
        this.mDishOrders = mDishOrders;
    }
    
    public void addDishOrder(DishOrder dishOrder) {
        assert ! this.mDishOrders.contains(dishOrder);
        this.mDishOrders.add(dishOrder);
    }
}
