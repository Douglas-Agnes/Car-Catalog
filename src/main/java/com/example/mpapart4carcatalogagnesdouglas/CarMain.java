// **********************************************************************************
// Title: Car Catalog
// Author: Douglas Agnes
// Course Section: CMIS201-ONL1 (Seidel) Spring 2024
// File: CarMain.java
// Description: A software system that lets people create a catalog of cars and displays it
// **********************************************************************************
package com.example.mpapart4carcatalogagnesdouglas;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

public class CarMain extends Application{
    // The lists and panes are created as class fields so that they can be accessed and updated in multiple methods
    // Sometimes when they are being used in a method, the method calling it doesn't have access to pass it as a parameter

    // carList stores all Car Objects in memory
    private static final ArrayList<Car> carList = new ArrayList<>();
    // displayList stores only the Car Objects that are being displayed
    private static final ArrayList<Car> displayList = new ArrayList<>();
    // vboxCarPane displays the cars in displayList
    private static final VBox vboxCarPane = new VBox(10);
    // scrollPane displays vboxCarPane
    private static final ScrollPane scrollPaneOfCarPanes = new ScrollPane(vboxCarPane);
    // sortSelect lets the user pick how they sort the list
    private static final ComboBox<String> sortSelect = new ComboBox<>(FXCollections.observableArrayList(null, "Make", "Model", "Lowest Model Year", "Highest Model Year",
            "Lowest Price", "Highest Price", "Lowest Mileage", "Highest Mileage"));

    // The images for the symbols are created in the class because then they only have to be loaded from the web once and can be accessed by the whole program
    // Loading the images each time I found made things really slow

    // Creates the image for the price tag symbol
    private static final Image priceTagPicture = new Image("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRT5LKnEjlQ39jYrOqVFQB1F0ANh4myzMmzQH9ntkOtLt0YcJrz");
    // Creates the image for the odometer symbol
    private static final Image odometerPicture = new Image("https://w7.pngwing.com/pngs/18/150/png-transparent-car-speedometer-odometer-dashboard-car-angle-driving-transport-thumbnail.png");
    // Creates the image for the trash can symbol
    private static final Image trashCanPicture = new Image("https://www.nicepng.com/png/detail/122-1222136_trash-can-icon-free-delete-icon-png-transparent.png");
    // Creates the image for the edit symbol
    private static final Image editPicture = new Image("https://www.clker.com/cliparts/4/3/2/e/12065629511862768218qubodup_16x16px-capable_black_and_white_icons_12.svg.hi.png");

    // Starts the application
    public static void main(String[] args) {
        // Loads in the data and launches the program
        readCarList();
        launch(args);
    }

