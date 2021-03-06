package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.apps.b3bytes.homefoods.utils.Address;
import com.apps.b3bytes.homefoods.utils.ContactDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sindhu on 7/26/2015.
 */
public class Foodie implements Parcelable {

    private int mFoodieId;
    private String mFirstName;
    private String mMiddleName;
    private String mLastName;
    private String mUserName;
    private String mPassword;
    private String mImageURL;
    private Address mAddr;
    private ContactDetails mContact;
    private String mFavFoods;
    //private ArrayList<String> mPayOptions;
    private String mTag;
    private Chef mChef; // when set can be same as FoodieId

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public Foodie() {
        mAddr = new Address();
        mContact = new ContactDetails();
        mChef = new Chef();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Foodie)) {
            return false;
        }

        Foodie that = (Foodie) other;

        // Custom equality check here.
        return getmUserName().equals(that.getmUserName());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;

        hashCode = hashCode * 37 + getmUserName().hashCode();

        return hashCode;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public static Foodie createDummyFoodie() {
        JSONObject addr =  new JSONObject();

        try {
            addr.put("AddrLine1", "34113 Via Lucca")
                    .put("AddrZip", 94555)
                    .put("AddrState", "CA")
                    .put("AddrCountry", "US");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject contact = new JSONObject();
        try {
            contact.put("HomePh", "1234567890")
                    .put("Mobile", "1234567890")
                    .put("EmailId", "patibandla@gmail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject foodie = new JSONObject();
        try {
            foodie.put("FoodieId", -1)
                    .put("FirstName", "Pavan")
                    .put("LastName", "Patibandla")
                    .put("UserName", "Pavan")
                    .put("Password", "welcome")
                    .put("Address", addr)
                    .put("ContactDetails", contact)
                    .put("FavoriteFoods", "Kakarakai");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Foodie f = new Foodie(foodie);

        return f;
    }

    public Foodie(JSONObject f) {
        try {
            mFoodieId = f.getInt("FoodieId");
            mUserName = f.getString("UserName");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Crash!");
        }
        try {
            mPassword = f.getString("Password");
        } catch (JSONException e) {
            e.printStackTrace();
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
    public void signUpAsChef() {
        mChef = new Chef();
        mChef.setmChefId(mFoodieId);
        mChef.setmAddr(mAddr);
    }
    public int getmFoodieId() {
        return mFoodieId;
    }

    public void setmFoodieId(int mFoodieId) {
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

/*    public ArrayList<String> getmPayOptions() {
        return mPayOptions;
    }

    public void setmPayOptions(ArrayList<String> mPayOptions) {
        this.mPayOptions = mPayOptions;
    }*/

 /*   public void setmChefId(String mChefId) {
        this.mChef.setmChefId(mChefId);
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getmFoodieId());
        dest.writeString(getmFirstName());
        dest.writeString(getmMiddleName());
        dest.writeString(getmLastName());
        dest.writeString(getmUserName());
        dest.writeString(getmPassword());
        dest.writeString(getmImageURL());
        dest.writeParcelable(getmAddr(), flags);
        dest.writeParcelable(getmContact(), flags);
        dest.writeString(getmFavFoods());
        //dest.writeStringList(getmPayOptions()); // TODO: Check this? will it work for ArrayList?
        dest.writeString(getmTag());
        dest.writeParcelable(mChef, flags);
    }

    public static final Parcelable.Creator<Foodie> CREATOR = new Parcelable.Creator<Foodie>() {
        public Foodie createFromParcel(Parcel in) {
            Foodie foodie = new Foodie(in);
            return foodie;
        }

        public Foodie[] newArray(int size) {
            return new Foodie[size];
        }
    };

    public Foodie(Parcel in) {
        setmFoodieId(in.readInt());
        setmFirstName(in.readString());
        setmMiddleName(in.readString());
        setmLastName(in.readString());
        setmUserName(in.readString());
        setmPassword(in.readString());
        setmImageURL(in.readString());
        setmAddr((Address) in.readParcelable(Address.class.getClassLoader()));
        setmContact((ContactDetails) in.readParcelable(ContactDetails.class.getClassLoader()));
        setmFavFoods(in.readString());
        setmTag(in.readString());
        setmChef((Chef) in.readParcelable(Chef.class.getClassLoader()));
    }

}
