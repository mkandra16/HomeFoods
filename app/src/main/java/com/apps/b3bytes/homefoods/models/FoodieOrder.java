package com.apps.b3bytes.homefoods.models;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pavan on 8/27/2015.
 */
public class FoodieOrder {
    public enum OrderStatus {Cancelled, Ordered, Delivered, Unknown};

    private String mTag;
    private Set<ChefOrder> mChefOrders;
    private Foodie mFoodie;
    private double mTotal;
    private OrderStatus mOrderStatus;

    public FoodieOrder() {
        mChefOrders = new HashSet<ChefOrder>();
        setmOrderStatus(OrderStatus.Unknown);
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public Set<ChefOrder> getmChefOrders() {
        return mChefOrders;
    }

    public void setmChefOrders(Set<ChefOrder> mChefOrders) {
        this.mChefOrders = mChefOrders;
    }

    public void addmChefOrder(ChefOrder chefOrder) {
        this.mChefOrders.add(chefOrder);
    }

    public Foodie getmFoodie() {
        return mFoodie;
    }

    public void setmFoodie(Foodie mFoodie) {
        this.mFoodie = mFoodie;
    }

    public double getmTotal() {
        return mTotal;
    }

    public void setmTotal(double mTotal) {
        this.mTotal = mTotal;
    }

    public OrderStatus getmOrderStatus() {
        return mOrderStatus;
    }

    public void setmOrderStatus(OrderStatus mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
    }
}
