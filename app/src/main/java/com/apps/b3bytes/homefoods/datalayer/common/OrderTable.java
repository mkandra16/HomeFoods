package com.apps.b3bytes.homefoods.datalayer.common;

import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.Foodie;

import java.util.Set;
import java.util.Vector;

/**
 * Created by sindhu on 8/14/2015.
 */
public interface OrderTable {
    public abstract void checkOutDish(DishOnSale dish, int qty, DataLayer.OrderCallback cb);
    public abstract void placeChefOrder(Foodie f, Set<String> dishOrders, double total,
                                        DataLayer.OrderCallback cb);
    public abstract void placeFoodieOrder(Set<String> chefOrders, double total,
                                            final DataLayer.OrderCallback cb);
    public abstract void getOrdersForChef(Foodie chef, DataLayer.getChefOrdersCallback cb);
    public abstract void getFoodieOrder(String orderId, DataLayer.GetFoodieOrderCallback cb);
}
