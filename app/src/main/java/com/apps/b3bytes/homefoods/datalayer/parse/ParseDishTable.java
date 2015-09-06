package com.apps.b3bytes.homefoods.datalayer.parse;

import android.util.Log;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.datalayer.common.DishTable;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
//        dish.setmQty(object.getInt("Qty"));
     //   dish.setmMeasure(object.getString("Measure"));
//        dish.setmPrice(object.getDouble("Price"));
        dish.setmThumbsUp(object.getInt("ThumbsUp"));
        dish.setmThumbsDown(object.getInt("ThumbsDown"));
        dish.setmCusineId(object.getInt("CusineId"));
        dish.setmChefId(object.getInt("ChefId"));
        dish.setmTag(object.getObjectId());
        dish.setmImageURL(object.getString("ImageURL"));
        Foodie f = new Foodie(ParseFoodieTable.parseUser2JSONObject(chef));
        f.setmTag(chef.getObjectId());
        dish.setmChef(f);
        return dish;
    }
    public static DishOnSale ParseObject2DishOnSale(ParseObject object) {
        DishOnSale dos = new DishOnSale();
        dos.setmTag(object.getObjectId());
        dos.setmMeasure(object.getString("Measure"));
        dos.setmToTime(object.getString("ToTime"));
        dos.setmQtyPerUnit(object.getDouble("QtyPerUnit"));
        dos.setmUnitPrice(object.getDouble("UnitPrice"));
        dos.setmPickUp(object.getBoolean("PickUp"));
        dos.setmUnitsOnSale(object.getInt("QtyOnSale"));
        dos.setmUnitsOrdered(object.getInt("UnitsOrdered"));
        dos.setmUnitsDelivered(object.getInt("UnitsDelivered"));
        dos.setmDelivery(object.getBoolean("Delivery"));
        dos.setmPickUp(object.getBoolean("PickUp"));
        dos.setmDish(ParseObject2Dish(object.getParseObject("Dish")));
        return dos;
    }
    public static ArrayList<DishOnSale> ParseList2DishOnSaleList(List<ParseObject> list) {
        ArrayList<DishOnSale> al = new ArrayList<DishOnSale>();
        for(ParseObject o : list) {
            DishOnSale d = ParseObject2DishOnSale(o);
            al.add(d);
        }
        return al;
    }

    @Override
    public void addDishInBackground(final Dish dish, final DataLayer.PublishCallback c) {
        final ParseObject dishObj = new ParseObject("Dish");
        dishObj.increment("DishId");
        dishObj.put("DishName", dish.getmDishName());

        dishObj.put("DishInfo", dish.getmDishInfo());
        if (dish.getmImageURL() != null) {
            dishObj.put("ImageURL", dish.getmImageURL());
        }

//        dishObj.put("Qty", dish.getmQty());
     //   dishObj.put("Measure", dish.getmMeasure().name());
//        dishObj.put("Price", dish.getmPrice());
        dishObj.put("ThumbsUp", dish.getmThumbsUp());
        dishObj.put("ThumbsDown", dish.getmThumbsDown());
//        if (dish.getmCusineId() != 0) {
//            dishObj.put("CuisineId", dish.getmCusineId());
//        }
        // Infuture we want to maintain sepaate table for cusines, for now stora it as tag.
        dishObj.put("Cusine", dish.getmCusine());
        dishObj.put("Vegitarian", dish.ismVegitarian());
        dishObj.put("Vegan", dish.ismVegan());
        dishObj.put("GlutenFree", dish.ismGlutenFree());
        dishObj.put("RedMeat", dish.ismRedMeat());

        dishObj.put("Chef", ParseUser.getCurrentUser());
        dishObj.put("ChefId", ParseUser.getCurrentUser().getInt("FoodieId"));
        ParseGeoPoint loc = new ParseGeoPoint(40, 50);
        dishObj.put("DeliveryLoc", loc);
        dishObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    dish.setmTag(dishObj.getObjectId());
                } else {
                    Log.e("Publish Dish", "Failed to save Dish" + e.toString());
                }
                c.done(e);
            }
        });
    }

    @Override
    public void getNearbyDishes(int radius, final DataLayer.DishQueryCallback callback) {
        ParseGeoPoint loc = new ParseGeoPoint(40,50);
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Dish");
        innerQuery.whereWithinMiles("DeliveryLoc", loc, radius);
        ParseQuery query = ParseQuery.getQuery("DishOnSale");
        query.whereMatchesQuery("Dish", innerQuery);
        query.include("Dish");
        query.include("Dish.Chef");
        query.setLimit(20);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   public void done(List<ParseObject> dishList, ParseException e) {
                                       if (e == null) {
                                           Log.d("score", "Retrieved " + dishList.size() + " scores");

                                           callback.done(ParseList2DishOnSaleList(dishList), e);
                                       } else {
                                           Log.d("score", "Error: " + e.getMessage());
                                       }
                                   }
                               }
        );
    }

    @Override
    public void putDishOnSale(final DishOnSale dishOnSale, final DataLayer.PublishCallback cb) {
        final ParseObject dishOnSaleObj = new ParseObject("DishOnSale");
        assert ! dishOnSale.getmDish().getmTag().isEmpty();
        dishOnSaleObj.put("Dish", ParseObject.createWithoutData("Dish",dishOnSale.getmDish().getmTag()));
        dishOnSaleObj.put("Measure", dishOnSale.getmMeasure().toString());
        dishOnSaleObj.put("QtyPerUnit", dishOnSale.getmQtyPerUnit());
        dishOnSaleObj.put("UnitPrice", dishOnSale.getmUnitPrice());
        dishOnSaleObj.put("QtyOnSale", dishOnSale.getmUnitsOnSale());
        dishOnSaleObj.put("PickUp", dishOnSale.ismPickUp());
        dishOnSaleObj.put("Delivery", dishOnSale.ismDelivery());
        dishOnSaleObj.put("ToDate", dishOnSale.getmToDate());
        dishOnSaleObj.put("ToTime", dishOnSale.getmToTime());


        dishOnSaleObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    dishOnSale.setmTag(dishOnSaleObj.getObjectId());
                    cb.done(e);
                } else {
                    Log.d("ParseDishOnSale", "Error: " + e.getMessage());
                    cb.done(e);
                }
            }
        });
    }

}