    // This is the main application
    @Override
    public void start(Stage primaryStage) {
        // Border pane for holding the layout
        BorderPane pane = new BorderPane();

        // Creates the buttons at the top of the application
        Font btFont = Font.font("Arial", FontWeight.BOLD, 50);
        Button addBt = new Button("Add a Car");
        addBt.setOnAction(E -> {
            // Displays the add menu
            displayAdd();
        });
        addBt.setFont(btFont);
        addBt.prefWidthProperty().bind(primaryStage.widthProperty().divide(3));
        addBt.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white; -fx-border-color: black;");
        Button aboutBt = new Button("About");
        aboutBt.setOnAction(E -> {
            // Displays the about menu
            displayAbout();
        });
        aboutBt.setFont(btFont);
        aboutBt.prefWidthProperty().bind(primaryStage.widthProperty().divide(3));
        aboutBt.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white; -fx-border-color: black;");
        Button glossaryBt = new Button("Glossary");
        glossaryBt.setOnAction(E -> {
            // Displays the glossary menu
            displayGlossary();
        });
        glossaryBt.setFont(btFont);
        glossaryBt.prefWidthProperty().bind(primaryStage.widthProperty().divide(3));
        glossaryBt.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white; -fx-border-color: black;");
        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(addBt, aboutBt, glossaryBt);
        topButtons.setStyle("-fx-border-color: black");
        pane.setTop(topButtons);

        // Creates the drop-down box for sorting
        sortSelect.setOnAction(e -> {
            // Sorts display list
            sortDisplayList();
        });
        sortSelect.setStyle("-fx-font-size: 20px; -fx-alignment: CENTER-RIGHT;");
        sortSelect.setPrefWidth(245);

        // Creates the checkboxes on the side for adding or removing the cars from the list
        Font chkFont = Font.font("Arial", 20);
        Label chkLabel = new Label("Show:");
        chkLabel.setFont(Font.font("Arial", 30));
        CheckBox chkElectric = new CheckBox("Electric");
        chkElectric.setFont(chkFont);
        chkElectric.setSelected(true);
        CheckBox chkGas = new CheckBox("Gas");
        chkGas.setFont(chkFont);
        chkGas.setSelected(true);
        CheckBox chkHybrid = new CheckBox("Hybrid");
        chkHybrid.setFont(chkFont);
        chkHybrid.setSelected(true);
        CheckBox chkDiesel = new CheckBox("Diesel");
        chkDiesel.setFont(chkFont);
        chkDiesel.setSelected(true);
        EventHandler<ActionEvent> handler = e -> {
            // Creates a clean slate for the display list
            displayList.clear();
            // Adds the selected car types
            if (chkElectric.isSelected()) {
                addToDisplayList("ElectricCar");
            }
            if (chkGas.isSelected()) {
                addToDisplayList("GasCar");
            }
            if (chkHybrid.isSelected()) {
                addToDisplayList("HybridCar");
            }
            if (chkDiesel.isSelected()) {
                addToDisplayList("DieselCar");
            }
            // Updates the vbox to display the selected car types
            sortDisplayList();
        };
        chkElectric.setOnAction(handler);
        chkGas.setOnAction(handler);
        chkHybrid.setOnAction(handler);
        chkDiesel.setOnAction(handler);
        VBox paneForCheckBoxes = new VBox(20);
        paneForCheckBoxes.getChildren().addAll(sortSelect, chkLabel, chkElectric, chkGas, chkHybrid, chkDiesel);
        paneForCheckBoxes.setPadding(new Insets(20));
        paneForCheckBoxes.setStyle("-fx-border-color: black");
        pane.setLeft(paneForCheckBoxes);

        // Initializes the display pane and vbox for the start
        addToDisplayList("ElectricCar");
        addToDisplayList("GasCar");
        addToDisplayList("HybridCar");
        addToDisplayList("DieselCar");
        vboxCarPane.setPadding(new Insets(20));
        update();
        pane.setCenter(scrollPaneOfCarPanes);

        // Sets up the scene/stage
        Scene scene = new Scene(pane, 1920, 1050);
        primaryStage.setTitle("Car Catalog");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // Creates the HBoxes for displaying each car
    public static HBox carPane(Car car) {

        // Creates the HBox that will be returned
        HBox carPane = new HBox(20);
        carPane.setStyle("-fx-border-color: lightgray;");

        // Creates the image of the car and scales it down to a height of 200
        Image picture = car.getPicture();
        double scaleFactor = 200.0 / picture.getHeight();
        ImageView pictureView = new ImageView(picture);
        pictureView.setFitHeight(200);
        pictureView.setFitWidth(picture.getWidth() * scaleFactor);

        // Creates the brief description of the car that is displayed
        VBox description = new VBox(10);
        description.setAlignment(Pos.CENTER_LEFT);
        Label name = new Label(car.getYear() + " " + car.getMake() + ' ' + car.getModel());
        name.setFont(Font.font(30));
        name.setTextFill(Color.DEEPSKYBLUE);
        ImageView priceTag = new ImageView(priceTagPicture);
        priceTag.setFitHeight(25);
        priceTag.setFitWidth(25);
        Label price = new Label("$" + formatWithCommas(car.getPrice()), priceTag);
        price.setFont(Font.font(20));
        ImageView odometer = new ImageView(odometerPicture);
        odometer.setFitHeight(25);
        odometer.setFitWidth(25);
        Label miles = new Label(formatWithCommas(car.getMileage()) + " miles", odometer);
        miles.setFont(Font.font(20));
        description.getChildren().addAll(name, price, miles);

        // Adds the image and description to the HBox
        carPane.getChildren().addAll(pictureView, description);

        // Displays more detailed information on the car when clicked on
        carPane.setOnMouseClicked(E -> {
            Stage informationStage = new Stage();
            informationStage.setTitle(car.getYear() + " " + car.getMake() + ' ' + car.getModel());
            informationStage.setScene(new Scene(carInformationPane(car)));
            informationStage.show();
        });

        // Returns that HBox
        return carPane;
    }

    // Creates a VBox that displays all the information about a car object
    // Returns a VBox rather than display a stage because it is used and added to in a few different places
    public static VBox carInformationPane(Car car) {

        // Creates the VBox and sets up fonts and constraints for later use
        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(10));
        Font lbFont = Font.font("Arial", FontWeight.BOLD, 20);
        Font fieldFont = Font.font("Arial", 20);
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPrefWidth(260);

        // Creates the label at the top with the car name
        Label name = new Label(car.getMake() + ' ' + car.getModel());
        name.setPadding(new Insets(5));
        name.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        name.prefWidthProperty().bind(vBox.widthProperty());
        name.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white;");
        name.setAlignment(Pos.CENTER);

        // Creates the image that is displayed
        Image picture = car.getPicture();
        double scaleFactor = Math.min(600.0 / picture.getWidth(), 300.0 / picture.getHeight());
        ImageView pictureView = new ImageView(picture);
        pictureView.setFitWidth(picture.getWidth() * scaleFactor);
        pictureView.setFitHeight(picture.getHeight() * scaleFactor);

        // Creates the label for the overview information
        Label lbOverview = new Label("Overview");
        lbOverview.setPadding(new Insets(5));
        lbOverview.setFont(lbFont);
        lbOverview.prefWidthProperty().bind(vBox.widthProperty());
        lbOverview.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white;");
        lbOverview.setAlignment(Pos.CENTER);

        // Creates the displayed information for the car overview
        GridPane gpOverview = new GridPane();
        gpOverview.getColumnConstraints().add(0, column0);
        gpOverview.setHgap(5);
        Label lbMake = new Label("Manufacturer:");
        lbMake.setFont(lbFont);
        Label lbModel = new Label("Model:");
        lbModel.setFont(lbFont);
        Label lbYear = new Label("Model Year:");
        lbYear.setFont(lbFont);
        Label lbPrice = new Label("Price:");
        lbPrice.setFont(lbFont);
        gpOverview.addColumn(0, lbMake, lbModel, lbYear, lbPrice);
        Label carMake = new Label(car.getMake());
        carMake.setFont(fieldFont);
        Label carModel = new Label(car.getModel());
        carModel.setFont(fieldFont);
        Label carYear = new Label(car.getYear() + "");
        carYear.setFont(fieldFont);
        Label carPrice = new Label("$" + formatWithCommas(car.getPrice()));
        carPrice.setFont(fieldFont);
        gpOverview.addColumn(1, carMake, carModel, carYear, carPrice);

        // Creates the label for the body and chassis information
        Label lbBodyAndChassis = new Label("Body and Chassis");
        lbBodyAndChassis.setPadding(new Insets(5));
        lbBodyAndChassis.setFont(lbFont);
        lbBodyAndChassis.prefWidthProperty().bind(vBox.widthProperty());
        lbBodyAndChassis.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white;");
        lbBodyAndChassis.setAlignment(Pos.CENTER);

        // Creates the displayed information for the body and chassis
        GridPane gpBodyAndChassis = new GridPane();
        gpBodyAndChassis.getColumnConstraints().add(0, column0);
        gpBodyAndChassis.setHgap(5);
        Label lbDoors = new Label("Doors:");
        lbDoors.setFont(lbFont);
        Label lbDrivetrain = new Label("Drivetrain:");
        lbDrivetrain.setFont(lbFont);
        Label lbMileage = new Label("Mileage:");
        lbMileage.setFont(lbFont);
        gpBodyAndChassis.addColumn(0, lbDoors, lbDrivetrain, lbMileage);
        Label carDoors = new Label(car.getDoors() + "");
        carDoors.setFont(fieldFont);
        Label carDrivetrain = new Label(car.getDrivetrain());
        carDrivetrain.setFont(fieldFont);
        Label carMileage = new Label(formatWithCommas(car.getMileage()) + " miles");
        carMileage.setFont(fieldFont);
        gpBodyAndChassis.addColumn(1, carDoors, carDrivetrain, carMileage);

        // Creates the label for the powertrain information
        Label lbPowertrain = new Label("Powertrain");
        lbPowertrain.setPadding(new Insets(5));
        lbPowertrain.setFont(lbFont);
        lbPowertrain.prefWidthProperty().bind(vBox.widthProperty());
        lbPowertrain.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white;");
        lbPowertrain.setAlignment(Pos.CENTER);

        // Creates the displayed information for the powertrain
        GridPane gpPowertrain = new GridPane();
        gpPowertrain.getColumnConstraints().add(0, column0);
        gpPowertrain.setHgap(5);
        Label lbFuelType = new Label("Fuel type:");
        lbFuelType.setFont(lbFont);
        gpPowertrain.addColumn(0, lbFuelType);
        Label carFuelType = new Label(car.getClass().getSimpleName().substring(0, car.getClass().getSimpleName().indexOf("Car")));
        carFuelType.setFont(fieldFont);
        gpPowertrain.addColumn(1, carFuelType);

        // Adds all the labels and information to the VBox
        vBox.getChildren().addAll(name, pictureView, lbOverview, gpOverview, lbBodyAndChassis, gpBodyAndChassis, lbPowertrain, gpPowertrain);

        // Adds the information specific to an electric car
        if(car.getClass().getSimpleName().equals("ElectricCar")) {

            // Casts the Car to ElectricCar to use the methods defined in ElectricCar
            ElectricCar eCar = (ElectricCar) car;

            // Creates the displayed information specific to an electric car
            Label lbRange = new Label("Range:");
            lbRange.setFont(lbFont);
            Label lbElectricMotors = new Label("Number of Electric Motors:");
            lbElectricMotors.setFont(lbFont);
            Label lbLvl3Charging = new Label("Level 3 Charging Capable:");
            lbLvl3Charging.setFont(lbFont);
            gpPowertrain.addColumn(0, lbRange, lbElectricMotors, lbLvl3Charging);
            Label carRange = new Label(formatWithCommas(eCar.getRange()) + " miles");
            carRange.setFont(fieldFont);
            Label carElectricMotors = new Label(eCar.getNumOfMotors() + "");
            carElectricMotors.setFont(fieldFont);
            String str = eCar.isLvl3ChargingCapable() + "";
            Label carLvl3Charging = new Label(Character.toUpperCase(str.charAt(0)) + str.substring(1));
            carLvl3Charging.setFont(fieldFont);
            gpPowertrain.addColumn(1, carRange, carElectricMotors, carLvl3Charging);

        }

        // Adds the information specific to all gas cars and its subclasses
        else if(car.getClass().getSimpleName().equals("GasCar") || car.getClass().getSimpleName().equals("DieselCar") ||
                car.getClass().getSimpleName().equals("HybridCar")) {

            // Casts the Car to a GasCar to use the methods defined in GasCar
            GasCar gCar = (GasCar)car;

            // Creates the displayed information specific to a gas car
            Label lbMPG = new Label("Miles per Gallon:");
            lbMPG.setFont(lbFont);
            Label lbCylinders = new Label("Number of Cylinders:");
            lbCylinders.setFont(lbFont);
            Label lbTransmission = new Label("Transmission:");
            lbTransmission.setFont(lbFont);
            Label lbAspiration = new Label("Aspiration:");
            lbAspiration.setFont(lbFont);
            gpPowertrain.addColumn(0, lbMPG, lbCylinders, lbTransmission, lbAspiration);
            Label carMPG = new Label(gCar.getMpg() + "");
            carMPG.setFont(fieldFont);
            Label carCylinders = new Label(gCar.getCylinders() + "");
            carCylinders.setFont(fieldFont);
            Label carTransmission = new Label(gCar.getTransmission());
            carTransmission.setFont(fieldFont);
            Label carAspiration = new Label(gCar.getAspiration());
            carAspiration.setFont(fieldFont);
            gpPowertrain.addColumn(1, carMPG, carCylinders, carTransmission, carAspiration);

            // Adds the information specific to a diesel car
            if(car.getClass().getSimpleName().equals("DieselCar")) {

                // Casts the Car to a DieselCar to use the methods defined in DieselCar
                DieselCar dCar = (DieselCar)car;

                // Creates the displayed information specific to a diesel car
                Label lbTowingCapacity = new Label("Towing Capacity:");
                lbTowingCapacity.setFont(lbFont);
                Label lbTorque = new Label("Torque:");
                lbTorque.setFont(lbFont);
                gpPowertrain.addColumn(0, lbTowingCapacity, lbTorque);
                Label carTowingCapacity = new Label(formatWithCommas(dCar.getTowingCapacity()) + " lbs");
                carTowingCapacity.setFont(fieldFont);
                Label carTorque = new Label(formatWithCommas(dCar.getTorque()) + " lb-ft");
                carTorque.setFont(fieldFont);
                gpPowertrain.addColumn(1, carTowingCapacity, carTorque);

            }

            // Adds the information specific to a hybrid car
            else if(car.getClass().getSimpleName().equals("HybridCar")) {

                // Casts the Car to a HybridCar to use the methods defined in HybridCar
                HybridCar hCar = (HybridCar)car;

                // Creates the displayed information specific to a hybrid car
                Label lbHybridType = new Label("Hybrid Type:");
                lbHybridType.setFont(lbFont);
                Label lbElectricRange = new Label("Electric Range:");
                lbElectricRange.setFont(lbFont);
                gpPowertrain.addColumn(0, lbHybridType, lbElectricRange);
                Label carHybridType = new Label(hCar.getHybridType());
                carHybridType.setFont(fieldFont);
                Label carElectricRange = new Label(formatWithCommas(hCar.getElectricRange()) + " miles");
                carElectricRange.setFont(fieldFont);
                gpPowertrain.addColumn(1, carHybridType, carElectricRange);

            }
        }

        // returns the VBox
        return vBox;
    }

