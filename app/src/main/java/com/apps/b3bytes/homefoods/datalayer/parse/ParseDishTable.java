package com.apps.b3bytes.homefoods.datalayer.parse;

import android.util.Log;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.datalayer.common.DishTable;
import com.apps.b3bytes.homefoods.models.Dish;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindhu on 7/26/2015.
 */
public class ParseDishTable implements DishTable {
    private Dish ParseObject2Dish(ParseObject object) {
        JSONObject dish = new JSONObject();
        try {
            dish.put("DishId", object.getInt("DishId"))
                    .put("DishName", object.getString("DishName"))
                    .put("DishInfo", object.getString("DishInfo"))
                    .put("ImageURL", object.getString("ImageURL"))
                    .put("Qty", object.getInt("Qty"))
                    .put("Unit", object.getString("Unit"))
                    .put("Price", object.getDouble("Price"))
                    .put("ThumbsUp", object.getInt("ThumbsUp"))
                    .put("ThumbsDown", object.getInt("ThumbsDown"))
                    .put("CusineId", object.getInt("CusineId"))
                    .put("ChefId", object.getInt("ChefId"));
            ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Dish(dish);
    }
    private ArrayList<Dish> ParseList2DishList(List<ParseObject> list) {
        ArrayList<Dish> al = new ArrayList<Dish>();
        for(ParseObject o : list) {
            Dish d = ParseObject2Dish(o);
            al.add(d);
        }
        return al;
    }
    @Override
    public void addDishInBackground(final Dish dish, final DataLayer.DishPublishCallback c) {
        ParseObject dishObj = new ParseObject("Dish");
        dishObj.increment("DishId");
        dishObj.put("DishName", dish.getmDishName());

        dishObj.put("DishInfo", dish.getmDishInfo());
        if (dish.getmImageURL() != null) {
            dishObj.put("ImageURL", dish.getmImageURL());
        }

        dishObj.put("Qty", dish.getmQty());
        dishObj.put("Unit", dish.getmUnit().name());
        dishObj.put("Price", dish.getmPrice());
        dishObj.put("ThumbsUp", dish.getmThumbsUp());
        dishObj.put("ThumbsDown", dish.getmThumbsDown());
        if (dish.getmCusineId() != 0) {
            dishObj.put("CuisineId", dish.getmCusineId());
        }
        dishObj.put("Chef", ParseUser.getCurrentUser());
        dishObj.put("ChefId", ParseUser.getCurrentUser().getInt("FoodieId"));
        ParseGeoPoint loc = new ParseGeoPoint(40, 50);
        dishObj.put("DeliveryLoc", loc);
        dishObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                c.done(dish, e);
            }
        });
    }

    @Override
    public void getNearbyDishes(int radius, final DataLayer.DishQueryCallback callback) {
        ParseGeoPoint loc = new ParseGeoPoint(40,50);
        ParseQuery query = ParseQuery.getQuery("Dish");
        query.whereWithinMiles("DeliveryLoc", loc, radius);
        query.setLimit(20);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   public void done(List<ParseObject> dishList, ParseException e) {
                                       if (e == null) {
                                           Log.d("score", "Retrieved " + dishList.size() + " scores");

                                           callback.done(ParseList2DishList(dishList), e);
                                       } else {
                                           Log.d("score", "Error: " + e.getMessage());
                                       }
                                   }
                               }
        );
    }

}
