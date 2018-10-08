package com.example.lki94.what2eat;

import java.util.ArrayList;

public class RestaurantModel {
    private String name;
    private String address;
    private String cuisines;
    private int averageCost;
    private double userRating;
    private String image;

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
}
