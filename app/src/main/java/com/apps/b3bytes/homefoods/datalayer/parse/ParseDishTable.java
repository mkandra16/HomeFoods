package com.apps.b3bytes.homefoods.datalayer.parse;

import com.apps.b3bytes.homefoods.datalayer.common.DishTable;
import com.apps.b3bytes.homefoods.models.Dish;
import com.parse.ParseObject;

/**
 * Created by sindhu on 7/26/2015.
 */
public class ParseDishTable implements DishTable {
    @Override
    public void addDishInBackground(Dish dish) {
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
        if (dish.getmCusineId() != null) {
            dishObj.put("CuisineId", dish.getmCusineId());
        }

        dishObj.saveInBackground();
    }
}
