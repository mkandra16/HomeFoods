package com.apps.b3bytes.homefoods.datalayer.common;

import android.content.Context;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.datalayer.parse.ParseDishTable;
import com.apps.b3bytes.homefoods.datalayer.parse.ParseFoodieTable;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.Listener;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by sindhu on 7/26/2015.
 */
public class DataLayer {
// Sample functions
    public void addFewDishes(final Context context){
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
            Dish d = Dish.createDummyDish(dishes[i][0], dishes[i][1], dishes[i][2], Integer.parseInt(dishes[i][3]));
                publishDish(d, new DataLayer.DishPublishCallback() {
                @Override
                public void done(Dish d, Exception e) {
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


    public static abstract class SignInCallback {
        public abstract void done(Foodie f, Exception e);
    };

    public static abstract class RegistrationCallback {
        public abstract void done(Foodie f, Exception e);
    };
    public static abstract class DishPublishCallback {
        public abstract void done(Dish d, Exception e);
    }
    public static abstract class DishQueryCallback {
        public abstract void done(ArrayList<Dish> list, Exception e);
    }
    enum Backend {UNKNOWN, PARSE};
    private static final Backend  mCurBackend = Backend.PARSE;
    private static final String PARSEAPPID = "ZWqK3UW3ePNeSReJmDGEVk2V5DmulwNOHsNDDsc8";
    private static final String PARSECLIENTKEY = "PThw1qVFNYiKAZ8cdbIECLnJEyakl9nkPDmbLnAD";
    //private DishTable mDishTable;
    private FoodieTable mFoodieTable;
    private DishTable mDishTable;
    public DataLayer(Context context) {
        assert mCurBackend == Backend.PARSE;
        // Enable Local Datastore.
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, PARSEAPPID, PARSECLIENTKEY);
        mFoodieTable = new ParseFoodieTable();
        mDishTable = new ParseDishTable();
    }
    public void registerFoodie(Foodie f,RegistrationCallback callback) {
        mFoodieTable.registerFoodieInBackground(f, callback);
    }
    public void signInFoodie(String UserName, String password, SignInCallback callback) {
        mFoodieTable.signInFoodie(UserName, password, callback);
    }
    public void signUpAsChef(Foodie f) {

    }
    public void publishDish(Dish d, DishPublishCallback c) {
        mDishTable.addDishInBackground(d, c);
    }
    // getNearByDishes();
    // getRelatedDishes(Dish d);
    // getDishesByChef(String chefId);



}
