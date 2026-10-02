Car Catalog

A Java desktop application for building and browsing a catalog of vehicles. Add cars, edit or delete them, filter by fuel type, and sort the list eight different ways. Built with Java and JavaFX as a course project for CMIS 201 (Spring 2024).

Demo

Video walkthrough: Watch on YouTube

https://www.youtube.com/watch?v=3e3f-_PN7fY

Features
Add, edit, and delete cars through dedicated windows. Adding or deleting a car shows a confirmation dialog with the car's full details first.
Adaptive add form. Choosing Electric or Gas (then Regular, Diesel, or Hybrid) swaps in only the fields that apply to that type.
Detail view. Click any car in the list to open a window with its overview, body and chassis, and powertrain specs.
Filter by fuel type with Electric, Gas, Hybrid, and Diesel checkboxes.
Sort by make, model, model year (lowest or highest), price (lowest or highest), or mileage (lowest or highest).
Input validation. Empty fields, non-numeric values, and bad image URLs are caught and explained to the user instead of crashing the app.
Persistent storage. The catalog is saved to a text file on every change and reloaded on startup.
About and Glossary pages that explain the app and define terms such as aspiration type, drivetrain, torque, and Level 3 charging.

Class Design
The project uses inheritance to model the four vehicle types. Car is abstract and holds the attributes every vehicle shares.

Car (abstract)
├── ElectricCar
└── GasCar
    ├── DieselCar
    └── HybridCar
Class	Adds
Car	make, model, year, doors, drivetrain, price, mileage, picture
ElectricCar	range, number of motors, Level 3 charging capability
GasCar	MPG, cylinders, transmission, aspiration
DieselCar	towing capacity, torque
HybridCar	hybrid type, electric range

Data File Format

Each line in carList.txt is one car, with fields separated by tabs. The first field is the class name, followed by the shared fields, then the fields specific to that type.

Author:
Douglas Agnes CMIS 201, Spring 2024
