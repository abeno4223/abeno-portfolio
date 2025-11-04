/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package alexbenoc482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
  This class initializes the Inventory Management System. Not much time was spent on this part of the project. 
  It just creates some starter parts and products with which to populate the initial tableviews.
  
  @author Alex Beno
 */
public class AlexBenoC482 extends Application {

    /**
      This method is the main method, it sets up the program. It does so by creating some starter parts and products to fill the initial tableviews.
      The JavaDoc folder is "Alex Beno C482 Final Project.zip/javadoc"
      @param args the command line arguments
     */
    public static void main(String[] args) {
        InHouse wheel = new InHouse(Inventory.getNextPartID(), "Wheel", 9.99, 14, 2, 20, 3);
        InHouse seat = new InHouse(Inventory.getNextPartID(), "Seat", 15.99, 3, 1, 10, 1);
        Outsourced bell = new Outsourced(Inventory.getNextPartID(), "Bell", 5.99, 5, 1, 10, "Bells Incorporated");
        Product bike1 = new Product(Inventory.getNextProductID(), "Mark V", 54.99, 3, 1, 5);
        Product bike2 = new Product(Inventory.getNextProductID(), "Mark VI", 79.99, 4, 1, 5);
        bike1.addAssociatedPart(seat);
        bike1.addAssociatedPart(wheel);
        bike1.addAssociatedPart(wheel);
        bike2.addAssociatedPart(seat);
        bike2.addAssociatedPart(wheel);
        bike2.addAssociatedPart(wheel);
        bike2.addAssociatedPart(bell);
        
        Inventory.addPart(wheel);
        Inventory.addPart(seat);
        Inventory.addPart(bell);
        Inventory.addProduct(bike1);
        Inventory.addProduct(bike2);
        
        launch(args);
    }

    /**
        This method starts the application. It sets the scene, and loads up the main form, as well as setting the title to Inventory Management System.
        @param stage The stage where the main form's scene will be set.
    */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }
    
}
