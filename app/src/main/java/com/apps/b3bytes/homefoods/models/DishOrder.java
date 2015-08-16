package com.apps.b3bytes.homefoods.models;

/**
 * Created by sindhu on 8/15/2015.
 */
public class DishOrder {
    private Dish mDish;
    private int mQty;
    private Foodie mFoodie;
    private String mTag;

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
}
