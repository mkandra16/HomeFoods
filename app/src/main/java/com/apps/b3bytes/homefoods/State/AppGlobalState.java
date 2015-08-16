package com.apps.b3bytes.homefoods.State;

import android.content.Context;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.Cart;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.Foodie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Pavan on 8/10/2015.
 */

public final class AppGlobalState {
    public static DataLayer gDataLayer;
    // HashMap<Dish, qty>

    public static Cart gCart;
    // All orders grouped by chef
    private static HashMap<Foodie, Vector<String>> chefOrders;
    private static Context mContext;

    public static void initialize(final Context context) {
        mContext = context;
        gCart = new Cart();
        if (gDataLayer == null) {
            gDataLayer = new DataLayer(context);
        }

    }


    public static void checkOutCart() {
        gDataLayer.checkOutCart(gCart, new DataLayer.OrderCallback() {
            @Override
            public void done(String OrderId, Exception e) {
                if (e == null) {
                    Toast.makeText(mContext, "Placed order : " + OrderId, Toast.LENGTH_SHORT).show();
                    gCart.clear();
                } else {
                    Toast.makeText(mContext, "Failed to place order", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
