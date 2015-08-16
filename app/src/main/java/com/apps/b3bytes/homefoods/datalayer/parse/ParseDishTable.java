package com.apps.b3bytes.homefoods.datalayer.parse;

import android.util.Log;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.datalayer.common.DishTable;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.Foodie;
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
    public static  Dish ParseObject2Dish(ParseObject object) {
        ParseUser chef = object.getParseUser("Chef");
        Dish dish = new Dish();

        dish.setmDishId(object.getInt("DishId"));
        dish.setmDishName(object.getString("DishName"));
        dish.setmDishInfo(object.getString("DishInfo"));
        dish.setmImageURL(object.getString("ImageURL"));
        dish.setmQty(object.getInt("Qty"));
        dish.setmUnit(object.getString("Unit"));
        dish.setmPrice(object.getDouble("Price"));
        dish.setmThumbsUp(object.getInt("ThumbsUp"));
        dish.setmThumbsDown(object.getInt("ThumbsDown"));
        dish.setmCusineId(object.getInt("CusineId"));
        dish.setmChefId(object.getInt("ChefId"));
        dish.setmTag(object.getObjectId());
        Foodie f = new Foodie(ParseFoodieTable.parseUser2JSONObject(chef));
        f.setmTag(chef.getObjectId());
        dish.setmChef(f);
        return dish;
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
        query.include("Chef");
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
