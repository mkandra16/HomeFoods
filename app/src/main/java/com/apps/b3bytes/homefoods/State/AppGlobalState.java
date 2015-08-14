package com.apps.b3bytes.homefoods.State;

import android.content.Context;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.OneDishOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Pavan on 8/10/2015.
 */

public class AppGlobalState {
    public static DataLayer gDataLayer;
    // HashMap<Dish, qty>
    public static HashMap<Dish, Integer> gCart;

    public static void initialize(final Context context) {
        gCart = new HashMap<Dish, Integer>();
        if (AppGlobalState.gDataLayer == null) {
            AppGlobalState.gDataLayer = new DataLayer(context);
/*
            gDataLayer.signInFoodie("Pavan", "welcome", new DataLayer.SignInCallback() {
                @Override
                public void done(Foodie f, Exception e) {
                    if (e == null) {
                        Toast t = Toast.makeText(context,
                                "Welcome " + f.getmUserName(), Toast.LENGTH_LONG);
                        t.show();
                    } else {
                        Toast t = Toast.makeText(context, "SignIn failed", Toast.LENGTH_LONG);
                        t.show();
                    }
                }
            });
*/
        }

    }

    public static void add_to_bag(Dish dish) {
        int qty = 1;
        if (gCart.containsKey(dish)) {
            qty = gCart.get(dish).intValue() + qty;
            gCart.remove(dish);
        }
        gCart.put(dish, qty);
    }

    public static HashSet<Foodie> chefsInCart() {
        HashSet<Foodie> chefIds = new HashSet<Foodie>();

        for (Dish d : AppGlobalState.gCart.keySet()) {
            chefIds.add(d.getmChef());
        }
        return chefIds;
    }

    public static HashSet<Dish> chefDishesInCart(Foodie chef) {
        HashSet<Dish> dishes = new HashSet<Dish>();
        for (Dish d : AppGlobalState.gCart.keySet()) {
            if (d.getmChef().equals(chef)) {
                dishes.add(d);
            }
        }
        return dishes;
    }

    public static int dishQtyInCart(Dish dish) {
        return gCart.get(dish);
    }
}
