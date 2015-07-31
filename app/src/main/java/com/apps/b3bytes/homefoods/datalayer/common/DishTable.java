package com.apps.b3bytes.homefoods.datalayer.common;

import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.Listener;

/**
 * Created by Pavan on 7/26/2015
 *
 * Interface for accessing Dish Table (irrespective of the Backend.
 */
public interface DishTable {

    public abstract  void addDishInBackground(Dish dish);
    // public abstract void getDishesByChef(String chefId, ResultObject...);
}
