package com.apps.b3bytes.homefoods.State;

import android.content.Context;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.OneDishOrder;

import java.util.ArrayList;
import java.util.HashMap;

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
}
