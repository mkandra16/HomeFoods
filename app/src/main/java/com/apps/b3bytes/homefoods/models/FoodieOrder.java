package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Pavan on 8/27/2015.
 */

public class FoodieOrder implements Parcelable {
    public enum OrderStatus {Cancelled, Ordered, Delivered, Unknown}

    ;

    private String mTag;
    private Set<ChefOrder> mChefOrders;
    private Foodie mFoodie;
    private double mTotal;
    private OrderStatus mOrderStatus;
    private Date mOrderedDate;

    public FoodieOrder() {
        mChefOrders = new HashSet<ChefOrder>();
        setmOrderStatus(OrderStatus.Unknown);
    }

    public ChefOrder getChefOrder(Foodie f) {
        for (ChefOrder order : mChefOrders) {
            if (order.getmChef().equals(f)) {
                return order;
            }
        }
        return null;
    }

    public List<DishOrder> getDishOrders() {
        List<DishOrder> orders = new ArrayList<DishOrder>();
        for (ChefOrder co : mChefOrders) {
            orders.addAll(co.getmDishOrders());
        }
        return orders;
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public Set<ChefOrder> getmChefOrders() {
        return mChefOrders;
    }

    public void setmChefOrders(Set<ChefOrder> mChefOrders) {
        this.mChefOrders = mChefOrders;
    }

    public void addmChefOrder(ChefOrder chefOrder) {
        this.mChefOrders.add(chefOrder);
    }

    public Foodie getmFoodie() {
        return mFoodie;
    }

    public void setmFoodie(Foodie mFoodie) {
        this.mFoodie = mFoodie;
    }

    public double getmTotal() {
        return mTotal;
    }

    public void setmTotal(double mTotal) {
        this.mTotal = mTotal;
    }

    public OrderStatus getmOrderStatus() {
        return mOrderStatus;
    }

    public void setmOrderStatus(OrderStatus mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
    }

    public String getmOrderedDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String dateString = df.format(mOrderedDate);
        return dateString;
    }

    public void setmOrderedDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date date = formatter.parse(dateString);
            this.mOrderedDate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setmOrderedDate(Date mOrderedDate) {
            this.mOrderedDate = mOrderedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ArrayList<ChefOrder> aLChefOrder = new ArrayList<ChefOrder>();
        aLChefOrder.addAll(getmChefOrders());

        dest.writeString(getmTag());
        dest.writeList(aLChefOrder);
        dest.writeParcelable(getmFoodie(), flags);
        dest.writeDouble(getmTotal());
        dest.writeString(getmOrderStatus().toString());
        dest.writeString(getmOrderedDate());
    }


    public static final Parcelable.Creator<FoodieOrder> CREATOR = new Parcelable.Creator<FoodieOrder>() {
        public FoodieOrder createFromParcel(Parcel in) {
            FoodieOrder foodieOrder = new FoodieOrder(in);
            return foodieOrder;
        }

        public FoodieOrder[] newArray(int size) {
            return new FoodieOrder[size];
        }
    };

    public FoodieOrder(Parcel in) {
        setmTag(in.readString());

        ArrayList<ChefOrder> aLChefOrder = new ArrayList<ChefOrder>();
        in.readList(aLChefOrder, ChefOrder.class.getClassLoader());
        mChefOrders.addAll(aLChefOrder);

        setmFoodie((Foodie) in.readParcelable(Foodie.class.getClassLoader()));
        setmTotal(in.readDouble());
        setmOrderStatus(OrderStatus.valueOf(in.readString()));
        setmOrderedDate(in.readString());
    }
}
