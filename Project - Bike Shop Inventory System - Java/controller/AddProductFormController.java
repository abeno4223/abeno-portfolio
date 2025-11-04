/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
  This is the FXML Controller Class for the Add Product Form of the Inventory Management System. The main purpose of the methods here are to read user data, make sure it fits, and then add it as a part to the main form's product's list.
 
  This ended up being relatively easy compared to the Add Part Form - most of the logic in this could be repurposed from either there or the main form's tableviews.
  @author Alex Beno
 */
public class AddProductFormController implements Initializable {

    @FXML
    private Button addAssociatedPartBtn;

    @FXML
    private TableView<Part> addProductAllPartTblVw;

    @FXML
    private TableView<Part> addProductAssociatedPartTblVw;

    @FXML
    private Button addProductCancelBtn;

    @FXML
    private TextField addProductIDTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductPartSearchTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private Button addProductSaveBtn;

    @FXML
    private TableColumn<Part, Integer> allPartIDCol;

    @FXML
    private TableColumn<Part, Integer> allPartInvCol;

    @FXML
    private TableColumn<Part, String> allPartNameCol;

    @FXML
    private TableColumn<Part, Double> allPartPriceCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartIDCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    @FXML
    private Button removeAssociatedPartBtn;
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;
    private int index;
    /** This method detects when the Add Associated Part button is pressed and adds it to the list of associated parts for this product.
     
      @param event The event of clicking on the Add Associated Part Button
     */
    @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        if(addProductAllPartTblVw.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Select a product to associate.");
            alert.showAndWait();
        }
        else
        associatedParts.add(addProductAllPartTblVw.getSelectionModel().getSelectedItem());
    }

    /**
      This method detects when the Cancel button is pressed and cancels the new product. It then returns the user back to the main form. It also decrements the count of the next product.
      @param event The event of clicking on the cancel button.
     */
    @FXML
    void onActionCancelNewProduct(ActionEvent event) throws IOException {
        Inventory.productCanceled();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This method detects when the Remove Asssociated Part button is pressed and removes the Part. It uses a dialog to make sure the user is sure. It also checks to make sure the user has actually selected a part to remove.
     @param event The event of clicking on the Remove Associated Part button.
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        if(addProductAssociatedPartTblVw.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Select a part to remove.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        associatedParts.remove(addProductAssociatedPartTblVw.getSelectionModel().getSelectedItem());
        }
    }

    /** LOGICAL ERROR
      This method saves the new product. It reads the text field inputs and ensures they are valid, then saves the product and switches the scene back to the main form.
     
      A logical error I ran into occurred while writing this method. I thought it would be easy to add the associated parts to the actual associated parts list of the product.
      However, a problem arose as the Product class can only accept one associated part at a time, not a list.
      In order to resolve this, I had to iterate through the list of associated parts the user had chosen and then add them one by one into the new product's list of associated parts.
      @param event The event of clicking on the Save button
     */
    @FXML
    void onActionSaveNewProduct(ActionEvent event) throws IOException {
        String field = "Name";
        try{
        int id = index;
        String name = addProductNameTxt.getText();
        if(name.length() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Name field cannot be blank.");
            alert.showAndWait();
        }
        else{
        field = "Inv";
        int stock = Integer.parseInt(addProductInvTxt.getText());
        field = "Price";
        double price = Double.parseDouble(addProductPriceTxt.getText());
        field = "Max";
        int max = Integer.parseInt(addProductMaxTxt.getText());
        field = "Min";
        int min = Integer.parseInt(addProductMinTxt.getText());
        
            if(min > max){
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Min cannot be greater than max.");
            alert.showAndWait();
            }
            else if(!(min <= stock && stock <= max)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("The Inv level must be within the Min and Max");
            alert.showAndWait();
            }
            else if(!(min >= 0 && max >= 0 && stock >= 0)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Inv, Min, and Max must be greater than 0");
            alert.showAndWait();
            }
            
            else{
        ObservableList<Part> parts = addProductAssociatedPartTblVw.getItems();
        Product product = new Product(id, name, price, stock, min, max);
        for(Part part : parts){
            product.addAssociatedPart(part);
        }
        Inventory.addProduct(product);
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
            }
        }}
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values in text fields!\n"+e.getMessage()+"\nField: "+field);
            alert.showAndWait();
        }
        
    }
    
    /**
     This method searches the list of parts. It activates when the user presses the enter key while the search field is highlighted. It iterates through the list of parts to see if it can find the sought after part, and creates a dialog box if it cannot.
     If it can, it highlights the part or narrows the list, depending on if the search was for an ID or a Name.
     
     The code here is basically the same as the searches in the main method.
      @param event The event of pressing a key while the search field is selected.
     */
     @FXML
    void onActionAllPartsSearchKeyPressed(KeyEvent event) {
        addProductAllPartTblVw.getSelectionModel().clearSelection();
        if(event.getCode().equals(KeyCode.ENTER)){
        String search = addProductPartSearchTxt.getText();
        boolean isInt = true;
        boolean isNull = false;
        try {
            Integer.parseInt(search);
        }
        catch(NumberFormatException e) {
            isInt = false;
        }
        catch(NullPointerException e) {
            isInt = false;
            isNull = true;
        }
        if(isInt){
            addProductAllPartTblVw.setItems(Inventory.getAllParts());
            Part p = Inventory.lookUpPart(Integer.parseInt(search));
            int count = 0;
            if(p != null){
                for(Part a : addProductAllPartTblVw.getItems()){
                    if(a.getId()==p.getId()){
                        addProductAllPartTblVw.getSelectionModel().select(count);
                    }
                    count++;
                }
            }
            else {
                addProductAllPartTblVw.getSelectionModel().clearSelection();
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part could not be found.");
            alert.showAndWait();
            }
            
        }
        else if(!isNull){
            ObservableList<Part> sublist = Inventory.lookUpPart(search);
            
            if(sublist.size() == 0){
                addProductAllPartTblVw.setItems(Inventory.getAllParts());
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part could not be found.");
            alert.showAndWait();
            }
            else{
                addProductAllPartTblVw.setItems(sublist);
            }
        }
        else{
            addProductAllPartTblVw.setItems(Inventory.getAllParts());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part could not be found.");
            alert.showAndWait();
            
        }
        }
    

    }
    /**
      Initializes the controller class for the Add Product Form. It increases the index of the next part, as this method starting up indicates we are making that part.
      It sets the ID field to that ID as well. It also sets up the tableviews for the form.
      
      @param url The URL used to initialize
      @param rb The ResourceBundle used to initialize.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addProductAllPartTblVw.setItems(Inventory.getAllParts());
        allPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductAssociatedPartTblVw.setItems(associatedParts);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        index = Inventory.getNextProductID();
        addProductIDTxt.setText(""+index);
    }    
    
}
