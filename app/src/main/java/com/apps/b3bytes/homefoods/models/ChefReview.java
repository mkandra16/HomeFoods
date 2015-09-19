package com.apps.b3bytes.homefoods.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChefReview implements Parcelable {
    private float mRating;
    private String mDate;
    private String mFoodieImageUrl;
    private String mFoodieName;
    private String mOneLine;
    private String mMultiLine;
    private boolean mUseful;

    public ChefReview() {
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

    public boolean ismUseful() {
        return mUseful;
    }

    public void setmUseful(boolean mUseful) {
        this.mUseful = mUseful;
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
        dest.writeInt(ismUseful() ? 1 : 0);
    }


    public static final Creator<ChefReview> CREATOR = new Creator<ChefReview>() {
        public ChefReview createFromParcel(Parcel in) {
            ChefReview review = new ChefReview(in);
            return review;
        }

        public ChefReview[] newArray(int size) {
            return new ChefReview[size];
        }
    };

    public ChefReview(Parcel in) {
        setmRating(in.readFloat());
        setmDate(in.readString());
        setmFoodieImageUrl(in.readString());
        setmFoodieName(in.readString());
        setmOneLine(in.readString());
        setmMultiLine(in.readString());
        setmUseful((in.readInt() == 1) ? true : false);
    }


}
