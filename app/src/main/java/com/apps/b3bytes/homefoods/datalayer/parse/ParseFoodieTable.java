package com.apps.b3bytes.homefoods.datalayer.parse;

import android.util.Log;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.datalayer.common.FoodieTable;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sindhu on 7/28/2015.
 */
public class ParseFoodieTable implements FoodieTable {
    public static JSONObject parseUser2JSONObject(ParseUser user) {
        JSONObject addr = new JSONObject();
        try {
            addr.put("AddrLine1", user.getString("AddrLine1"))
                    .put("AddrZip", user.getInt("Zip"))
                    .put("AddrState", user.getString("State"))
                    .put("AddrCountry", user.getString("Country"));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Crash!");


        }
        if (user.getString("AddrLine2") != null) {
            try {
                addr.put("AddrLine2", user.getString("AddrLine2"));
            } catch (JSONException e) {
                throw new RuntimeException("AddrLine2 : Crash!");
            }
        }

        JSONObject contact = new JSONObject();
        try {
            contact.put("HomePh", user.getString("HomePh"))
                    .put("OfficePh", user.getString("OfficePh"))
                    .put("Mobile", user.getString("Mobile"))
                    .put("EmailId", user.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject foodie = new JSONObject();
        try {
            foodie.put("FoodieId", user.getInt("FoodieId"))
                    .put("FirstName", user.getString("FirstName"))
                    .put("LastName", user.getString("LastName"))
                    .put("UserName", user.getEmail())
                    .put("Address", addr)
                    .put("ContactDetails", contact)
                    .put("FavoriteFoods", user.getString("FavoriteFoods"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodie;
    }

    @Override
    public  void signInFoodie(String userName, String password, final DataLayer.SignInCallback callback) {
        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, com.parse.ParseException e) {
                if (e == null) {

                    Foodie f = new Foodie(parseUser2JSONObject(parseUser));
                    f.setmTag(parseUser.getObjectId());
                    callback.done(f, null);
                } else {
                    Log.e("Error", "Failed to login");
                    callback.done(null,e);
                }
            }
        });
    }

    @Override
    public void registerFoodieInBackground(final Foodie foodie, final DataLayer.RegistrationCallback callback) {
        final ParseUser user = new ParseUser();
        user.setUsername(foodie.getmUserName());
        user.setPassword(foodie.getmPassword());
        user.setEmail(foodie.getmContact().getmEmailId());
        user.increment("FoodieId");
        user.put("FirstName", foodie.getmFirstName());
        if (! foodie.getmMiddleName().isEmpty()) {
            user.put("MiddleName", foodie.getmMiddleName());
        }
        user.put("LastName", foodie.getmLastName());
        if (! foodie.getmImageURL().isEmpty()) {
            user.put("ImageURL", foodie.getmImageURL());
        }

        user.put("AddrLine1", foodie.getmAddr().getmLine1());
        if (! foodie.getmAddr().getmCity().isEmpty()) {
            user.put("AddrLine2", foodie.getmAddr().getmCity());
        }
        user.put("Zip", foodie.getmAddr().getmZip());
        user.put("State", foodie.getmAddr().getmState());
        user.put("Country", foodie.getmAddr().getmCountry());
        if (! foodie.getmContact().getmHomePh().isEmpty()) {
            user.put("HomePh", foodie.getmContact().getmHomePh());
        }
        if (! foodie.getmContact().getmOfficePh().isEmpty()) {
            user.put("OfficePh", foodie.getmContact().getmOfficePh());
        }
        user.put("Mobile", foodie.getmContact().getmMoblie());

        if (! foodie.getmFavFoods().isEmpty()) {
            user.put("FavoriteFoods", foodie.getmFavFoods());
        }
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.i("Info", "SignUP succesful");
                    foodie.setmFoodieId(user.getInt("FoodieId"));
                    foodie.setmTag(user.getObjectId());

                    callback.done(foodie, null);
                } else {
                    Log.e("Error", "SignUP failed");
                    callback.done(foodie, e);
                }
            }
        });
    }
}
