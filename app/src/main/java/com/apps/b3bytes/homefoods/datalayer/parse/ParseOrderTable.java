package com.apps.b3bytes.homefoods.datalayer.parse;

import android.util.Log;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.datalayer.common.OrderTable;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sindhu on 8/14/2015.
 */
public class ParseOrderTable implements OrderTable {
    @Override
    public void checkOutDish(Dish dish, int qty, final DataLayer.OrderCallback cb) {

        final ParseObject dishOrder = new ParseObject("DishOrder");
        dishOrder.put("Foodie", ParseUser.getCurrentUser());
        ParseObject dishObj= ParseObject.createWithoutData("Dish", dish.getmTag());
        dishOrder.put("Dish", dishObj);
        dishOrder.put("Qty", qty);
        dishOrder.put("Price", dish.getmPrice() * qty);
        dishOrder.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                cb.done(dishOrder.getObjectId(), e);
            }
        });
    }

    @Override
    public void placeChefOrder(Foodie chef, Set<String> dishOrders, double total,
                                        final DataLayer.OrderCallback cb) {
        final ParseObject chefOrder = new ParseObject("ChefOrder");
        chefOrder.put("Foodie", ParseUser.getCurrentUser());
        ArrayList<ParseObject> aDishOrderObj = new ArrayList<ParseObject>();
        for (String s : dishOrders) {
            ParseObject dishOrder= ParseObject.createWithoutData("DishOrder", s);
            aDishOrderObj.add(dishOrder);
        }
        chefOrder.put("DishOrders", aDishOrderObj);
        chefOrder.put("ChefTotal", total);
        ParseObject chefObj = ParseUser.createWithoutData("_User", chef.getmTag());
        chefOrder.put("Chef", chefObj);
        chefOrder.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                cb.done(chefOrder.getObjectId(), e);
            }
        });
    }

    @Override
    public void placeFoodieOrder(Set<String> chefOrders, double total,
                               final DataLayer.OrderCallback cb) {
        final ParseObject foodieOrder = new ParseObject("FoodieOrder");
        foodieOrder.put("Foodie", ParseUser.getCurrentUser());
        ArrayList<ParseObject> aChefOrderObj = new ArrayList<ParseObject>();
        for (String s : chefOrders) {
            ParseObject chefOrder= ParseObject.createWithoutData("ChefOrder", s);
            aChefOrderObj.add(chefOrder);
        }
        foodieOrder.put("ChefOrders", aChefOrderObj);
        foodieOrder.put("TotalPrice", total);
        foodieOrder.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                cb.done(foodieOrder.getObjectId(), e);
            }
        });
    }

    @Override
    public void getOrdersForChef(Foodie chef) {
        ParseQuery query = ParseQuery.getQuery("ChefOrder");
        query.include("DishOrders");
        query.include("DishOrder.Dish");
        query.include("Foodie");
        ParseObject chefObj = ParseUser.createWithoutData("_User", "WW2iTg5tqL");
        query.whereEqualTo("Chef", chefObj);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
                                   public void done(List<ParseObject> chefOrders, ParseException e) {
                                       if (e == null) {
                                           Log.d("score", "Retrieved " + chefOrders.size() + " orders");

//                                           callback.done(ParseList2DishList(dishList), e);
                                       } else {
                                           Log.d("score", "Error: " + e.getMessage());
                                       }
                                   }
                               }
        );

    }

}
