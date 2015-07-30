package com.apps.b3bytes.homefoods.models;

import com.apps.b3bytes.homefoods.utils.Address;
import com.apps.b3bytes.homefoods.utils.ContactDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sindhu on 7/26/2015.
 */
public class Foodie {

    private String mFoodieId;
    private String mFirstName;
    private String mMiddleName;
    private String mLastName;
    private String mImageURL;
    private Address mAddr;
    private ContactDetails mContact;
    private String mFavFoods;
    private ArrayList<String> mPayOptions;
    private Chef mChef; // when set can be same as FoodieId

    public static Foodie createDummyFoodie() {
        JSONObject addr =  new JSONObject();
        try {
            addr.put("AddrLine1", "1234 Kiely Blvd")
                    .put("AddrZip", 95129)
                    .put("AddrState", "CA")
                    .put("AddrCountry", "US");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject contact = new JSONObject();
        try {
            contact.put("HomePh", "1234567890")
                    .put("Mobile", "1234567890")
                    .put("EmailId", "mohan.kandra@somemail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject foodie = new JSONObject();
        try {
            foodie.put("FoodieId", 1)
                    .put("FirstName", "Mohan")
                    .put("LastName", "Kandra")
                    .put("Address", addr)
                    .put("ContactDetails", contact)
                    .put("FavoriteFoods", "Dosa, Sambar");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Foodie f = new Foodie(foodie);

        return f;
    }

    public Foodie(JSONObject f) {
        try {
            mFoodieId = f.getString("FoodieId");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Crash!");
        }

        try {
            mMiddleName = f.getString("MiddleName");
        } catch (JSONException e) {
            mMiddleName = new String();
        }
        try {
            mImageURL = f.getString("ImageURL");
        } catch (JSONException e) {
            mImageURL = new String();
        }
        try {
            mFavFoods = f.getString("FavoriteFoods");
        } catch (JSONException e) {
            mFavFoods = new String();
        }
        try {
            mFirstName = f.getString("FirstName");
            mLastName = f.getString("LastName");
            mAddr = new Address(f.getJSONObject("Address"));
            mContact = new ContactDetails(f.getJSONObject("ContactDetails"));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Crash!");
        }

    }
    public String getmFoodieId() {
        return mFoodieId;
    }

    public void setmFoodieId(String mFoodieId) {
        this.mFoodieId = mFoodieId;
    }

    public ContactDetails getmContact() {
        return mContact;
    }

    public void setmContact(ContactDetails mContact) {
        this.mContact = mContact;
    }

    public Chef getmChef() {
        return mChef;
    }

    public void setmChef(Chef mChef) {
        this.mChef = mChef;
    }

    public String getnfId() {
        return mFoodieId;
    }

    public void setmfId(String mfId) {
        this.mFoodieId = mfId;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmMiddleName() {
        return mMiddleName;
    }

    public void setmMiddleName(String mMiddleName) {
        this.mMiddleName = mMiddleName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public Address getmAddr() {
        return mAddr;
    }

    public void setmAddr(Address mAddr) {
        this.mAddr = mAddr;
    }

    public String getmFavFoods() {
        return mFavFoods;
    }

    public void setmFavFoods(String mFavFoods) {
        this.mFavFoods = mFavFoods;
    }

    public ArrayList<String> getmPayOptions() {
        return mPayOptions;
    }

    public void setmPayOptions(ArrayList<String> mPayOptions) {
        this.mPayOptions = mPayOptions;
    }

    public String getmChefId() {
        return mChef.getmChefId();
    }

    public void setmChefId(String mChefId) {
        this.mChef.setmChefId(mChefId);
    }
}
