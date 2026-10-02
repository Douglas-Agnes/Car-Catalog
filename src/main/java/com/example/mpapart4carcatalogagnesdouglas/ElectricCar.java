// **********************************************************************************
// Title: Car Catalog
// Author: Douglas Agnes
// Course Section: CMIS201-ONL1 (Seidel) Spring 2024
// File: ElectricCar.java
// Description: Creates an object for an electric car
// **********************************************************************************
package com.example.mpapart4carcatalogagnesdouglas;

import javafx.scene.image.Image;

public class ElectricCar extends Car{
    private int range;
    private int numOfMotors;
    private boolean lvl3ChargingCapable;
    // Constructors:

    public ElectricCar() {
        super();
        this.range = 0;
        this.numOfMotors = 0;
        this.lvl3ChargingCapable = false;
    }

    public ElectricCar(String make, String model, int year, int doors, String drivetrain, int price, int mileage, Image picture, int range, int numOfMotors, boolean lvl3ChargingCapable) {
        super(make, model, year, doors, drivetrain, price, mileage, picture);
        this.range = range;
        this.numOfMotors = numOfMotors;
        this.lvl3ChargingCapable = lvl3ChargingCapable;
    }

    // Mutators:

    public void setRange(int rg) {
        this.range = rg;
    }

    public void setNumOfMotors(int nm) {
        this.numOfMotors = nm;
    }

    public void setLvl3ChargingCapability(boolean cc) {
        this.lvl3ChargingCapable = cc;
    }

    // Accessors:

    public int getRange() {
        return range;
    }

    public int getNumOfMotors() {
        return numOfMotors;
    }

    public boolean isLvl3ChargingCapable() {
        return lvl3ChargingCapable;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nRange = " + range + " miles"+
                "\nNumber of Electric Motors = " + numOfMotors +
                "\nLevel 3 Charging Capable = " + lvl3ChargingCapable;
    }

    @Override
    public String fields() {
        return super.fields() + '\t' + range + '\t' + numOfMotors + '\t' + lvl3ChargingCapable;
    }
}
