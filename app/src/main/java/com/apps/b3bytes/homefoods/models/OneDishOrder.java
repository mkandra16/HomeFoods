package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

public class OneDishOrder implements Parcelable {
    private Dish mDish;
    private int quantity; //posted
    private int quantityDelivered;
    private int quantityPending;

    public OneDishOrder(String dishName, int quantity, double unitPrice) {
        mDish = Dish.createDummyDish(dishName, "OneDishOrder", "OneDishOrder Method", 0);
        mDish.setmPrice(unitPrice);
        this.quantity = quantity;
        this.quantityDelivered = 0;
        this.quantityPending = quantity;
    }

    public OneDishOrder(Dish dish, int qty) {
        mDish = dish;
        this.quantity = qty;
        this.quantityDelivered = 0;
        this.quantityPending = 1;
    }

    public String getDishName() {
        return mDish.getmDishName();
    }

    public Dish getmDish() {
        return mDish;
    }

    public void setmDish(Dish mDish) {
        this.mDish = mDish;
    }

    public void setDishName(String dishName) {
        mDish.setmDishName(dishName);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return mDish.getmPrice();
    }

    public void setUnitPrice(double unitPrice) {
        mDish.setmPrice(unitPrice);
    }

    public int getQuantityDelivered() {
        return quantityDelivered;
    }

    public void setQuantityDelivered(int quantityDelivered) {
        this.quantityDelivered = quantityDelivered;
    }

    public int getQuantityPending() {
        return quantityPending;
    }

    public void setQuantityPending(int quantityPending) {
        this.quantityPending = quantityPending;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mDish, flags);
        dest.writeInt(getQuantity());
        dest.writeInt(getQuantityDelivered());
        dest.writeInt(getQuantityPending());
    }

    public static final Parcelable.Creator<OneDishOrder> CREATOR = new Parcelable.Creator<OneDishOrder>() {
        public OneDishOrder createFromParcel(Parcel in) {
            OneDishOrder dish = new OneDishOrder(in);
            return dish;
        }

        public OneDishOrder[] newArray(int size) {
            return new OneDishOrder[size];
        }
    };

    public OneDishOrder(Parcel in) {
        setmDish((Dish) in.readParcelable(Dish.class.getClassLoader()));
        setQuantity(in.readInt());
        setQuantityDelivered(in.readInt());
        setQuantityPending(in.readInt());
    }

}
