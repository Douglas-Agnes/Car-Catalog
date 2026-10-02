// **********************************************************************************
// Title: Car Catalog
// Author: Douglas Agnes
// Course Section: CMIS201-ONL1 (Seidel) Spring 2024
// File: GasCar.java
// Description: Creates an object for a gas car and lays the groundwork for specific types of gas cars
// **********************************************************************************
package com.example.mpapart4carcatalogagnesdouglas;

import javafx.scene.image.Image;

public class GasCar extends Car{
    private int mpg;
    private int cylinders;
    private String transmission;
    private String aspiration;

    // Constructors:

    public GasCar() {
        super();
        this.mpg = 0;
        this.cylinders = 0;
        this.transmission = "";
        this.aspiration = "";
    }

    public GasCar(String make, String model, int year, int doors, String drivetrain, int price, int mileage, Image picture, int mpg,
                  int cylinders, String transmission, String aspiration) {
        super(make, model, year, doors, drivetrain, price, mileage, picture);
        this.mpg = mpg;
        this.cylinders = cylinders;
        this.transmission = transmission;
        this.aspiration = aspiration;
    }

    // Mutators:

    public void setMpg(int mpg) {
        this.mpg = mpg;
    }

    public void setCylinders(int cy) {
        this.cylinders = cy;
    }

    public void setTransmission(String tm) {
        this.transmission = tm;
    }

    public void setAspiration(String as) {
        this.aspiration = as;
    }

    // Accessors:

    public int getMpg() {
        return mpg;
    }

    public int getCylinders() {
        return cylinders;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getAspiration() {
        return aspiration;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nMiles Per Gallon = " + mpg +
                "\nCylinders = " + cylinders +
                "\nTransmission = '" + transmission + '\'' +
                "\nAspiration = '" + aspiration + '\'';
    }

    @Override
    public String fields() {
        return super.fields() + '\t' + mpg + '\t' + cylinders + '\t' + transmission + '\t' + aspiration;
    }
}
