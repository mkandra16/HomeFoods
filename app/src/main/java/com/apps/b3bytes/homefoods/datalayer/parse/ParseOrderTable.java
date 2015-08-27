package com.apps.b3bytes.homefoods.datalayer.parse;

import android.util.Log;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.datalayer.common.OrderTable;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.DishOrder;
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
    public void checkOutDish(DishOnSale dish, int qty, final DataLayer.OrderCallback cb) {

        final ParseObject dishOrder = new ParseObject("DishOrder");
        dishOrder.put("Foodie", ParseUser.getCurrentUser());
        ParseObject dishObj= ParseObject.createWithoutData("DishOnSale", dish.getmTag());
        dishOrder.put("DishOnSale", dishObj);
        dishOrder.put("Qty", qty);
        dishOrder.put("Price", dish.getmUnitPrice() * qty);
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

    private DishOrder ParseObj2DishOrder(ParseObject object) {
        DishOrder order = new DishOrder();
        order.setmQty(object.getInt("Qty"));
        order.setmDishOnSale(ParseDishTable.ParseObject2DishOnSale(object.getParseObject("DishOnSale")));
        order.setmFoodie(ParseFoodieTable.parseUser2Foodie(object.getParseUser("Foodie")));
        order.setmTag(object.getObjectId());
        return order;
    }
    private ChefOrder ParseObj2ChefOrder(ParseObject obj) {
        ChefOrder order = new ChefOrder();
        order.setmTotal(obj.getDouble("ChefTotal"));
        order.setmChef(ParseFoodieTable.parseUser2Foodie(obj.getParseUser("Chef")));
        order.setmFoodie(ParseFoodieTable.parseUser2Foodie(obj.getParseUser("Foodie")));
        order.setmTag(obj.getObjectId());
        List<ParseObject> dishOrders = obj.getList("DishOrders");
        for (ParseObject dishOrderobj : dishOrders){
            order.addDishOrder(ParseObj2DishOrder(dishOrderobj));
        }
        return order;
    }
    private ArrayList<ChefOrder> convert2ChefOrders(List<ParseObject> parseChefOrders) {
        ArrayList<ChefOrder> chefOrders = new ArrayList<ChefOrder>();
        for (ParseObject obj : parseChefOrders) {
            chefOrders.add(ParseObj2ChefOrder(obj));
        }
        return chefOrders;
    }
    @Override
    public void getOrdersForChef(Foodie chef, final DataLayer.getChefOrdersCallback cb) {
        ParseQuery query = ParseQuery.getQuery("ChefOrder");
        query.include("DishOrders");
        query.include("DishOrders.DishOnSale");
        query.include("DishOrders.DishOnSale.Dish");
        query.include("DishOrders.DishOnSale.Dish.Chef");
        query.include("DishOrders.Foodie");
        query.include("Foodie");
        query.include("Chef");
        ParseObject chefObj = ParseUser.createWithoutData("_User", "WW2iTg5tqL");
//         ParseObject chefObj = ParseUser.createWithoutData("_User", "WW2iTg5pqr");

        query.whereEqualTo("Chef", chefObj);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
                                   public void done(List<ParseObject> chefOrders, ParseException e) {
                                       if (e == null) {
                                           Log.d("score", "Retrieved " + chefOrders.size() + " orders");
                                            ArrayList<ChefOrder> orders = convert2ChefOrders(chefOrders);
                                            cb.done(orders, e);
//                                           callback.done(ParseList2DishList(dishList), e);
                                       } else {
                                           Log.d("score", "Error: " + e.getMessage());
                                           ArrayList<ChefOrder> orders = new ArrayList<ChefOrder>();
                                           cb.done(orders, e);
                                       }
                                   }
                               }
        );

    }

}
