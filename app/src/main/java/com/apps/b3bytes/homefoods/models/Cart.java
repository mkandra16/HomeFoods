package com.apps.b3bytes.homefoods.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pavan on 8/15/2015.
 */
public  class Cart {
    private HashMap<DishOnSale, Integer> dishesMap;
    private HashMap<Foodie, Double> chefMap;
    private double grandTotal;
    private HashMap<DishOnSale, String> dishOrderIds;
    private HashMap<Foodie, String> chefOrderIds;

    public Cart() {
        dishesMap = new HashMap<DishOnSale, Integer>();
        chefMap = new HashMap<Foodie, Double>();
        dishOrderIds = new HashMap<DishOnSale, String>();
        chefOrderIds = new HashMap<Foodie, String>();
        this.grandTotal = 0;
    }

    public void clear() {
        dishesMap = new HashMap<DishOnSale, Integer>();
        chefMap = new HashMap<Foodie, Double>();
        dishOrderIds = new HashMap<DishOnSale, String>();
        chefOrderIds = new HashMap<Foodie, String>();
        this.grandTotal = 0;
    }

    public void add_to_bag(DishOnSale dishOnSale) {
        int qty = 1;
        double chefTotal = dishOnSale.getmUnitPrice();
        grandTotal += dishOnSale.getmUnitPrice();
        if (dishesMap.containsKey(dishOnSale)) {
            qty = dishesMap.get(dishOnSale).intValue() + qty;
            dishesMap.remove(dishOnSale);
        }
        dishesMap.put(dishOnSale, qty);
        Foodie chef = dishOnSale.getmDish().getmChef();
        if (chefMap.containsKey(chef)) {
            chefTotal = chefMap.get(chef);
            chefTotal += dishOnSale.getmUnitPrice();
            chefMap.remove(chef);
        }
        chefMap.put(chef, chefTotal);
    }
    private void removeDish(DishOnSale dishOnSale) {
        assert dishesMap.containsKey(dishOnSale);
        // Adjust Grand Total
        grandTotal -= getGrandTotalByDish(dishOnSale);

        // Adjust Chef Total, Remove Chef if not needed.
        assert chefMap.containsKey(dishOnSale.getmDish().getmChef());
        assert chefMap.get(dishOnSale.getmDish().getmChef()) >= getGrandTotalByDish(dishOnSale);

        double chefTotal = chefMap.get(dishOnSale.getmDish().getmChef()) - getGrandTotalByDish(dishOnSale);
        chefMap.remove(dishOnSale.getmDish().getmChef());

        if (chefTotal != 0) {
            chefMap.put(dishOnSale.getmDish().getmChef(), chefTotal);
        }

        // Remove Dish from DishList
        dishesMap.remove(dishOnSale);
    }

    // This is special private interface which doesn't expect item to be present in the cart.
    // If item is already present, caller should call removeDish() first.
    private void add_to_bag(DishOnSale dishOnSale, int qty) {
        assert qty != 0;
        assert ! dishesMap.containsKey(dishOnSale);

        // Add Dish to DishList
        dishesMap.put(dishOnSale, qty);
        // Adjust Grand Total
        grandTotal += getGrandTotalByDish(dishOnSale);
        // Adjust Chef Total, Add Chef if new.
        double chefTotal = getGrandTotalByDish(dishOnSale);
        if (chefMap.containsKey(dishOnSale.getmDish().getmChef())) {
            chefTotal += chefMap.get(dishOnSale.getmDish().getmChef());
            chefMap.remove(dishOnSale.getmDish().getmChef());
        }
        chefMap.put(dishOnSale.getmDish().getmChef(), chefTotal);
    }

    public void setQty(DishOnSale dishOnSale, int qty) {
        // First remove current dish
        removeDish(dishOnSale);
        // Add new dish
        if (qty != 0) {
            add_to_bag(dishOnSale, qty);
        }
    }

    public Set<Foodie> chefsInCart() {
        return chefMap.keySet();
    }
    public Set<DishOnSale> dishesInCart() { return dishesMap.keySet(); }

    public int getNumDishesInCart() {
        int totalItems = 0;
        for (DishOnSale d : dishesMap.keySet()) {
            totalItems += dishQtyInCart(d);
        }
        return totalItems;
    }

    public HashSet<DishOnSale> chefDishesInCart(Foodie chef) {
        HashSet<DishOnSale> dishes = new HashSet<DishOnSale>();
        for (DishOnSale d : dishesMap.keySet()) {
            if (d.getmDish().getmChef().equals(chef)) {
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

    public double getGrandTotalByDish(DishOnSale dish) {
        if (dishesMap.containsKey(dish)) {
            return dishesMap.get(dish) * dish.getmUnitPrice();
        } else {
            return 0.0;
        }
    }

    public int dishQtyInCart(DishOnSale dish) {
        return dishesMap.get(dish);
    }

    public void setOrderId(DishOnSale dish, String orderId) {
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
        for (DishOnSale dish : dishesMap.keySet()) {
            if (dish.getmDish().getmChef().equals(chef)) {
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
