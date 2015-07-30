package com.apps.b3bytes.homefoods.datalayer.common;

import android.content.Context;

import com.apps.b3bytes.homefoods.datalayer.parse.ParseFoodieTable;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by sindhu on 7/26/2015.
 */
public class DataLayer {
    enum Backend {UNKNOWN, PARSE};
    private static final Backend  mCurBackend = Backend.PARSE;
    private static final String PARSEAPPID = "ZWqK3UW3ePNeSReJmDGEVk2V5DmulwNOHsNDDsc8";
    private static final String PARSECLIENTKEY = "PThw1qVFNYiKAZ8cdbIECLnJEyakl9nkPDmbLnAD";
    //private DishTable mDishTable;
    private FoodieTable mFoodieTable;

    public DataLayer(Context context) {
        assert mCurBackend == Backend.PARSE;
        // Enable Local Datastore.
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, PARSEAPPID, PARSECLIENTKEY);
        mFoodieTable = new ParseFoodieTable();
    }
    public void registerFoodie(Foodie f) {
        mFoodieTable.registerFoodieInBackground(f);
    }
    // getNearByDishes();
    // getRelatedDishes(Dish d);
    // getDishesByChef(String chefId);


}
