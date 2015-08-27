package com.apps.b3bytes.homefoods.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pavan on 8/15/2015.
 */
public class ChefOrder {
    private Foodie mChef;
    private double mTotal;
    private Set<DishOrder> mDishOrders;
    private String mTag;
    private Foodie mFoodie;

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
