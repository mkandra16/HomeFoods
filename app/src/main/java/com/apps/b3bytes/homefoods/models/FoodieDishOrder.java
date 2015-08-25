package com.apps.b3bytes.homefoods.models;

/**
 * Created by Pavan on 8/23/2015.
 */
public class FoodieDishOrder  {
    private DishOnSale mdishOnSale;
    private int mQty;

    public FoodieDishOrder(DishOnSale mdishOnSale, int mQty) {
        this.mdishOnSale = mdishOnSale;
        this.mQty = mQty;
    }

    public DishOnSale getmDishOnSale() {
        return mdishOnSale;
    }

    public void setmDishOnSale(DishOnSale mdishOnSale) {
        this.mdishOnSale = mdishOnSale;
    }

    public int getmQty() {
        return mQty;
    }

    public void setmQty(int mQty) {
        this.mQty = mQty;
    }
}
