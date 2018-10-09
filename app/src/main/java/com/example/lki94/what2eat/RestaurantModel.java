package com.example.lki94.what2eat;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantModel implements Parcelable {

    private String name;
    private String address;
    private String cuisines;
    private int averageCost;
    private double userRating;
    private String image;

    public RestaurantModel() {
    }

    private RestaurantModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        cuisines = in.readString();
        averageCost = in.readInt();
        userRating = in.readDouble();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.cuisines);
        dest.writeInt(this.averageCost);
        dest.writeDouble(this.userRating);
        dest.writeString(this.image);
    }

    public static final Parcelable.Creator<RestaurantModel> CREATOR = new Parcelable.Creator<RestaurantModel>() {
        public RestaurantModel createFromParcel(Parcel source) {
            return new RestaurantModel(source);
        }

        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    public RestaurantModel(String name, String address, String cuisines, int averageCost, double userRating, String image){
        this.name = name;
        this.address = address;
        this.cuisines = cuisines;
        this.averageCost = averageCost;
        this.userRating = userRating;
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public void setName(String rname) {
        this.name = rname;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String raddress) {
        this.address = raddress;
    }

    public String getCuisines() {
        return cuisines;
    }
    public void setCuisines(String rcuisines) {
        this.cuisines = rcuisines;
        //this.cuisinesList = Arrays.asList(rcuisines.split("\\s*,\\s*"));
    }

    public int getAverageCost() {
        return averageCost;
    }
    public void setAverageCost(int raverageCost) {
        this.averageCost = raverageCost/2;
    }

    public double getUserRating() {
        return userRating;
    }
    public void setUserRating(double ruserRating) {
        this.userRating = ruserRating;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "RestaurantModel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", cuisines='" + cuisines + '\'' +
                '}';
    }
}