    // Displays the stage for adding a new car
    public static void displayAdd() {

        // Creates the Stage
        Stage stage = new Stage();
        stage.setTitle("Add a car");

        // Creates the VBox and gridPane to hold all the labels and fields for adding a new car
        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(10));
        GridPane gpCar = new GridPane();
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPrefWidth(300);
        gpCar.getColumnConstraints().add(0, column0);
        gpCar.setAlignment(Pos.CENTER);
        gpCar.setHgap(10);
        gpCar.setVgap(5);
        Font titleFont = Font.font("Arial", FontWeight.BOLD, 35);
        Font lbFont = Font.font("Arial", 25);
        Font textBoxFont = Font.font("Arial", 20);

        // Creates the main title at the top of the pane
        Label titleCar = new Label("Car Attributes");
        titleCar.setPadding(new Insets(5));
        titleCar.setFont(titleFont);
        titleCar.prefWidthProperty().bind(vBox.widthProperty());
        titleCar.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white;");
        titleCar.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(titleCar, gpCar);

        // Creates and adds the labels for the fields for all cars
        Label lbMake = new Label("Make:");
        lbMake.setFont(lbFont);
        Label lbModel = new Label("Model:");
        lbModel.setFont(lbFont);
        Label lbYear = new Label("Year:");
        lbYear.setFont(lbFont);
        Label lbDoors = new Label("Doors:");
        lbDoors.setFont(lbFont);
        Label lbDrivetrain = new Label("Drivetrain:");
        lbDrivetrain.setFont(lbFont);
        Label lbPrice = new Label("Price:");
        lbPrice.setFont(lbFont);
        Label lbMileage = new Label("Mileage:");
        lbMileage.setFont(lbFont);
        Label lbPicture = new Label("Picture URL:");
        lbPicture.setFont(lbFont);
        Label lbFuelType = new Label("Fuel Type:");
        lbFuelType.setFont(lbFont);
        gpCar.addColumn(0, lbMake, lbModel, lbYear, lbDoors, lbDrivetrain, lbPrice, lbMileage, lbPicture, lbFuelType);

        // Creates and adds the fields for all cars
        TextField fieldMake = new TextField();
        fieldMake.setFont(textBoxFont);
        TextField fieldModel = new TextField();
        fieldModel.setFont(textBoxFont);
        Slider sliderYear = new Slider(1900, Year.now().getValue() + 1, 2000);
        Label fieldYear = new Label("2000", sliderYear);
        fieldYear.setFont(textBoxFont);
        fieldYear.setContentDisplay(ContentDisplay.RIGHT);
        sliderYear.setPrefWidth(300);
        sliderYear.valueProperty().addListener((observable, oldValue, newValue) -> fieldYear.setText(newValue.intValue() + ""));
        Slider sliderDoors = new Slider(1, 6, 4);
        Label fieldDoors = new Label("4      ", sliderDoors);
        fieldDoors.setFont(textBoxFont);
        fieldDoors.setContentDisplay(ContentDisplay.RIGHT);
        sliderDoors.setPrefWidth(300);
        sliderDoors.valueProperty().addListener((observable, oldValue, newValue) -> fieldDoors.setText(newValue.intValue() + "      "));
        ComboBox<String> fieldDrivetrain = new ComboBox<>(FXCollections.observableArrayList("FWD", "RWD", "AWD", "4WD"));
        fieldDrivetrain.setStyle("-fx-font-size: 20px; -fx-alignment: CENTER-RIGHT;");
        fieldDrivetrain.setPrefWidth(350);
        TextField fieldPrice = new TextField();
        fieldPrice.setFont(textBoxFont);
        TextField fieldMileage = new TextField();
        fieldMileage.setFont(textBoxFont);
        TextField fieldURL = new TextField();
        fieldURL.setFont(textBoxFont);
        RadioButton rbElectric = new RadioButton("Electric");
        RadioButton rbGas = new RadioButton("Gas");
        rbElectric.setFont(textBoxFont);
        rbGas.setFont(textBoxFont);
        ToggleGroup fuelType = new ToggleGroup();
        rbElectric.setToggleGroup(fuelType);
        rbGas.setToggleGroup(fuelType);
        HBox hBoxFuelType = new HBox(20, rbElectric, rbGas);
        gpCar.addColumn(1, fieldMake, fieldModel, fieldYear, fieldDoors, fieldDrivetrain, fieldPrice, fieldMileage, fieldURL, hBoxFuelType);

        // Creates the labels for the fields for an electric car
        Label lbRange = new Label("Range:");
        lbRange.setFont(lbFont);
        Label lbMotors = new Label("Number of Electric Motors:");
        lbMotors.setFont(lbFont);
        Label lbLvl3Charging = new Label("Level 3 Charging Capable:");
        lbLvl3Charging.setFont(lbFont);

        // Creates the fields for an electric car
        TextField fieldRange = new TextField();
        fieldRange.setFont(textBoxFont);
        Slider sliderMotors = new Slider(1, 4, 1);
        Label fieldMotors = new Label("1     ", sliderMotors);
        fieldMotors.setFont(textBoxFont);
        fieldMotors.setContentDisplay(ContentDisplay.RIGHT);
        sliderMotors.setPrefWidth(300);
        sliderMotors.valueProperty().addListener((observable, oldValue, newValue) -> fieldMotors.setText(newValue.intValue() + "     "));
        RadioButton rbTrue = new RadioButton("Yes");
        RadioButton rbFalse = new RadioButton("No");
        rbTrue.setFont(textBoxFont);
        rbFalse.setFont(textBoxFont);
        ToggleGroup lvl3Charging = new ToggleGroup();
        rbTrue.setToggleGroup(lvl3Charging);
        rbFalse.setToggleGroup(lvl3Charging);
        HBox hBoxLvl3Charging = new HBox(20, rbTrue, rbFalse);

        // Creates the labels for the fields for all gas cars
        Label lbMPG = new Label("Miles Per Gallon:");
        lbMPG.setFont(lbFont);
        Label lbCylinders = new Label("Number of Cylinders:");
        lbCylinders.setFont(lbFont);
        Label lbTransmission = new Label("Transmission:");
        lbTransmission.setFont(lbFont);
        Label lbAspiration = new Label("Aspiration:");
        lbAspiration.setFont(lbFont);
        Label lbGasType = new Label("Gas Type:");
        lbGasType.setFont(lbFont);

        // Creates the fields for all gas cars
        Slider sliderMPG = new Slider(1, 100, 1);
        Label fieldMPG = new Label("1     ", sliderMPG);
        fieldMPG.setFont(textBoxFont);
        fieldMPG.setContentDisplay(ContentDisplay.RIGHT);
        sliderMPG.setPrefWidth(300);
        sliderMPG.valueProperty().addListener((observable, oldValue, newValue) -> {
            int val = newValue.intValue();
            if(val < 10)
                fieldMPG.setText(val + "     ");
            else if (val < 100)
                fieldMPG.setText(val + "   ");
            else if (val == 100)
                fieldMPG.setText(val + " ");
        });
        Slider sliderCylinders = new Slider(1, 16, 1);
        Label fieldCylinders = new Label("1     ", sliderCylinders);
        fieldCylinders.setFont(textBoxFont);
        fieldCylinders.setContentDisplay(ContentDisplay.RIGHT);
        sliderCylinders.setPrefWidth(300);
        sliderCylinders.valueProperty().addListener((observable, oldValue, newValue) -> {
            int val = newValue.intValue();
            if(val < 10)
                fieldCylinders.setText(val + "     ");
            else
                fieldCylinders.setText(val + "   ");
        });
        ComboBox<String> fieldTransmission = new ComboBox<>(FXCollections.observableArrayList("Automatic", "Manual", "CVT"));
        fieldTransmission.setStyle("-fx-font-size: 20px; -fx-alignment: CENTER-RIGHT;");
        fieldTransmission.setPrefWidth(350);
        ComboBox<String> fieldAspiration = new ComboBox<>(FXCollections.observableArrayList("Naturally Aspirated", "Turbocharged", "Supercharged"));
        fieldAspiration.setStyle("-fx-font-size: 20px; -fx-alignment: CENTER-RIGHT;");
        fieldAspiration.setPrefWidth(350);
        RadioButton rbRegular = new RadioButton("Regular");
        RadioButton rbDiesel = new RadioButton("Diesel");
        RadioButton rbHybrid = new RadioButton("Hybrid");
        rbRegular.setFont(textBoxFont);
        rbDiesel.setFont(textBoxFont);
        rbHybrid.setFont(textBoxFont);
        ToggleGroup gasType = new ToggleGroup();
        rbRegular.setToggleGroup(gasType);
        rbDiesel.setToggleGroup(gasType);
        rbHybrid.setToggleGroup(gasType);
        HBox hBoxGasType = new HBox(20, rbRegular, rbDiesel, rbHybrid);

