package com.apps.b3bytes.homefoods.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Created by sindhu on 8/15/2015.
 */
public  class Cart {
    private HashMap<Dish, Integer> dishesMap;
    private HashMap<Foodie, Double> chefMap;
    private double grandTotal;
    private HashMap<Dish, String> dishOrderIds;
    private HashMap<Foodie, String> chefOrderIds;

    public Cart() {
        dishesMap = new HashMap<Dish, Integer>();
        chefMap = new HashMap<Foodie, Double>();
        dishOrderIds = new HashMap<Dish, String>();
        chefOrderIds = new HashMap<Foodie, String>();
        this.grandTotal = 0;
    }

    public void clear() {
        dishesMap = new HashMap<Dish, Integer>();
        chefMap = new HashMap<Foodie, Double>();
        dishOrderIds = new HashMap<Dish, String>();
        chefOrderIds = new HashMap<Foodie, String>();
        this.grandTotal = 0;
    }

    public void add_to_bag(Dish dish) {
        int qty = 1;
        double chefTotal = dish.getmPrice();
        grandTotal += dish.getmPrice();
        if (dishesMap.containsKey(dish)) {
            qty = dishesMap.get(dish).intValue() + qty;
            dishesMap.remove(dish);
        }
        dishesMap.put(dish, qty);
        if (chefMap.containsKey(dish.getmChef())) {
            chefTotal = chefMap.get(dish.getmChef());
            chefTotal += dish.getmPrice();
            chefMap.remove(dish.getmChef());
        }
        chefMap.put(dish.getmChef(), chefTotal);
    }

    public Set<Foodie> chefsInCart() {
        return chefMap.keySet();
    }
    public Set<Dish> dishesInCart() { return dishesMap.keySet(); }

    public HashSet<Dish> chefDishesInCart(Foodie chef) {
        HashSet<Dish> dishes = new HashSet<Dish>();
        for (Dish d : dishesMap.keySet()) {
            if (d.getmChef().equals(chef)) {
                dishes.add(d);
            }
        }
        return dishes;
    }
    public double getGrandTotal() { return grandTotal; }

    public double getGrandTotalByChef(Foodie chef) {
        if (chefMap.containsKey(chef)) {
            return chefMap.get(chef);
        } else {
            return 0.0;
        }
    };

    public double getGrandTotalByDish(Dish dish) {
        if (dishesMap.containsKey(dish)) {
            return dishesMap.get(dish) * dish.getmPrice();
        } else {
            return 0.0;
        }
    }

    public int dishQtyInCart(Dish dish) {
        return dishesMap.get(dish);
    }

    public void setOrderId(Dish dish, String orderId) {
        assert ! dishOrderIds.containsKey(dish);
        dishOrderIds.put(dish, orderId);
    }

    public void setOrderId(Foodie chef, String orderId) {
        assert ! dishOrderIds.containsKey(chef);
        chefOrderIds.put(chef, orderId);
    }

    public Set<String> getAllDishOrderIds() {
        Set<String> orders = new HashSet<>();
        for (String id : dishOrderIds.values()) {
            assert ! orders.contains(id);
            orders.add(id);
        }
        return orders;

    }

    public Set<String> getAllDishOrderIdsByChef(Foodie chef) {
        Set<String> orders = new HashSet<>();
        for (Dish dish : dishesMap.keySet()) {
            if (dish.getmChef().equals(chef)) {
                String id = dishOrderIds.get(dish);
                assert !orders.contains(id);
                orders.add(id);
            }
        }
        return orders;
    }

    public Set<String> getAllChefOrderIds() {
        Set<String> orders = new HashSet<>();
        for (String id : chefOrderIds.values()) {
            assert ! orders.contains(id);
            orders.add(id);
        }
        return orders;
    }
}
