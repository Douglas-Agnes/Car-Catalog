// **********************************************************************************
// Title: Car Catalog
// Author: Douglas Agnes
// Course Section: CMIS201-ONL1 (Seidel) Spring 2024
// File: Car.java
// Description: Lays the groundwork for creating car objects
// **********************************************************************************
package com.example.mpapart4carcatalogagnesdouglas;

import javafx.scene.image.Image;

public abstract class Car {
    private String make;
    private String model;
    private int year;
    private int doors;
    private String drivetrain;
    private int price;
    private int mileage;
    private Image picture;

    // Constructors:

    public Car() {
        this.make = "";
        this.model = "";
        this.year = 0;
        this.doors = 0;
        this.drivetrain = "";
        this.price = 0;
        this.mileage = 0;
    }
    public Car(String make, String model, int year, int doors, String drivetrain, int price, int mileage, Image picture) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.doors = doors;
        this.drivetrain = drivetrain;
        this.price = price;
        this.mileage = mileage;
        this.picture = picture;
    }

    // Mutators:

    public void setMake(String mk) {
        this.make = mk;
    }

    public void setModel(String md) {
        this.model = md;
    }

    public void setYear(int yr) {
        this.year = yr;
    }

    public void setDoors(int dr) {
        this.doors = dr;
    }

    public void setDrivetrain(String dt) {
        this.drivetrain = dt;
    }

    public void setPrice(int pr) {
        this.price = pr;
    }

    public void setMileage(int mi) {
        this.mileage = mi;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    // Accessors:

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getDoors() {
        return doors;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    public int getPrice() {
        return price;
    }

    public int getMileage() {
        return mileage;
    }

    public Image getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "\nMake = '" + make + '\'' +
                "\nModel = '" + model + '\'' +
                "\nYear = " + year +
                "\nFuel Type = " + this.getClass().getSimpleName().substring(0, this.getClass().getSimpleName().indexOf("Car")) +
                "\nDoors = " + doors +
                "\nDrivetrain = '" + drivetrain + '\'' +
                "\nPrice = $" + price +
                "\nMileage = " + mileage + " miles";
    }

    public String fields() {
        return make + '\t' + model + '\t' + year + '\t' + doors + '\t' + drivetrain + '\t' + price + '\t' + mileage + '\t' + picture.getUrl();
    }
}
