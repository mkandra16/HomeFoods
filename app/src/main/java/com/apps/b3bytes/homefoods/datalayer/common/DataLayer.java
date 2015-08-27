package com.apps.b3bytes.homefoods.datalayer.common;

import android.content.Context;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.datalayer.parse.ParseDishTable;
import com.apps.b3bytes.homefoods.datalayer.parse.ParseFoodieTable;
import com.apps.b3bytes.homefoods.datalayer.parse.ParseOrderTable;
import com.apps.b3bytes.homefoods.models.Cart;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.FoodieOrder;
import com.parse.Parse;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by sindhu on 7/26/2015.
 */
public class DataLayer {
    // Sample functions
    // Usage: dataLayer.addFewDishes_sample(getApplicationContext());
    public void addFewDishes_sample(final Context context) {
        String dishes[][] = {
                {"Gongura", "AndhraMata Gongura", "Boil Gongura. Grind it along with onions and chillies. Add Tadka!!!", "20"},
                {"Pappu", "Served with YUMMYYY chintakaya pachaddi", "Home made fresh chintakaya pachhadi with fresh ingredients", "10"},
                {"Beans Curry", "Made with fresh long beans", " Garnished with Home made spices with cocunt powder", "40"},
                {"Curry3", "AndhraMata Gongura", "Boil Gongura. Grind it along with onions and chillies. Add Tadka!!!", "20"},
                {"Curry4", "Served with YUMMYYY chintakaya pachaddi", "Home made fresh chintakaya pachhadi with fresh ingredients", "10"},
                {"Curry5", "Made with fresh long beans", " Garnished with Home made spices with cocunt powder", "40"},
                {"Curry6", "AndhraMata Gongura", "Boil Gongura. Grind it along with onions and chillies. Add Tadka!!!", "20"},
                {"Curry7", "Served with YUMMYYY chintakaya pachaddi", "Home made fresh chintakaya pachhadi with fresh ingredients", "10"},
                {"Curry8", "Made with fresh long beans", " Garnished with Home made spices with cocunt powder", "40"},
                {"Curry9", "AndhraMata Gongura", "Boil Gongura. Grind it along with onions and chillies. Add Tadka!!!", "20"},
                {"Curry10", "Served with YUMMYYY chintakaya pachaddi", "Home made fresh chintakaya pachhadi with fresh ingredients", "10"},
                {"Curry11", "Made with fresh long beans", " Garnished with Home made spices with cocunt powder", "40"},
                {"Curry12", "AndhraMata Gongura", "Boil Gongura. Grind it along with onions and chillies. Add Tadka!!!", "20"},
                {"Curry13", "Served with YUMMYYY chintakaya pachaddi", "Home made fresh chintakaya pachhadi with fresh ingredients", "10"},
                {"Curry14", "Made with fresh long beans", " Garnished with Home made spices with cocunt powder", "40"},
                {"Curry15", "AndhraMata Gongura", "Boil Gongura. Grind it along with onions and chillies. Add Tadka!!!", "20"},
                {"Curry16", "Served with YUMMYYY chintakaya pachaddi", "Home made fresh chintakaya pachhadi with fresh ingredients", "10"},
                {"Curry17", "Made with fresh long beans", " Garnished with Home made spices with cocunt powder", "40"},
                {"Curry18", "AndhraMata Gongura", "Boil Gongura. Grind it along with onions and chillies. Add Tadka!!!", "20"},
                {"Curry19", "Served with YUMMYYY chintakaya pachaddi", "Home made fresh chintakaya pachhadi with fresh ingredients", "10"},
                {"Curry20", "Made with fresh long beans", " Garnished with Home made spices with cocunt powder", "40"},
                {"Curry21", "AndhraMata Gongura", "Boil Gongura. Grind it along with onions and chillies. Add Tadka!!!", "20"},
                {"Curry22", "Served with YUMMYYY chintakaya pachaddi", "Home made fresh chintakaya pachhadi with fresh ingredients", "10"},
                {"Curry23", "Made with fresh long beans", " Garnished with Home made spices with cocunt powder", "40"},
                {"Curry24", "AndhraMata Gongura", "Boil Gongura. Grind it along with onions and chillies. Add Tadka!!!", "20"},
                {"Curry25", "Served with YUMMYYY chintakaya pachaddi", "Home made fresh chintakaya pachhadi with fresh ingredients", "10"},
                {"Curry26", "Made with fresh long beans", " Garnished with Home made spices with cocunt powder", "40"}
        };
        for (int i = 0; i <= 25; i++) {
            final Dish d = Dish.createDummyDish(dishes[i][0], dishes[i][1], dishes[i][2], Integer.parseInt(dishes[i][3]));
            publishDish(d, new com.apps.b3bytes.homefoods.datalayer.common.DataLayer.PublishCallback() {
                @Override
                public void done(Exception e) {
                    if (e == null) {
                        Toast t = Toast.makeText(context,
                                "Published Dish " + d.getmDishName(), Toast.LENGTH_LONG);
                        t.show();
                    } else {
                        Toast t = Toast.makeText(context,
                                "Failed to publish dish" + d.getmDishName(), Toast.LENGTH_LONG);
                        t.show();
                    }
                }
            });
        }
        return;
    }

