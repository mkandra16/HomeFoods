package com.apps.b3bytes.homefoods.State;

import android.content.Context;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.Cart;
import com.apps.b3bytes.homefoods.models.Foodie;

import java.util.HashMap;
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
    private static Foodie mCurrentFoodie;

    public static Foodie getmCurrentFoodie() {
        return mCurrentFoodie;
    }

    public static void setmCurrentFoodie(Foodie mCurrentFoodie) {
        AppGlobalState.mCurrentFoodie = mCurrentFoodie;
    }

    public static void initialize(final Context context) {
        mContext = context;
        gCart = new Cart();
        if (gDataLayer == null) {
            gDataLayer = new DataLayer(context);
        }

    }
    public static void signIn(String uid, String password, final DataLayer.SignInCallback cb) {
        gDataLayer.signInFoodie(uid, password, new DataLayer.SignInCallback() {
            @Override
            public void done(Foodie f, Exception e) {
                if (e == null) {
                    Toast t = Toast.makeText(mContext,
                            "Welcome " + f.getmUserName(), Toast.LENGTH_LONG);
                    t.show();
                    mCurrentFoodie = f;
                    cb.done(f, e);
                } else {
                    Toast t = Toast.makeText(mContext, "SignIn failed", Toast.LENGTH_LONG);
                    t.show();
                    cb.done(f, e);
                }
            }
        });

    }

    public static void checkOutCart(final DataLayer.SaveCallback cb) {
        gDataLayer.checkOutCart(gCart, cb);
    }
}
