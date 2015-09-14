package com.apps.b3bytes.homefoods.datalayer.common;

import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.DishOnSale;

/**
 * Created by Pavan on 7/26/2015
 *
 * Interface for accessing Dish Table (irrespective of the Backend.
 */
public interface DishTable {

    public abstract  void addDishInBackground(Dish dish, DataLayer.PublishCallback c);
    public abstract void getNearbyDishes(int radius, int skip, int count, DataLayer.DishQueryCallback callback);
    public abstract void putDishOnSale(DishOnSale dish, DataLayer.PublishCallback cb);
}
