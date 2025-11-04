/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 This class stores info about parts that are made In-House. It extends Part, meaning most methods are already defined. Only a select few
 need to be worried about here.
 
 @author Alex Beno
 */
public class InHouse extends Part {
    
    private int machineID;
    
    /**
     This method constructs a new In House Part. This differs from Part due to it requiring a machineID.
     @param id The ID of the new part
     @param name The name of the new part
     @param price The price of the new part
     @param stock The inventory level of the new part
     @param min The minimum inventory level of the new part.
     @param max The maximum inventory level of the new part.
     @param machineID The machine ID of the machine on which the new part was made.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    

    /**
     This method sets the machine ID of an In-House part.
     @param machineID The ID that the Machine ID should be set to.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    
    /**
     This method returns the machine ID of this part.
     @return Returns the machine ID of this part.
     */
    public int getMachineID() {
        return machineID;
    }
    
}
