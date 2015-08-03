package com.apps.b3bytes.homefoods.datalayer.common;

import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.Listener;

/**
 * Created by sindhu on 7/28/2015.
 */
public interface FoodieTable {
    public abstract void registerFoodieInBackground(Foodie foodie,
                                                    DataLayer.RegistrationCallback callback);

    public abstract void signInFoodie(String userName, String password,
                                      DataLayer.SignInCallback callback);
}
