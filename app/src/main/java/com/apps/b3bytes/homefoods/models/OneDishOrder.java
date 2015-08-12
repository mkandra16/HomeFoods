package com.apps.b3bytes.homefoods.models;

public class OneDishOrder {
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

    public OneDishOrder(Dish dish, int qty ) {
        mDish = dish;
        this.quantity = qty;
        this.quantityDelivered = 0;
        this.quantityPending = 1;
    }

    public String getDishName() {
        return mDish.getmDishName();
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
}