    // Usage :  dataLayer.queryFewDishes_sample(getApplicationContext());
    public void queryFewDishes_sample(final Context context) {
        getNearByDishes(10, new DataLayer.DishQueryCallback() {
            @Override
            public void done(ArrayList<DishOnSale> list, Exception e) {
                if (e == null) {
                    Toast t = Toast.makeText(context,
                            "Received Dishes, count " + list.size(), Toast.LENGTH_LONG);
                    t.show();
                    for (DishOnSale d : list) {
                        Toast.makeText(context, d.getmDish().getmDishName(), Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast t = Toast.makeText(context,
                            "Failed to Retrieve dishes", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }

    public static abstract class SignInCallback {
        public abstract void done(Foodie f, Exception e);
    }

    ;

    public static abstract class RegistrationCallback {
        public abstract void done(Foodie f, Exception e);
    }

    ;

    public static abstract class PublishCallback {
        public abstract void done(Exception e);
    }

    public static abstract class DishQueryCallback {
        public abstract void done(ArrayList<DishOnSale> list, Exception e);
    }

    enum Backend {UNKNOWN, PARSE}

    ;
    private static final Backend mCurBackend = Backend.PARSE;
    private static final String PARSEAPPID = "ZWqK3UW3ePNeSReJmDGEVk2V5DmulwNOHsNDDsc8";
    private static final String PARSECLIENTKEY = "PThw1qVFNYiKAZ8cdbIECLnJEyakl9nkPDmbLnAD";
    private Context mContext;
    //private DishTable mDishTable;
    private FoodieTable mFoodieTable;
    private DishTable mDishTable;
    private OrderTable mOrderTable;

    public DataLayer(Context context) {
        mContext = context;
        assert mCurBackend == Backend.PARSE;
        // Enable Local Datastore.
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, PARSEAPPID, PARSECLIENTKEY);
        mFoodieTable = new ParseFoodieTable();
        mDishTable = new ParseDishTable();
        mOrderTable = new ParseOrderTable();
    }

    public void registerFoodie(Foodie f, RegistrationCallback callback) {
        mFoodieTable.registerFoodieInBackground(f, callback);
    }

    public void signInFoodie(String UserName, String password, SignInCallback callback) {
        mFoodieTable.signInFoodie(UserName, password, callback);
    }

    public void signUpAsChef(Foodie f) {
        //      if(f.getmChef() == null) {
        //          f.signUpAsChef();
        //          dataLayer.signUpAsChef(f);
        //      }
    }

    public void signOut() {
        ParseUser.logOut();
    }

    public void publishDish(Dish d, com.apps.b3bytes.homefoods.datalayer.common.DataLayer.PublishCallback c) {
        mDishTable.addDishInBackground(d, c);
    }
    public void putDishOnSale(final DishOnSale d, final PublishCallback cb) {
        // first publish Dish
        publishDish(d.getmDish(), new com.apps.b3bytes.homefoods.datalayer.common.DataLayer.PublishCallback() {
            @Override
            public void done(Exception e) {
                if (e == null) {
                    mDishTable.putDishOnSale(d, cb);
                } else {
                    cb.done(e);
                }
            }
        });
    }

    public void getNearByDishes(int radius, DishQueryCallback callback) {
        mDishTable.getNearbyDishes(radius, callback);
    }

    ;

    // getRelatedDishes(Dish d);
    // getDishesByChef(String chefId);
    public static abstract class OrderCallback {
        public abstract void done(String OrderId, Exception e);
    }

    public void checkOutCart(final Cart cart, final OrderCallback cb) {
            for (final DishOnSale dish : cart.dishesInCart()) {
                mOrderTable.checkOutDish(dish, cart.dishQtyInCart(dish), new OrderCallback() {

                    @Override
                    public void done(String DishId, Exception e) {
                        Foodie f = dish.getmDish().getmChef();
                        if (e == null) {
                            cart.setOrderId(dish, DishId);
                        } else {
                            Toast.makeText(mContext, "Failed to place Dish order", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            throw  new RuntimeException("Failed to place Foodie Order");
                        }
                        if (cart.dishesInCart().size() == cart.getAllDishOrderIds().size()) {
                            addChefOrders(cart, cb);
                        }
                    }
                });
            }
    }

    private void addChefOrders(final Cart cart, final OrderCallback cb) {
        for (final Foodie chef : cart.chefsInCart()) {
            mOrderTable.placeChefOrder(chef, cart.getAllDishOrderIdsByChef(chef),
                    cart.getGrandTotalByChef(chef), new OrderCallback() {
                @Override
                public void done(String OrderId, Exception e) {
                    if (e == null) {
                        cart.setOrderId(chef, OrderId);
                        if (cart.chefsInCart().size() == cart.getAllChefOrderIds().size()) {
                            addFinalFoodieOrder(cart, cb);
                        }
                    } else {
                        Toast.makeText(mContext, "Failed to place Chef order", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        throw  new RuntimeException("Failed to place Chef Order");
                    }
                }
            });
        }
    }

    private void addFinalFoodieOrder(final Cart cart, final OrderCallback cb) {
        mOrderTable.placeFoodieOrder(cart.getAllChefOrderIds(), cart.getGrandTotal(), cb);
    }

    public static abstract class getChefOrdersCallback {
        public abstract void done(ArrayList<ChefOrder> orders, Exception e);
    }

    public void getOrdersForChef(Foodie chef, getChefOrdersCallback cb) {
        mOrderTable.getOrdersForChef(chef, cb);
    }

    public static abstract class GetFoodieOrderCallback {
        public abstract void done(FoodieOrder order, Exception e);
    }

    //Retrieves Foodie Order to this chef based on orderId.
    public void getFoodieOrder(String orderId, GetFoodieOrderCallback cb ) {
        mOrderTable.getFoodieOrder(orderId, cb);
    }
}
