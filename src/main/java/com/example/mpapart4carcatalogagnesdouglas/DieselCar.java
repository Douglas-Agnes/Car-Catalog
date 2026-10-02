// **********************************************************************************
// Title: Car Catalog
// Author: Douglas Agnes
// Course Section: CMIS201-ONL1 (Seidel) Spring 2024
// File: DieselCar.java
// Description: Creates an object for a diesel car
// **********************************************************************************
package com.example.mpapart4carcatalogagnesdouglas;

import javafx.scene.image.Image;

public class DieselCar extends GasCar {
    private int towingCapacity;
    private int torque;

    // Constructors:

    public DieselCar() {
        super();
        this.towingCapacity = 0;
        this.torque = 0;
    }

    public DieselCar(String make, String model, int year, int doors, String drivetrain, int price, int mileage, Image picture,
                     int mpg, int cylinders, String transmission, String aspiration, int towingCapacity, int torque) {
        super(make, model, year, doors, drivetrain, price, mileage, picture, mpg, cylinders, transmission, aspiration);
        this.towingCapacity = towingCapacity;
        this.torque = torque;
    }

    // Mutators:

    public void setTowingCapacity(int tc) {
        this.towingCapacity = tc;
    }

    public void setTorque(int tq) {
        this.torque = tq;
    }

    // Accessors:

    public int getTowingCapacity() {
        return towingCapacity;
    }

    public int getTorque() {
        return torque;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nTowingCapacity = " + towingCapacity +" lbs" +
                "\nTorque = " + torque + " lb-ft";
    }

    @Override
    public String fields() {
        return super.fields() + '\t' + towingCapacity + '\t' + torque;
    }
}
