// **********************************************************************************
// Title: Car Catalog
// Author: Douglas Agnes
// Course Section: CMIS201-ONL1 (Seidel) Spring 2024
// File: Help.java
// Description: Stores useful information
// **********************************************************************************
package com.example.mpapart4carcatalogagnesdouglas;

public class Help {
    public static String about() {
        String description = """
                This software is a system that is designed to manage and display information about various cars. 
                The software allows users to enter information about a car and it gets stored for later use. It 
                lets users browse and obtain information about different car models they have previously inputted, 
                including specifications, features, and quantitative data. The system allows users to sort, edit, 
                or delete the cars in the list. 
                """;

        return description;
    }
    public static String glossary() {
        String description = """
                •	Aspiration type
                    An engine’s aspiration type is basically how it “breathes”. There are three main types:
                    1)  Naturally Aspirated - engines that take in air under normal means at normal atmospheric pressures. As the piston in the engine goes down, 
                        it creates an area of low pressure in the cylinder which pulls air into the engine.
                    2)  Supercharged – is a form of forced induction. A compressor attached to the engine is powered by the engine via a belt. The compressor 
                        forces more air into the engine.
                    3)  Turbocharged – is another form of forced induction. They use the pressure from the exhaust gases exiting the combustion chamber to power 
                        their compressor. The compressor forces more air into the engine.
                        
                •	Cylinder
                    A cylinder is a chamber in the engine where fuel is combusted. The cylinder consists of a piston and two valves. The piston moves up and down, 
                    and its motion from the combustion generates power that moves the car. When referring to a car being a four cylinder, it means there are four 
                    cylinders inside of the engine.
                    
                •	Drivetrain
                    A car’s drivetrain connects the engine to the wheels enabling the vehicle to move. When someone is talking about the kind of drivetrain a car 
                    has, they are referring to what wheels the power is sent to. A front wheel drive sends power to the two front wheels, all wheel drive sends 
                    power to all wheels, and rear wheel drive sends power to the two rear wheels.
                    
                •	Electric Range
                    The electric range of a hybrid or just the range of an electric car refers to the distance a car can travel using only electric power from the 
                    battery, starting with a full battery.
                    
                •	Hybrid types
                    Hybrid cars use a combination of a gasoline engine and electric motors to power the car. There are three main types of hybrid systems:
                    1)  Parallel Hybrids - both an engine and an electric motor can power the wheels, together or separately. The battery is charged from a mix of 
                        engine power, and regenerative breaking.
                    2)  Series Hybrids – a gasoline engine is used to power the battery pack like a generator and only the electric motors power the wheels.
                    3)  Plug-In Hybrids – very similar to a parallel hybrid, but they can be plugged in and charged from the grid. They typically have a larger 
                        battery.
                    Most if not all hybrids have regenerative breaking where they recapture as much as 30 percent of the energy expended to power the car by using 
                    a motor as a generator when the car slows to recharge the battery.
                    
                •	Level three charging
                    Not all chargers for electric vehicles are created equal. There are three tiers of chargers that vary based on power output and charging speed:
                    1)  Level 1 - operates at a standard 120 volts, utilizing the typical wall outlet. This level offers the slowest charging rate, needing tens of 
                        hours to completely charge a fully electric vehicle.
                    2)  Level 2 - prevalent in homes and garages, employs the standard EV plug. Most public charging stations operate at this level. These can 
                        completely charge a fully electric vehicle in around 12 hours.
                    3)  Level 3 - alternatively referred to as DCFC or DC Fast Chargers. These stations provide the fastest charging option available. These can 
                        completely charge a fully electric vehicle in around 1-2 hours.
                    While almost every EV can use level one and two chargers, not all electric vehicles are compatible with the higher power levels of level 3 
                    chargers.
                    
                •	Make/Model
                    A car make refers to the manufacturer or company that makes the vehicle, while the model represents the specific version or design.
                    
                •	Torque
                    Torque is a twisting force; it describes how many pounds of force are provided if the force is applied with a 1-foot lever. In cars, torque 
                    measures the amount of force an engine applies to complete a task. The more torque a car has, the easier it is for it to move a heavier load.
                    
                •	Transmission
                    A car’s transmission is its gearbox. It sends the power from the engine to the rest of the drivetrain. Smaller gears are used for better 
                    acceleration, larger gears are used for a higher speed without straining the engine. An automatic transmission changes through gears using a 
                    computer. With a manual transmission, the driver must manually change the gears. A CVT transmission can seamlessly change through a continuous 
                    range of gear ratios.
                """;

        return description;
    }
}