        // Creates the labels for the fields for a diesel car
        Label lbTowingCap = new Label("Towing Capacity:");
        lbTowingCap.setFont(lbFont);
        Label lbTorque = new Label("Torque:");
        lbTorque.setFont(lbFont);

        // Creates the fields for a diesel car
        TextField fieldTowingCap = new TextField();
        fieldTowingCap.setFont(textBoxFont);
        TextField fieldTorque = new TextField();
        fieldTorque.setFont(textBoxFont);

        // Creates the labels for the fields for a hybrid car
        Label lbHybridType = new Label("Hybrid Type:");
        lbHybridType.setFont(lbFont);
        Label lbElectricRange = new Label("Electric Range:");
        lbElectricRange.setFont(lbFont);

        // Creates the fields for a hybrid car
        ComboBox<String> fieldHybridType = new ComboBox<>(FXCollections.observableArrayList("Parallel", "Series", "Plug-In"));
        fieldHybridType.setStyle("-fx-font-size: 20px; -fx-alignment: CENTER-RIGHT;");
        fieldHybridType.setPrefWidth(350);
        TextField fieldElectricRange = new TextField();
        fieldElectricRange.setFont(textBoxFont);

        // Creates the button for finalizing the new car
        Button btAdd = new Button("Add Car");
        btAdd.setFont(lbFont);
        GridPane.setHalignment(btAdd, HPos.RIGHT);

        // Removes the non-gas or diesel car fields and adds the diesel car fields and add button
        rbDiesel.setOnAction(e -> {
            if (rbDiesel.isSelected()) {
                gpCar.getChildren().removeAll(lbHybridType, lbElectricRange, fieldHybridType, fieldElectricRange, btAdd);
                gpCar.addColumn(0, lbTowingCap, lbTorque);
                gpCar.addColumn(1, fieldTowingCap, fieldTorque, btAdd);
            }
        });

        // Removes the non-gas or hybrid car fields and adds the hybrid car fields and add button
        rbHybrid.setOnAction(e -> {
            if (rbHybrid.isSelected()) {
                gpCar.getChildren().removeAll(lbTowingCap, lbTorque, fieldTowingCap, fieldTorque, btAdd);
                gpCar.addColumn(0, lbHybridType, lbElectricRange);
                gpCar.addColumn(1, fieldHybridType, fieldElectricRange, btAdd);
            }
        });

        // Removes the non-gas car fields and adds the add button
        rbRegular.setOnAction(e -> {
            if (rbRegular.isSelected()) {
                gpCar.getChildren().removeAll(lbTowingCap, lbTorque, fieldTowingCap, fieldTorque, lbHybridType, lbElectricRange, fieldHybridType, fieldElectricRange, btAdd);
                gpCar.addColumn(1, btAdd);
            }
        });

        // Removes the non-electric car fields and adds the electric car fields and add button
        rbElectric.setOnAction(e -> {
            if (rbElectric.isSelected()) {
                gpCar.getChildren().removeAll(lbMPG, lbCylinders, lbTransmission, lbAspiration, fieldMPG, fieldCylinders, fieldTransmission, fieldAspiration, lbGasType,
                        hBoxGasType,lbTowingCap, lbTorque, fieldTowingCap, fieldTorque, lbHybridType, lbElectricRange, fieldHybridType, fieldElectricRange, btAdd);
                gasType.selectToggle(null);
                gpCar.addColumn(0, lbRange, lbMotors, lbLvl3Charging);
                gpCar.addColumn(1, fieldRange, fieldMotors, hBoxLvl3Charging, btAdd);
            }
        });

        // Removes the non-gas car fields and adds the gas car fields and add button
        rbGas.setOnAction(e -> {
            if (rbGas.isSelected()) {
                gpCar.getChildren().removeAll(lbRange, lbMotors, lbLvl3Charging, fieldRange, fieldMotors, hBoxLvl3Charging, btAdd);
                gpCar.addColumn(0, lbMPG, lbCylinders, lbTransmission, lbAspiration, lbGasType);
                gpCar.addColumn(1, fieldMPG, fieldCylinders, fieldTransmission, fieldAspiration, hBoxGasType);
            }
        });

