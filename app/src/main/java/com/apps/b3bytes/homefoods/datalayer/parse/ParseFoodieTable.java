package com.apps.b3bytes.homefoods.datalayer.parse;

import com.apps.b3bytes.homefoods.datalayer.common.FoodieTable;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.parse.ParseObject;

/**
 * Created by sindhu on 7/28/2015.
 */
public class ParseFoodieTable implements FoodieTable {
    @Override
    public void registerFoodieInBackground(Foodie foodie) {
        ParseObject foodieObj = new ParseObject("Foodie");
        foodieObj.put("FirstName", foodie.getmFirstName());
        if (! foodie.getmMiddleName().isEmpty()) {
            foodieObj.put("MiddleName", foodie.getmMiddleName());
        }
        foodieObj.put("LastName", foodie.getmLastName());
        if (! foodie.getmImageURL().isEmpty()) {
            foodieObj.put("ImageURL", foodie.getmImageURL());
        }

        foodieObj.put("AddrLine1", foodie.getmAddr().getmLine1());
        if (! foodie.getmAddr().getmLine2().isEmpty()) {
            foodieObj.put("AddrLine2", foodie.getmAddr().getmLine2());
        }
        foodieObj.put("Zip", foodie.getmAddr().getmZip());
        foodieObj.put("State", foodie.getmAddr().getmState());
        foodieObj.put("Country", foodie.getmAddr().getmCountry());
        if (! foodie.getmContact().getmHomePh().isEmpty()) {
            foodieObj.put("HomePh", foodie.getmContact().getmHomePh());
        }
        if (! foodie.getmContact().getmOfficePh().isEmpty()) {
            foodieObj.put("OfficePh", foodie.getmContact().getmOfficePh());
        }
        foodieObj.put("Mobile", foodie.getmContact().getmMoblie());
        foodieObj.put("EmailId", foodie.getmContact().getmEmailId());

        if (! foodie.getmFavFoods().isEmpty()) {
            foodieObj.put("FavoriteFoods", foodie.getmFavFoods());
        }
        foodieObj.saveInBackground();

    }
}
