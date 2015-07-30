package com.apps.b3bytes.homefoods.datalayer.common;

import com.apps.b3bytes.homefoods.models.Foodie;

/**
 * Created by sindhu on 7/28/2015.
 */
public interface FoodieTable {
    public abstract void registerFoodieInBackground(Foodie foodie);
}
