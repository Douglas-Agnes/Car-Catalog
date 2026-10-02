// **********************************************************************************
// Title: Car Catalog
// Author: Douglas Agnes
// Course Section: CMIS201-ONL1 (Seidel) Spring 2024
// File: HybridCar.java
// Description: Creates an object for a hybrid car
// **********************************************************************************
package com.example.mpapart4carcatalogagnesdouglas;

import javafx.scene.image.Image;

public class HybridCar extends GasCar{
    private String hybridType;
    private int electricRange;

    // Constructors:

    public HybridCar() {
        super();
        this.hybridType = "";
        this.electricRange = 0;
    }

    public HybridCar(String make, String model, int year, int doors, String drivetrain, int price, int mileage, Image picture,
                     int mpg, int cylinders, String transmission, String aspiration, String hybridType, int electricRange) {
        super(make, model, year, doors, drivetrain, price, mileage, picture, mpg, cylinders, transmission, aspiration);
        this.hybridType = hybridType;
        this.electricRange = electricRange;
    }

    // Mutators:

    public void setHybridType(String ht) {
        this.hybridType = ht;
    }

    public void setElectricRange(int er) {
        this.electricRange = er;
    }

    // Accessors:

    public String getHybridType() {
        return hybridType;
    }

    public int getElectricRange() {
        return electricRange;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nHybridType = '" + hybridType + '\'' +
                "\nElectricRange = " + electricRange + " miles";
    }

    @Override
    public String fields() {
        return super.fields() + '\t' + hybridType + '\t' + electricRange;
    }
}
