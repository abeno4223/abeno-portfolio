/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 This class stores information about Products for use in the Inventory Management System. This is fairly similar to Part, except Products have a list of
 associated parts in place of a machineID/companyName.
 
 * @author Alex Beno
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    /**
    This method constructs a new Product.
    @param id The ID of the product
    @param name The Name of the product
    @param price The price/cost of the product
    @param stock The inventory level of the product
    @param min The minimum inventory level of the product 
   @param max The maximum inventory level of the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     This method sets the ID of the product.
     @param id The number that this product should have its ID set to
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     This method sets the name of the product.
     @param name The name that this product should have its name set to.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     This method sets the price/cost of the product.
     @param price The name that this product should have its price/cost set to.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     This method sets the inventory level of the product.
     @param stock The inventory level that this product should have its inventory level set to.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     This method sets the min inventory level of the product.
     @param min The min inventory level that this product should have its min inventory level set to.
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     This method sets the max of the product.
     @param max The max inventory level that this product should have its max inventory level set to.
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     This method returns the ID of the product.
     @return Returns the Id of the product
     */
    public int getId() {
        return id;
    }
    /**
     This method returns the Name of the product.
     @return Returns the Name of the product
     */
    public String getName() {
        return name;
    }

    
/**
     This method returns the Price of the product.
     @return Returns the Price of the product
     */
    public double getPrice() {
        return price;
    }

    
/**
     This method returns the Inventory Level of the product.
     @return Returns the inventory level of the product
     */
    public int getStock() {
        return stock;
    }

    
/**
     This method returns the min inventory level of the product.
     @return Returns the max inventory level of the product
     */
    public int getMin() {
        return min;
    }

    
/**
     This method returns the max inventory level of the product.
     @return Returns the min inventory level of the product
     */
    public int getMax() {
        return max;
    }
    /**
     This method adds a part to the product's list of associated parts.
     @param part The part to be added to the list of associated parts.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    /**
     This method deletes a given part from the product's list of associated parts.
     @param selectedPart The part to be removed.
     @return True for deletion success, false for deletion failure.
     */
    public boolean deleteAssociatedPart(Part selectedPart){
        for(Part p : associatedParts){
            if(p.getId() == selectedPart.getId()){
                associatedParts.remove(selectedPart);
                return true;
            }
        }
        return false;
    }
    
    /**
     This method returns the list of of the product's associated parts.
     * @return An observablelist of all of the product's associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

    
    

   
    
}
