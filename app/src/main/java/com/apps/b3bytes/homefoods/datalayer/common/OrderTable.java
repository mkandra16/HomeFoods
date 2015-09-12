package com.apps.b3bytes.homefoods.datalayer.common;

import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.FoodieOrder;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by sindhu on 8/14/2015.
 */
public interface OrderTable {
    public abstract void checkOutDish(DishOnSale dish, int qty, DataLayer.SaveCallback cb);
    public abstract void placeChefOrder(Foodie f, Set<String> dishOrders, double total,
                                        DataLayer.SaveCallback cb);
    public abstract void placeFoodieOrder(Set<String> chefOrders, double total,
                                            final DataLayer.SaveCallback cb);
    public abstract void getOrdersForChef(Foodie chef, DataLayer.getChefOrdersCallback cb);
    public abstract void getFoodieOrder(String orderId, DataLayer.GetFoodieOrderCallback cb);
    public abstract void deliverFoodieOrder(FoodieOrder foodieOrder, ChefOrder chefOrder,
                                            DataLayer.SaveCallback cb);
    public abstract void getFoodieOrders(EnumSet<FoodieOrder.OrderStatus> status, DataLayer.GetFoodieOrdersCallback cb);

}
