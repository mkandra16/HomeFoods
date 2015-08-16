package com.apps.b3bytes.homefoods.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sindhu on 8/15/2015.
 */
public class ChefOrder {
    private Foodie mChef;
    private double mTotal;
    private Set<DishOrder> mDishOrders;
    private String mTag;
    private Foodie mFoodie;

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
