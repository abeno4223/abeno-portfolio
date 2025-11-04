/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 This class stores info about parts that are Outsourced. It extends Part, meaning most methods are already defined. Only a select few
 need to be worried about here.
 @author Alex Beno
 */
public class Outsourced extends Part {
    private String companyName;
    /**
     This method constructs a new Outsourced Part. This differs from Part due to it requiring a companyName.
     @param id The ID of the new part
     @param name The name of the new part
     @param price The price of the new part
     @param stock The inventory level of the new part
     @param min The minimum inventory level of the new part.
     @param max The maximum inventory level of the new part.
     @param companyName The name of the company who made the new part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    

    /**
     This method sets the company name of an Outsourced part.
     @param companyName The name of the company who made the part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    /**
     This method returns the company name of this part.
     @return The name of the company who made this part
     */
    public String getCompanyName() {
        return companyName;
    }
}