        // Creates the Car object if the fields are valid and the user confirms
        btAdd.setOnAction(e -> {

            // Tries to create the Car object
            try {
                // Counts the number of empty fields
                int empty = 0;

                // Adds the number of empty fields for all car types
                if (fieldMake.getText().isEmpty())
                    empty++;
                if (fieldModel.getText().isEmpty())
                    empty++;
                if (fieldDrivetrain.getValue() == null)
                    empty++;
                if (fieldPrice.getText().isEmpty())
                    empty++;
                if (fieldMileage.getText().isEmpty())
                    empty++;
                if (fieldURL.getText().isEmpty())
                    empty++;

                // Tests to see if it is creating an electric car
                if (gpCar.getChildren().contains(lbRange)) {
                    // Adds the number of empty fields for an electric car
                    if (fieldRange.getText().isEmpty())
                        empty++;
                    if (lvl3Charging.getSelectedToggle() == null)
                        empty++;
                }

                // Tests to see if it is creating a gas car
                if (gpCar.getChildren().contains(fieldMPG)) {
                    // Adds the number of empty fields for a gas car
                    if (fieldTransmission.getValue() == null)
                        empty++;
                    if (fieldAspiration.getValue() == null)
                        empty++;
                }

                // Tests to see if it is creating a diesel car
                if (gpCar.getChildren().contains(fieldTowingCap)) {
                    // Adds the number of empty fields for a diesel car
                    if (fieldTowingCap.getText().isEmpty())
                        empty++;
                    if (fieldTorque.getText().isEmpty())
                        empty++;
                }

                // Tests to see if it is creating a hybrid car
                if (gpCar.getChildren().contains(fieldHybridType)) {
                    // Adds the number of empty fields for a hybrid car
                    if (fieldHybridType.getValue() == null)
                        empty++;
                    if (fieldElectricRange.getText().isEmpty())
                        empty++;
                }

                // Throws an exception if there are any empty fields
                if (empty != 0)
                    throw new EmptyFieldsException(empty);

                // Declares the car variable outside the if statements where it is initialized, so it can be accessed outside of them
                Car car;

                // Checks to see if an electric car data field is displayed to decide to create an ElectricCar
                if (gpCar.getChildren().contains(lbRange)) {
                    // Creates an ElectricCar
                    car = new ElectricCar(fieldMake.getText(), fieldModel.getText(), (int)sliderYear.getValue(), (int)sliderDoors.getValue(), fieldDrivetrain.getValue(),
                            Integer.parseInt(fieldPrice.getText()), Integer.parseInt(fieldMileage.getText()), new Image(fieldURL.getText()),
                            Integer.parseInt(fieldRange.getText()), (int)sliderMotors.getValue(), rbTrue.isSelected());
                }

                // Checks to see if a diesel car data field is displayed to decide to create a DieselCar
                else if (gpCar.getChildren().contains(fieldTowingCap)) {
                    // Creates a DieselCar
                    car = new DieselCar(fieldMake.getText(), fieldModel.getText(), (int)sliderYear.getValue(), (int)sliderDoors.getValue(), fieldDrivetrain.getValue(),
                            Integer.parseInt(fieldPrice.getText()), Integer.parseInt(fieldMileage.getText()), new Image(fieldURL.getText()), (int)sliderMPG.getValue(),
                            (int)sliderCylinders.getValue(), fieldTransmission.getValue(), fieldAspiration.getValue(), Integer.parseInt(fieldTowingCap.getText()),
                            Integer.parseInt(fieldTorque.getText()));
                }

                // Checks to see if a hybrid car data field is displayed to decide to create a HybridCar
                else if (gpCar.getChildren().contains(fieldHybridType)) {
                    // Creates a HybridCar
                    car = new HybridCar(fieldMake.getText(), fieldModel.getText(), (int)sliderYear.getValue(), (int)sliderDoors.getValue(), fieldDrivetrain.getValue(),
                            Integer.parseInt(fieldPrice.getText()), Integer.parseInt(fieldMileage.getText()), new Image(fieldURL.getText()), (int)sliderMPG.getValue(),
                            (int)sliderCylinders.getValue(), fieldTransmission.getValue(), fieldAspiration.getValue(), fieldHybridType.getValue(),
                            Integer.parseInt(fieldElectricRange.getText()));
                }

                // Only other option is to create a GasCar
                // This had to be an else statement not an else-if otherwise the compiler couldn't guarantee the car variable had been initialized
                else {
                    // Creates a GasCar
                    car = new GasCar(fieldMake.getText(), fieldModel.getText(), (int)sliderYear.getValue(), (int)sliderDoors.getValue(), fieldDrivetrain.getValue(),
                            Integer.parseInt(fieldPrice.getText()), Integer.parseInt(fieldMileage.getText()), new Image(fieldURL.getText()), (int)sliderMPG.getValue(),
                            (int)sliderCylinders.getValue(), fieldTransmission.getValue(), fieldAspiration.getValue());
                }

                // Creates a stage for displaying this new car and having the user confirm things look right
                Stage confirmStage = new Stage();
                confirmStage.initModality(Modality.APPLICATION_MODAL);
                confirmStage.setTitle("Confirmation");

                // Gets the carInformationPane of the new car
                VBox vBoxConfirm = carInformationPane(car);

                // Creates the confirmation text and buttons
                Font font = Font.font("Arial", FontWeight.BOLD, 20);
                HBox addition = new HBox(20);
                Label lb = new Label("Does everything look correct?");
                lb.setFont(font);
                lb.setStyle("-fx-text-fill: green;");
                Button btYes = new Button("YES");
                btYes.setOnAction(E -> {
                    // Adds the car to storage and both arrayLists
                    add(car);
                    // Closes the confirmation stage and add stage
                    confirmStage.close();
                    stage.close();
                });
                btYes.setFont(font);
                btYes.setStyle("-fx-text-fill: green; -fx-border-color: green;");
                Button btNo = new Button("NO");
                btNo.setOnAction(E -> {
                    // Closes only the confirmation stage
                    confirmStage.close();
                });
                btNo.setFont(font);
                btNo.setStyle("-fx-text-fill: green; -fx-border-color: green;");
                addition.getChildren().addAll(lb, btYes, btNo);
                addition.setAlignment(Pos.CENTER);
                vBoxConfirm.getChildren().add(addition);

                // Sets the scene and shows the confirmation stage
                confirmStage.setScene(new Scene(vBoxConfirm));
                confirmStage.show();

            } catch (EmptyFieldsException E) {
                // Lets the user know how many empty fields there are
                displayMessageWithOk(E.getMessage());
            } catch (NumberFormatException E) {
                // Lets the user know that one of the fields they entered is the wrong data type
                displayMessageWithOk("Invalid information in one of the fields. \nPlease make sure things like 'Price' are entered as a number.");
            } catch (IllegalArgumentException E) {
                // Lets the user know that the image URL they entered was bad
                displayMessageWithOk("Invalid image URL. \nPlease make sure you include 'https://'.");
            }
        });

