/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 This class represents the Store's inventory of parts and products. Its methods are designed to add to, remove from, update, and search through its inventory.
 For a class this important, this was easy and relatively quick to code.
  @author Alex Beno
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int nextIndexPart = 1;
    private static int nextIndexProduct = 1;
    
    /**
     This method adds a part to the list of parts in the Inventory.
     @param part The part to be added.
     */
    public static void addPart(Part part){
        allParts.add(part);
    }
    
    /**
     This method adds a product to the list of products in the Inventory.
     @param product The product to be added.
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    
    /**
     This method returns the part associated with a particular ID. It iterates through the list of all the parts looking for a part that matches that ID.
     If it finds it, it returns that part. If it cannot find it, it returns null.
     @param partID The part ID to be searched for
     @return The part associated with partID if found, null if not
     */
    public static Part lookUpPart(int partID){
        int count = 0;
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == partID){
                return part;
            }
        }
        return null;
    }
    /**LOGICAL ERROR 
     This method returns the list of parts whose names contain a particular String. It iterates through the list of parts looking for those that match. It adds them to a new list, then returns that list.
     A logical error I ran into occurred while writing this method. Initially I was searching in a case-sensitive manner. But often when people are searching, they will search in lowercase
     even if there might be some part of their desired result that is in uppercase. So I made sure to convert both the names of both the part you are looking for and the parts you are comparing to to lowercase
     so that you don't have to worry about cases when searching.
     @param partName A portion of the name of the part(s) you are looking for.
     @return The list of parts whose names contain the name you were looking for.
     */
    public static ObservableList<Part> lookUpPart(String partName){
        ObservableList<Part> sublist = FXCollections.observableArrayList();
        for(Part part : Inventory.getAllParts()){
            if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                
                sublist.add(part);
            }
        }
        return sublist;
    }
    /**
     This method returns the product associated with a particular ID. It iterates through the list of all the products looking for a product that matches that ID.
     If it finds it, it returns that product. If it cannot find it, it returns null.
     @param productID The product ID to be searched for
     @return The product associated with productID if found, null if not
     */
    public static Product lookUpProduct(int productID){
        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == productID){
                return product;
            }
        }
        return null;
    }
    /**
     This method returns the list of products whose names contain a particular String. It iterates through the list of products looking for those that match. It adds them to a new list, then returns that list.
     
     @param productName The product name to be searched for
     @return The list of products containing that name
     */
    public static ObservableList<Product> lookUpProduct(String productName){
        ObservableList<Product> sublist = FXCollections.observableArrayList();
        for(Product product : Inventory.getAllProducts()){
            if(product.getName().toLowerCase().contains(productName.toLowerCase())){
                sublist.add(product);
            }
        }
        return sublist;
    }
    
    /**
     This method replaces a part at a particular index on the list of parts with a new part.
     @param index The index of the part to be replaced
     @param selectedPart The part that will replace the old one
     */
    public static void updatePart(int index, Part selectedPart){
        Inventory.getAllParts().set(index, selectedPart);
    }
    /**
     This method replaces a product at a particular index on the list of product with a new product.
     @param index The index of the product to be replaced
     @param selectedProduct The product that will replace the old one
     */
    public static void updateProduct(int index, Product selectedProduct){
        Inventory.getAllProducts().set(index, selectedProduct);
    }
    /**
     FUTURE ENHANCEMENT
     This method deletes a part from the list of parts.
     The program requirements state that a product cannot be deleted when it has associated parts. One future enhancement to the program would be to make it so a part
      cannot be deleted while it is associated with products, as the parts are used to make the products they are associated with. You wouldn't want
      a part in an associated parts list that is no longer in the inventory's list of parts.
     @param selectedPart The part to be deleted
     @return Returns true if deletion success, false if failure
     */
    public static boolean deletePart(Part selectedPart){
        return Inventory.getAllParts().remove(selectedPart);
    }
    /**
     This method deletes a product from the list of product.
     @param selectedProduct The product to be deleted
     @return Returns true if deletion success, false if failure
     */
    public static boolean deleteProduct(Product selectedProduct){
        return Inventory.getAllProducts().remove(selectedProduct);
    }
    /**
     This method returns the list of parts in the Inventory.
     @return The list of parts in the Inventory
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /**
     This method returns the list of products in the Inventory.
     @return The list of products in the Inventory
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    /**
     This method returns the ID of a new part to be added, after incrementing the ID from the last part. This method is necessary to keep track of the
     part IDs used, as even after a part is deleted its ID cannot be reused. Incrementing this after each part allows every ID to be unique and used only once.
     @return The ID of the next part to be created.
     */
    public static int getNextPartID(){
        int a = nextIndexPart;
        nextIndexPart++;
        return a;
    }
    /**
    This method undoes getNextPartID()'s incrementing of the ID of the next part, as that part's creation was canceled. 
     */
    public static void partCanceled(){
        nextIndexPart--;
    }
    /**
     This method returns the ID of a new product to be added, after incrementing the ID from the last product. This method is necessary to keep track of the
     product IDs used, as even after a product is deleted its ID cannot be reused. Incrementing this after each product allows every ID to be unique and used only once.
     @return The ID of the next product to be created.
     */
    public static int getNextProductID(){
        int a = nextIndexProduct;
        nextIndexProduct++;
        return a;
    }
    /**
    This method undoes getNextProductID()'s incrementing of the ID of the next product, as that product's creation was canceled. 
     */
    public static void productCanceled(){
        nextIndexProduct--;
    }
}
