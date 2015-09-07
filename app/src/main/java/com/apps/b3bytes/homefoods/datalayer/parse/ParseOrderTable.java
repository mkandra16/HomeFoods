package com.apps.b3bytes.homefoods.datalayer.parse;

import android.util.Log;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.datalayer.common.OrderTable;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.DishOrder;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.FoodieOrder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sindhu on 8/14/2015.
 */
public class ParseOrderTable implements OrderTable {
    @Override
    public void checkOutDish(DishOnSale dish, int qty, final DataLayer.SaveCallback cb) {

        final ParseObject dishOrder = new ParseObject("DishOrder");
        dishOrder.put("Foodie", ParseUser.getCurrentUser());
        ParseObject dishObj= ParseObject.createWithoutData("DishOnSale", dish.getmTag());
        dishOrder.put("DishOnSale", dishObj);
        dishOrder.put("Qty", qty);
        dishOrder.put("Price", dish.getmUnitPrice() * qty);
        dishOrder.saveInBackground(new com.parse.SaveCallback() {
            @Override
            public void done(ParseException e) {
                cb.done(dishOrder.getObjectId(), e);
            }
        });
    }

    @Override
    public void placeChefOrder(Foodie chef, Set<String> dishOrders, double total,
                                        final DataLayer.SaveCallback cb) {
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
        chefOrder.put("Status", FoodieOrder.OrderStatus.Ordered.toString());
        chefOrder.saveInBackground(new com.parse.SaveCallback() {
            @Override
            public void done(ParseException e) {
                cb.done(chefOrder.getObjectId(), e);
            }
        });
    }

    @Override
    public void placeFoodieOrder(Set<String> chefOrders, double total,
                               final DataLayer.SaveCallback cb) {
        final ParseObject foodieOrder = new ParseObject("FoodieOrder");
        foodieOrder.put("Foodie", ParseUser.getCurrentUser());
        ArrayList<ParseObject> aChefOrderObj = new ArrayList<ParseObject>();
        for (String s : chefOrders) {
            ParseObject chefOrder= ParseObject.createWithoutData("ChefOrder", s);
            aChefOrderObj.add(chefOrder);
        }
        foodieOrder.put("ChefOrders", aChefOrderObj);
        foodieOrder.put("TotalPrice", total);
        foodieOrder.put("Status", FoodieOrder.OrderStatus.Ordered.toString());
        foodieOrder.saveInBackground(new com.parse.SaveCallback() {
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
        order.setmOrderStatus(FoodieOrder.OrderStatus.valueOf(obj.getString("Status")));
        List<ParseObject> dishOrders = obj.getList("DishOrders");
        for (ParseObject dishOrderobj : dishOrders){
            DishOrder d = ParseObj2DishOrder(dishOrderobj);
            // Avoid duplicate objects
            assert d.getmFoodie().equals(order.getmFoodie());
            d.setmFoodie(order.getmFoodie());
            assert d.getmDishOnSale().getmDish().getmChef().equals(order.getmChef());
            d.getmDishOnSale().getmDish().setmChef(order.getmChef());

            order.addDishOrder(d);
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
    private FoodieOrder ParseObj2FoodieOrder(ParseObject obj) {
        FoodieOrder order = new FoodieOrder();
        order.setmTag(obj.getObjectId());
        order.setmFoodie(ParseFoodieTable.parseUser2Foodie(obj.getParseUser("Foodie")));
        order.setmOrderStatus(FoodieOrder.OrderStatus.valueOf(obj.getString("Status")));
        order.setmTotal(obj.getDouble("TotalPrice"));
        List<ParseObject> chefOrders = obj.getList("ChefOrders");
        for (ParseObject chefOrderObj : chefOrders) {
            ChefOrder c = ParseObj2ChefOrder(chefOrderObj);
            // Avoid duplicate objects
            order.addmChefOrder(c);
        }
        return order;
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

    @Override
    public void getFoodieOrder(String orderId, final DataLayer.GetFoodieOrderCallback cb) {
        ParseQuery query = ParseQuery.getQuery("FoodieOrder");
        query.include("ChefOrders");
        query.include("ChefOrders.Chef");
        query.include("ChefOrders.DishOrders");
        query.include("ChefOrders.DishOrders.DishOnSale");
        query.include("ChefOrders.DishOrders.DishOnSale.Dish");
        query.include("ChefOrders.DishOrders.DishOnSale.Dish.Chef");
        query.include("ChefOrders.DishOrders.Foodie");
        query.include("Foodie");
        query.whereEqualTo("objectId", orderId);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   public void done(List<ParseObject> foodieOrder, ParseException e) {
                                       if (e == null) {
                                           assert foodieOrder.size() == 1;
                                           Log.d("Retrieved Foodie Order", "Retrieved " + foodieOrder.size() + " orders");
                                           FoodieOrder order = ParseObj2FoodieOrder(foodieOrder.get(0));
                                           cb.done(order, e);
                                       } else {
                                           Log.d("score", "Error: " + e.getMessage());
                                           FoodieOrder order = new FoodieOrder();
                                           cb.done(order, e);
                                       }
                                   }
                               }
        );

    }

    @Override
    public void deliverFoodieOrder(final FoodieOrder foodieOrder, ChefOrder chefOrder, final DataLayer.SaveCallback cb) {
        final ParseObject foodieOrderObj= ParseObject.createWithoutData("FoodieOrder", foodieOrder.getmTag());
        foodieOrderObj.put("Status", FoodieOrder.OrderStatus.Delivered.toString());
        foodieOrderObj.increment("DeliveredCount");
        ParseObject chefOrderObj= ParseObject.createWithoutData("ChefOrder", chefOrder.getmTag());
        chefOrderObj.put("Status", FoodieOrder.OrderStatus.Delivered.toString());
        chefOrderObj.saveInBackground(new com.parse.SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    foodieOrderObj.saveInBackground(new com.parse.SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                                cb.done(foodieOrder.getmTag(), e);
                        }
                    });
                } else {
                    cb.done(foodieOrder.getmTag(), e);
                }
            }
        });



    }
}