        // Sets the scene and shows the add stage
        stage.setScene(new Scene(vBox, vBox.getPrefWidth(), 780));
        stage.show();
    }

    // Displays the stage for deleting a new car
    public static void displayDelete(Car car) {

        // Creates a stage for displaying the car to be deleted and having the user confirm
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Are you sure you want to delete this " + car.getYear() + " " + car.getMake() + ' ' + car.getModel() + "?");

        // Gets the carInformationPane of the new car
        VBox vBox = carInformationPane(car);

        // Creates a HBox and font for the buttons and label
        HBox deletion = new HBox(20);
        Font font = Font.font("Arial", FontWeight.BOLD, 20);

        // Creates the label
        Label lb = new Label(stage.getTitle().toUpperCase());
        lb.setFont(font);
        lb.setStyle("-fx-text-fill: red;");

        // Creates the yes button
        Button btYes = new Button("YES");
        btYes.setOnAction(E -> {
            // Removes the car and closes the deletion stage
            remove(car);
            stage.close();
        });
        btYes.setFont(font);
        btYes.setStyle("-fx-text-fill: red; -fx-border-color: red;");

        // Creates the no button
        Button btNo = new Button("NO");
        btNo.setOnAction(E -> {
            // Closes the deletion stage
            stage.close();
        });
        btNo.setFont(font);
        btNo.setStyle("-fx-text-fill: red; -fx-border-color: red;");

        // Adds the label and buttons to the vBox
        deletion.getChildren().addAll(lb, btYes, btNo);
        deletion.setAlignment(Pos.CENTER);
        vBox.getChildren().add(deletion);

        // Sets the scene and displays the stage
        stage.setScene(new Scene(vBox));
        stage.show();
    }

    // Displays the stage for editing an existing car
    public static void displayEdit(Car car) {

        // Creates the Stage
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit " + car.getYear() + " " + car.getMake() + ' ' + car.getModel());

        // Creates the VBox and gridPane to hold all the labels and fields for adding a new car
        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(10));
        GridPane gpCar = new GridPane();
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPrefWidth(300);
        gpCar.getColumnConstraints().add(0, column0);
        gpCar.setAlignment(Pos.CENTER);
        gpCar.setHgap(10);
        gpCar.setVgap(5);
        Font titleFont = Font.font("Arial", FontWeight.BOLD, 35);
        Font lbFont = Font.font("Arial", 25);
        Font textBoxFont = Font.font("Arial", 20);

        // Creates the main title at the top of the pane
        Label titleCar = new Label("Car Attributes");
        titleCar.setPadding(new Insets(5));
        titleCar.setFont(titleFont);
        titleCar.prefWidthProperty().bind(vBox.widthProperty());
        titleCar.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white;");
        titleCar.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(titleCar, gpCar);

        // Creates and adds the labels for the fields for all cars
        Label lbMake = new Label("Make:");
        lbMake.setFont(lbFont);
        Label lbModel = new Label("Model:");
        lbModel.setFont(lbFont);
        Label lbYear = new Label("Year:");
        lbYear.setFont(lbFont);
        Label lbDoors = new Label("Doors:");
        lbDoors.setFont(lbFont);
        Label lbDrivetrain = new Label("Drivetrain:");
        lbDrivetrain.setFont(lbFont);
        Label lbPrice = new Label("Price:");
        lbPrice.setFont(lbFont);
        Label lbMileage = new Label("Mileage:");
        lbMileage.setFont(lbFont);
        Label lbPicture = new Label("Picture URL:");
        lbPicture.setFont(lbFont);
        gpCar.addColumn(0, lbMake, lbModel, lbYear, lbDoors, lbDrivetrain, lbPrice, lbMileage, lbPicture);

        // Creates, adds, sets the fields for all cars to their current value
        TextField fieldMake = new TextField(car.getMake());
        fieldMake.setFont(textBoxFont);
        TextField fieldModel = new TextField(car.getModel());
        fieldModel.setFont(textBoxFont);
        Slider sliderYear = new Slider(1900, Year.now().getValue() + 1, 2000);
        Label fieldYear = new Label(car.getYear() + "", sliderYear);
        fieldYear.setFont(textBoxFont);
        fieldYear.setContentDisplay(ContentDisplay.RIGHT);
        sliderYear.setPrefWidth(300);
        sliderYear.valueProperty().addListener((observable, oldValue, newValue) -> fieldYear.setText(newValue.intValue() + ""));
        sliderYear.setValue(car.getYear());
        Slider sliderDoors = new Slider(1, 6, 4);
        Label fieldDoors = new Label(car.getDoors() + "      ", sliderDoors);
        fieldDoors.setFont(textBoxFont);
        fieldDoors.setContentDisplay(ContentDisplay.RIGHT);
        sliderDoors.setPrefWidth(300);
        sliderDoors.valueProperty().addListener((observable, oldValue, newValue) -> fieldDoors.setText(newValue.intValue() + "      "));
        sliderDoors.setValue(car.getDoors());
        ComboBox<String> fieldDrivetrain = new ComboBox<>(FXCollections.observableArrayList("FWD", "RWD", "AWD", "4WD"));
        fieldDrivetrain.setStyle("-fx-font-size: 20px; -fx-alignment: CENTER-RIGHT;");
        fieldDrivetrain.setPrefWidth(350);
        fieldDrivetrain.setValue(car.getDrivetrain());
        TextField fieldPrice = new TextField(car.getPrice() + "");
        fieldPrice.setFont(textBoxFont);
        TextField fieldMileage = new TextField(car.getMileage() + "");
        fieldMileage.setFont(textBoxFont);
        TextField fieldURL = new TextField(car.getPicture().getUrl());
        fieldURL.setFont(textBoxFont);
        gpCar.addColumn(1, fieldMake, fieldModel, fieldYear, fieldDoors, fieldDrivetrain, fieldPrice, fieldMileage, fieldURL);

        // Creates the labels for the fields for an electric car
        Label lbRange = new Label("Range:");
        lbRange.setFont(lbFont);
        Label lbMotors = new Label("Number of Electric Motors:");
        lbMotors.setFont(lbFont);
        Label lbLvl3Charging = new Label("Level 3 Charging Capable:");
        lbLvl3Charging.setFont(lbFont);

        // Creates the fields for an electric car
        TextField fieldRange = new TextField();
        fieldRange.setFont(textBoxFont);
        Slider sliderMotors = new Slider(1, 4, 1);
        Label fieldMotors = new Label("1     ", sliderMotors);
        fieldMotors.setFont(textBoxFont);
        fieldMotors.setContentDisplay(ContentDisplay.RIGHT);
        sliderMotors.setPrefWidth(300);
        sliderMotors.valueProperty().addListener((observable, oldValue, newValue) -> fieldMotors.setText(newValue.intValue() + "     "));
        RadioButton rbTrue = new RadioButton("Yes");
        RadioButton rbFalse = new RadioButton("No");
        rbTrue.setFont(textBoxFont);
        rbFalse.setFont(textBoxFont);
        ToggleGroup lvl3Charging = new ToggleGroup();
        rbTrue.setToggleGroup(lvl3Charging);
        rbFalse.setToggleGroup(lvl3Charging);
        HBox hBoxLvl3Charging = new HBox(20, rbTrue, rbFalse);

        // Creates the labels for the fields for all gas cars
        Label lbMPG = new Label("Miles Per Gallon:");
        lbMPG.setFont(lbFont);
        Label lbCylinders = new Label("Number of Cylinders:");
        lbCylinders.setFont(lbFont);
        Label lbTransmission = new Label("Transmission:");
        lbTransmission.setFont(lbFont);
        Label lbAspiration = new Label("Aspiration:");
        lbAspiration.setFont(lbFont);

        // Creates the fields for all gas cars
        Slider sliderMPG = new Slider(1, 100, 1);
        Label fieldMPG = new Label("1     ", sliderMPG);
        fieldMPG.setFont(textBoxFont);
        fieldMPG.setContentDisplay(ContentDisplay.RIGHT);
        sliderMPG.setPrefWidth(300);
        sliderMPG.valueProperty().addListener((observable, oldValue, newValue) -> {
            int val = newValue.intValue();
            if(val < 10)
                fieldMPG.setText(val + "     ");
            else if (val < 100)
                fieldMPG.setText(val + "   ");
            else if (val == 100)
                fieldMPG.setText(val + " ");
        });
        Slider sliderCylinders = new Slider(1, 16, 1);
        Label fieldCylinders = new Label("1     ", sliderCylinders);
        fieldCylinders.setFont(textBoxFont);
        fieldCylinders.setContentDisplay(ContentDisplay.RIGHT);
        sliderCylinders.setPrefWidth(300);
        sliderCylinders.valueProperty().addListener((observable, oldValue, newValue) -> {
            int val = newValue.intValue();
            if(val < 10)
                fieldCylinders.setText(val + "     ");
            else
                fieldCylinders.setText(val + "   ");
        });
        ComboBox<String> fieldTransmission = new ComboBox<>(FXCollections.observableArrayList("Automatic", "Manual", "CVT"));
        fieldTransmission.setStyle("-fx-font-size: 20px; -fx-alignment: CENTER-RIGHT;");
        fieldTransmission.setPrefWidth(350);
        ComboBox<String> fieldAspiration = new ComboBox<>(FXCollections.observableArrayList("Naturally Aspirated", "Turbocharged", "Supercharged"));
        fieldAspiration.setStyle("-fx-font-size: 20px; -fx-alignment: CENTER-RIGHT;");
        fieldAspiration.setPrefWidth(350);

        // Creates the labels for the fields for a diesel car
        Label lbTowingCap = new Label("Towing Capacity:");
        lbTowingCap.setFont(lbFont);
        Label lbTorque = new Label("Torque:");
        lbTorque.setFont(lbFont);

        // Creates the fields for a diesel car
        TextField fieldTowingCap = new TextField();
        fieldTowingCap.setFont(textBoxFont);
        TextField fieldTorque = new TextField();
        fieldTorque.setFont(textBoxFont);

        // Creates the labels for the fields for a hybrid car
        Label lbHybridType = new Label("Hybrid Type:");
        lbHybridType.setFont(lbFont);
        Label lbElectricRange = new Label("Electric Range:");
        lbElectricRange.setFont(lbFont);

        // Creates the fields for a hybrid car
        ComboBox<String> fieldHybridType = new ComboBox<>(FXCollections.observableArrayList("Parallel", "Series", "Plug-In"));
        fieldHybridType.setStyle("-fx-font-size: 20px; -fx-alignment: CENTER-RIGHT;");
        fieldHybridType.setPrefWidth(350);
        TextField fieldElectricRange = new TextField();
        fieldElectricRange.setFont(textBoxFont);

        // Adds the fields for an electric car and sets it if it is an electric car
        if (car.getClass().getSimpleName().equals("ElectricCar")) {

            // Casts the Car to ElectricCar to use the methods defined in ElectricCar
            ElectricCar eCar = (ElectricCar) car;

            // Adds the labels for the electric car fields
            gpCar.addColumn(0, lbRange, lbMotors, lbLvl3Charging);

            // Sets the values for the fields
            fieldRange.setText(eCar.getRange() + "");
            fieldMotors.setText(eCar.getNumOfMotors() + "");
            sliderMotors.setValue(eCar.getNumOfMotors());
            if (eCar.isLvl3ChargingCapable()) {
                lvl3Charging.selectToggle(rbTrue);
            } else {
                lvl3Charging.selectToggle(rbFalse);
            }
            gpCar.addColumn(1, fieldRange, fieldMotors, hBoxLvl3Charging);
        }

        // Adds the fields for a gas car and sets it if it is a gas car or one of its subtypes
        if (car.getClass().getSimpleName().equals("GasCar") || car.getClass().getSimpleName().equals("DieselCar") ||
                car.getClass().getSimpleName().equals("HybridCar")) {

            // Casts the Car to GasCar to use the methods defined in GasCar
            GasCar gCar = (GasCar) car;

            // Adds the labels for the gas car fields
            gpCar.addColumn(0, lbMPG, lbCylinders, lbTransmission, lbAspiration);

            // Sets the values for the fields
            fieldMPG.setText(gCar.getMpg() + "");
            sliderMPG.setValue(gCar.getMpg());
            fieldCylinders.setText(gCar.getCylinders() + "");
            sliderCylinders.setValue(gCar.getCylinders());
            fieldTransmission.setValue(gCar.getTransmission());
            fieldAspiration.setValue(gCar.getAspiration());
            gpCar.addColumn(1, fieldMPG, fieldCylinders, fieldTransmission, fieldAspiration);
        }

        // Adds the fields for a diesel car and sets it if it is a diesel car
        if (car.getClass().getSimpleName().equals("DieselCar")) {

            // Casts the Car to DieselCar to use the methods defined in DieselCar
            DieselCar dCar = (DieselCar) car;

            // Adds the labels for the diesel car fields
            gpCar.addColumn(0, lbTowingCap, lbTorque);

            // Sets the values for the fields
            fieldTowingCap.setText(dCar.getTowingCapacity() + "");
            fieldTorque.setText(dCar.getTorque() + "");
            gpCar.addColumn(1, fieldTowingCap, fieldTorque);
        }

        // Adds the fields for a hybrid car and sets it if it is a hybrid car
        if (car.getClass().getSimpleName().equals("HybridCar")) {

            // Casts the Car to HybridCar to use the methods defined in HybridCar
            HybridCar hCar = (HybridCar) car;

            // Adds the labels for the hybrid car fields
            gpCar.addColumn(0, lbHybridType, lbElectricRange);

            // Sets the values for the fields
            fieldHybridType.setValue(hCar.getHybridType());
            fieldElectricRange.setText(hCar.getElectricRange() + "");
            gpCar.addColumn(1, fieldHybridType, fieldElectricRange);
        }

        // Creates the buttons for saving the car or exiting
        Button btSave = new Button("Save");
        btSave.setFont(lbFont);
        Button btCancel = new Button("Cancel");
        btCancel.setFont(lbFont);
        HBox hBoxConfirmation = new HBox(5, btSave, btCancel);
        hBoxConfirmation.setAlignment(Pos.CENTER_RIGHT);
        gpCar.addColumn(1, hBoxConfirmation);

        // Creates the Car object if the fields are valid and the user confirms
        btSave.setOnAction(e -> {

            // Tries to edit the Car object
            try {
                // Counts the number of empty fields
                int empty = 0;

                // Adds the number of empty fields for all car types
                if (fieldMake.getText().isEmpty()) {
                    empty++;
                }
                if (fieldModel.getText().isEmpty()) {
                    empty++;
                }
                if (fieldDrivetrain.getValue() == null) {
                    empty++;
                }
                if (fieldPrice.getText().isEmpty())
                    empty++;
                if (fieldMileage.getText().isEmpty())
                    empty++;
                if (fieldURL.getText().isEmpty())
                    empty++;

                // Tests to see if it is editing an electric car
                if (car.getClass().getSimpleName().equals("ElectricCar")) {
                    // Adds the number of empty fields for an electric car
                    if (fieldRange.getText().isEmpty())
                        empty++;
                    if (lvl3Charging.getSelectedToggle() == null)
                        empty++;
                }

                // Tests to see if it is editing a gas car or one of its subtypes
                if (car.getClass().getSimpleName().equals("GasCar") || car.getClass().getSimpleName().equals("DieselCar") ||
                        car.getClass().getSimpleName().equals("HybridCar")) {
                    // Adds the number of empty fields for a gas car
                    if (fieldTransmission.getValue() == null)
                        empty++;
                    if (fieldAspiration.getValue() == null)
                        empty++;
                }

                // Tests to see if it is editing a diesel car
                if (car.getClass().getSimpleName().equals("DieselCar")) {
                    // Adds the number of empty fields for a diesel car
                    if (fieldTowingCap.getText().isEmpty())
                        empty++;
                    if (fieldTorque.getText().isEmpty())
                        empty++;
                }

                // Tests to see if it is editing a hybrid car
                if (car.getClass().getSimpleName().equals("HybridCar")) {
                    // Adds the number of empty fields for a hybrid car
                    if (fieldHybridType.getValue() == null)
                        empty++;
                    if (fieldElectricRange.getText().isEmpty())
                        empty++;
                }

                // Throws an exception if there are any empty fields
                if (empty != 0)
                    throw new EmptyFieldsException(empty);

                // Edits the general Car fields
                car.setMake(fieldMake.getText());
                car.setModel(fieldModel.getText());
                car.setYear((int)sliderYear.getValue());
                car.setDoors((int)sliderDoors.getValue());
                car.setDrivetrain(fieldDrivetrain.getValue());
                car.setPrice(Integer.parseInt(fieldPrice.getText()));
                car.setMileage(Integer.parseInt(fieldMileage.getText()));
                car.setPicture(new Image(fieldURL.getText()));

                // Tests to see if it is editing an electric car
                if (car.getClass().getSimpleName().equals("ElectricCar")) {
                    // Casts the Car to ElectricCar to use the methods defined in ElectricCar
                    ElectricCar eCar = (ElectricCar) car;

                    // Edits the ElectricCar fields
                    eCar.setRange(Integer.parseInt(fieldRange.getText()));
                    eCar.setNumOfMotors((int)sliderMotors.getValue());
                    eCar.setLvl3ChargingCapability(rbTrue.isSelected());
                }

                // Tests to see if it is editing a gas car or one of its subclasses
                if (car.getClass().getSimpleName().equals("GasCar") || car.getClass().getSimpleName().equals("DieselCar") ||
                        car.getClass().getSimpleName().equals("HybridCar")){
                    // Casts the Car to GasCar to use the methods defined in GasCar
                    GasCar gCar = (GasCar) car;

                    // Edits the GasCar fields
                    gCar.setMpg((int)sliderMPG.getValue());
                    gCar.setCylinders((int)sliderCylinders.getValue());
                    gCar.setTransmission(fieldTransmission.getValue());
                    gCar.setAspiration(fieldAspiration.getValue());
                }

                // Tests to see if it is editing a diesel car
                if (car.getClass().getSimpleName().equals("DieselCar")){
                    // Casts the Car to DieselCar to use the methods defined in DieselCar
                    DieselCar dCar = (DieselCar) car;

                    // Edits the DieselCar fields
                    dCar.setTowingCapacity(Integer.parseInt(fieldTowingCap.getText()));
                    dCar.setTorque(Integer.parseInt(fieldTorque.getText()));
                }

                // Tests to see if it is editing a hybrid car
                if (car.getClass().getSimpleName().equals("HybridCar")){
                    // Casts the Car to HybridCar to use the methods defined in HybridCar
                    HybridCar hCar = (HybridCar) car;

                    // Edits the HybridCar fields
                    hCar.setHybridType(fieldHybridType.getValue());
                    hCar.setElectricRange(Integer.parseInt(fieldElectricRange.getText()));
                }

                // Closes stage when done
                stage.close();

                // Updates the display, saves the carList, and lets the user know it was successful
                update();
                saveCarList();
                displayMessageWithOk(car.getYear() + " " + car.getMake() + " " + car.getModel() + " has been updated");

            } catch (EmptyFieldsException E) {
                // Lets the user know how many empty fields there are
                displayMessageWithOk(E.getMessage());
            } catch (NumberFormatException E) {
                // Lets the user know that one of the fields they entered is the wrong data type
                displayMessageWithOk("Invalid information in one of the fields. \nPlease make sure things like 'Price' are entered as a number.");
            } catch (IllegalArgumentException E) {
                // Lets the user know that the image URL they entered was bad
                displayMessageWithOk("Invalid image URL. \nPlease make sure you include 'https://'.");
            }
        });

        // Closes the stage when the cancel button is pressed
        btCancel.setOnAction(e -> {
            // Closes the stage
            stage.close();
        });

        // Sets the scene and shows the add stage
        stage.setScene(new Scene(vBox));
        stage.show();
    }

    // Displays a description of the program
    public static void displayAbout() {

        // Creates the stage
        Stage stage = new Stage();
        stage.setTitle("About");

        // Creates a vbox and scroll pane for displaying the about
        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPadding(new Insets(5));

        // Creates a title
        Label title = new Label("Car Catalog");
        title.setPadding(new Insets(5));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        title.prefWidthProperty().bind(scrollPane.widthProperty().subtract(30));
        title.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white;");
        title.setAlignment(Pos.CENTER);
        vBox.getChildren().add(title);

        // Adds the description from help.about()
        Label about = new Label(Help.about());
        about.setFont(Font.font(20));
        vBox.getChildren().add(about);

        // Sets the scene and displays the stage
        stage.setScene(new Scene(scrollPane));
        stage.show();
    }

    // Displays relevant glossary terms
    public static void displayGlossary() {

        // Creates the stage
        Stage stage = new Stage();
        stage.setTitle("Glossary");

        // Creates a vbox and scroll pane for displaying the glossary
        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPadding(new Insets(5));

        // Creates a title
        Label title = new Label("Glossary");
        title.setPadding(new Insets(5));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        title.prefWidthProperty().bind(scrollPane.widthProperty().subtract(45));
        title.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white;");
        title.setAlignment(Pos.CENTER);
        vBox.getChildren().add(title);

        // Adds the glossary from help.glossary()
        Label glossary = new Label(Help.glossary());
        glossary.setFont(Font.font(20));
        vBox.getChildren().add(glossary);

        // Sets the scene and displays the stage
        stage.setScene(new Scene(scrollPane, 1365, 1000));
        stage.show();
    }

    // Displays a message to the user
    public static void displayMessageWithOk(String message) {

        // Creates a stage that has to be dealt with
        Stage stage = new Stage();
        stage.setTitle(message);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Creates the message label
        Label msg = new Label(message);
        msg.setFont(Font.font(20));

        // Creates the okay button
        Button btOk = new Button("OK");
        btOk.setFont(Font.font(20));
        btOk.setOnAction(E -> {
            // Gets the stage to close since the user must deal with the message
            stage.close();
        });

        // Displays the message and button
        HBox pane = new HBox(10, msg, btOk);
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setPadding(new Insets(20));
        stage.setScene(new Scene(pane));
        stage.show();
    }

    // Reads the objects from the text file
    public static void readCarList()  {

        // Tries to read the list
        try {
            // Creates a scanner for the file
            File carsIn = new File("src/carList.txt");
            String lineIn;
            String[] fields;
            Scanner carsFile = new Scanner(carsIn);

            // Goes through the entire file
            while (carsFile.hasNext()) {

                // Gets the fields
                lineIn = carsFile.nextLine();
                fields = lineIn.split("\t");

                // Tests the first field to determine the class type
                switch (fields[0]) {
                    case "ElectricCar":
                        // Creates an ElectricCar object
                        carList.add(new ElectricCar(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]),
                                fields[5], Integer.parseInt(fields[6]), Integer.parseInt(fields[7]), new Image(fields[8]), Integer.parseInt(fields[9]),
                                Integer.parseInt(fields[10]), Boolean.parseBoolean(fields[11])));
                        break;
                    case "GasCar":
                        // Creates a GasCar object
                        carList.add(new GasCar(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]),
                                fields[5], Integer.parseInt(fields[6]), Integer.parseInt(fields[7]), new Image(fields[8]), Integer.parseInt(fields[9]),
                                Integer.parseInt(fields[10]), fields[11], fields[12]));
                        break;
                    case "DieselCar":
                        // Creates a DieselCar object
                        carList.add(new DieselCar(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]),
                                fields[5], Integer.parseInt(fields[6]), Integer.parseInt(fields[7]), new Image(fields[8]), Integer.parseInt(fields[9]),
                                Integer.parseInt(fields[10]), fields[11], fields[12], Integer.parseInt(fields[13]), Integer.parseInt(fields[14])));
                        break;
                    case "HybridCar":
                        // Creates a HybridCar object
                        carList.add(new HybridCar(fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]),
                                fields[5], Integer.parseInt(fields[6]), Integer.parseInt(fields[7]), new Image(fields[8]), Integer.parseInt(fields[9]),
                                Integer.parseInt(fields[10]), fields[11], fields[12], fields[13], Integer.parseInt(fields[14])));
                        break;
                }
            }
            // Closes the file when done
            carsFile.close();

        } catch (FileNotFoundException E) {
            // Lets the user know something went wrong and the file could not be located
            displayMessageWithOk("Car list file was not found");
        }
    }

    // Saves the carList to the text file
    public static void saveCarList() {

        // Tries to save the list
        try {
            // Creates a PrintWriter for the file
            File carsOut = new File("src/carList.txt");
            PrintWriter carsFile = new PrintWriter(carsOut);
            Car item;

            // Goes through each car in the list
            for (Car car : carList) {

                // Writes the car to the file
                item = car;
                carsFile.println(item.getClass().getSimpleName() + '\t' + item.fields());
            }

            // Closes the file when done
            carsFile.close();
        } catch (FileNotFoundException E) {
            // Lets the user know something went wrong and the file could not be located
            displayMessageWithOk("Car list file was not found");
        }
    }

    // Adds a car to the lists and display
    public static void add(Car car) {

        // Adds the car to the lists and saves carList
        carList.add(car);
        saveCarList();
        displayList.add(car);

        // Updates the display and lets the user know it was successful
        update();
        displayMessageWithOk(car.getYear() + " " + car.getMake() + " " + car.getModel() + " has been added");
    }

    // Removes a car from the lists and display
    public static void remove(Car car) {

        // Removes the car from the lists and saves carList
        carList.remove(car);
        saveCarList();
        displayList.remove(car);

        // Updates the display and lets the user know it was successful
        update();
        displayMessageWithOk(car.getYear() + " " + car.getMake() + " " + car.getModel() + " has been deleted");
    }

    // Updates the display
    public static void update() {

        // Clears the cars being displayed
        vboxCarPane.getChildren().clear();

        // Goes through the cars in displayList
        for (Car car: displayList) {

            // Gets the carPane
            HBox carPane = carPane(car);
            carPane.prefWidthProperty().bind(scrollPaneOfCarPanes.widthProperty().subtract(100));

            // Creates the delete button
            ImageView btDelete = new ImageView(trashCanPicture);
            btDelete.setFitWidth(25);
            btDelete.setFitHeight(25);
            btDelete.setOnMouseClicked(E -> {
                // Goes through the deletion process of the car
                displayDelete(car);
            });

            // Creates the edit button
            ImageView btEdit = new ImageView(editPicture);
            btEdit.setFitWidth(25);
            btEdit.setFitHeight(25);
            btEdit.setOnMouseClicked(E -> {
                // Goes through the edit process of the car
                displayEdit(car);
            });

            // Creates a VBox for the buttons
            VBox buttons = new VBox(5, btEdit, btDelete);
            buttons.setAlignment(Pos.CENTER);

            // Creates the HBox for the car
            HBox hBox = new HBox(20);
            hBox.getChildren().addAll(carPane, buttons);
            hBox.setAlignment(Pos.CENTER_LEFT);

            // Adds the car to the VBox
            vboxCarPane.getChildren().add(hBox);
        }
    }

    // Adds a car type to display list
    public static void addToDisplayList(String carClass) {

        // Goes through the list of stored cars
        for (Car car: carList) {

            // Tests to see if the car is the right type
            if (car.getClass().getSimpleName().equals(carClass)) {

                // Adds the car to displayList
                displayList.add(car);
            }
        }
    }

    // Sorts the cars in displayList
    public static void sortDisplayList() {

        // Sorts as long as there is an option selected
        if (sortSelect.getValue() != null) {

            // Creates the needed value, depending on the selected option, it might be sorting high to low or low to high
            Car currentMinOrMax;
            int currentMinOrMaxIndex;
            boolean needsSwap;

            // Cycles through each element in the list, after each iteration one new index is locked in the right position
            for (int i = 0; i < displayList.size() - 1; i++) {
                currentMinOrMax = displayList.get(i);
                currentMinOrMaxIndex = i;

                // Cycles through each element in the list, it starts one index up since that first element is assigned to currentMinOrMax
                for (int j = i + 1; j < displayList.size(); j++) {
                    needsSwap = false;

                    // Makes the comparison based on the sort selected
                    switch (sortSelect.getValue()) {
                        case "Make":
                            // Compares make name low to high
                            needsSwap = currentMinOrMax.getMake().compareTo(displayList.get(j).getMake()) > 0;
                            break;
                        case "Model":
                            // Compares model name low to high
                            needsSwap = currentMinOrMax.getModel().compareTo(displayList.get(j).getModel()) > 0;
                            break;
                        case "Lowest Model Year":
                            // Compares model year low to high
                            needsSwap = currentMinOrMax.getYear() > displayList.get(j).getYear();
                            break;
                        case "Highest Model Year":
                            // Compares model year high to low
                            needsSwap = currentMinOrMax.getYear() < displayList.get(j).getYear();
                            break;
                        case "Lowest Price":
                            // Compares price low to high
                            needsSwap = currentMinOrMax.getPrice() > displayList.get(j).getPrice();
                            break;
                        case "Highest Price":
                            // Compares price high to low
                            needsSwap = currentMinOrMax.getPrice() < displayList.get(j).getPrice();
                            break;
                        case "Lowest Mileage":
                            // Compares mileage low to high
                            needsSwap = currentMinOrMax.getMileage() > displayList.get(j).getMileage();
                            break;
                        case "Highest Mileage":
                            // Compares mileage high to low
                            needsSwap = currentMinOrMax.getMileage() < displayList.get(j).getMileage();
                            break;
                    }

                    if (needsSwap) {
                        currentMinOrMax = displayList.get(j);
                        currentMinOrMaxIndex = j;
                    }
                }
                if (currentMinOrMaxIndex != i) {
                    displayList.set(currentMinOrMaxIndex, displayList.get(i));
                    displayList.set(i, currentMinOrMax);
                }
            }
        }
        update();
    }

    // Helper method
    public static String formatWithCommas(int number) {

        // Passes the string value of the number to the other method
        return formatWithCommas(Integer.toString(number));
    }

    // Formats numbers to have commas every three numbers like 1,000,000
    public static String formatWithCommas(String number) {

        // Gets the string length
        int len = number.length();

        // Tests to see if it doesn't need formatting (base case)
        if (len <= 3) {
            // Returns the number
            return number;
        }

        // Calls itself recursively and adds a comma
        return formatWithCommas(number.substring(0, len-3)) + "," + number.substring(len-3);
    }
}

// Creates an exception for having empty fields
class EmptyFieldsException extends Exception {
    private final int emptyFields;

    public EmptyFieldsException(int emptyFields) {

        // Calls the superclass' constructor, passing it the message with the number of empty fields
        super("There " + (emptyFields == 1? "is " : "are ") + emptyFields + " empty field" + (emptyFields == 1? "" : "s"));

        // Initializes emptyFields
        this.emptyFields = emptyFields;
    }

    public int getEmptyFields() {
        return emptyFields;
    }
}