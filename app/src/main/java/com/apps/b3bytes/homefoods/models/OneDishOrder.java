package com.apps.b3bytes.homefoods.models;

public class OneDishOrder {
    private String dishName;
    private int quantity;
    private double unitPrice;

    public OneDishOrder(String dishName, int quantity, double unitPrice) {
        this.dishName = dishName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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
}
