package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DishReview implements Parcelable{
    private float mRating;
    private String mDate;
    private String mFoodieImageUrl;
    private String mFoodieName;
    private String mOneLine;
    private String mMultiLine;

    public DishReview() {
    }

    public float getmRating() {
        return mRating;
    }

    public void setmRating(float mRating) {
        this.mRating = mRating;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmFoodieImageUrl() {
        return mFoodieImageUrl;
    }

    public void setmFoodieImageUrl(String mFoodieImageUrl) {
        this.mFoodieImageUrl = mFoodieImageUrl;
    }

    public String getmFoodieName() {
        return mFoodieName;
    }

    public void setmFoodieName(String mFoodieName) {
        this.mFoodieName = mFoodieName;
    }

    public String getmOneLine() {
        return mOneLine;
    }

    public void setmOneLine(String mOneLine) {
        this.mOneLine = mOneLine;
    }

    public String getmMultiLine() {
        return mMultiLine;
    }

    public void setmMultiLine(String mMultiLine) {
        this.mMultiLine = mMultiLine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(getmRating());
        dest.writeString(getmDate());
        dest.writeString(getmFoodieImageUrl());
        dest.writeString(getmFoodieName());
        dest.writeString(getmOneLine());
        dest.writeString(getmMultiLine());
    }


    public static final Creator<DishReview> CREATOR = new Creator<DishReview>() {
        public DishReview createFromParcel(Parcel in) {
            DishReview review = new DishReview(in);
            return review;
        }

        public DishReview[] newArray(int size) {
            return new DishReview[size];
        }
    };

    public DishReview(Parcel in) {
        setmRating(in.readFloat());
        setmDate(in.readString());
        setmFoodieImageUrl(in.readString());
        setmFoodieName(in.readString());
        setmOneLine(in.readString());
        setmMultiLine(in.readString());
    }


}
