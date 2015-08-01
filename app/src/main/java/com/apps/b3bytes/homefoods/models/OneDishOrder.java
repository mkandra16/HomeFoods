package com.apps.b3bytes.homefoods.models;

public class OneDishOrder {
    private String dishName;
    private int quantity; //posted
    private int quantityDelivered;
    private int quantityPending;
    private double unitPrice;

    public OneDishOrder(String dishName, int quantity, double unitPrice) {
        this.dishName = dishName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.quantityDelivered = 0;
        this.quantityPending = quantity;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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
