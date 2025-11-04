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
  FXML Controller Class for the Modify Product Form. The main purpose of these methods is to read data entered by the user and use it to modify a product.
 This is very similar to the Add Product Form.
 
  @author Alex Beno
 */
public class ModifyProductFormController implements Initializable {

    @FXML
    private Button addAssociatedPartBtn;

    @FXML
    private TableView<Part> modifyProductAllPartTblVw;

    @FXML
    private TableView<Part> modifyProductAssociatedPartTblVw;

    @FXML
    private Button modifyProductCancelBtn;

    @FXML
    private TextField modifyProductIDTxt;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TextField modifyProductPartSearchTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private Button modifyProductSaveBtn;

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
    private Product original;
    private int spot;
    
    /**
     This method adds an associated part. It checks to make sure that a part is selected. If not, it creates a dialog.
     @param event The event of clicking on the Add Associated Part button.
     */
    @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        if(modifyProductAllPartTblVw.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Select a product to associate.");
            alert.showAndWait();
        }
        else
        associatedParts.add(modifyProductAllPartTblVw.getSelectionModel().getSelectedItem());
    }

    /** 
     This method cancels the modification of the product. It returns the user to the main form.
     @param event The event of clicking on the cancel button.
     */
    @FXML
    void onActionCancelModifyProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This method removes a part from the list the product's of associated parts. It checks to make sure that the user has selected a part to remove,
     and it shows a dialog boss to confirm if the user is sure.
     
     @param event The event of clicking on the remove associated part button.
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        if(modifyProductAssociatedPartTblVw.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Select a part to remove.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        associatedParts.remove(modifyProductAssociatedPartTblVw.getSelectionModel().getSelectedItem());
        }
    }

    /** 
     This method saves the modified product over its original spot in the Inventory. It checks to ensure that the data the user has entered is valid, and then
     it saves the product, overwriting the original product, and returns the user to the main form.
     
     @param event The event of clicking on the save button.
     */
    @FXML
    void onActionSaveModifyProduct(ActionEvent event) throws IOException {
        String field = "Name";
        try{
        int id = Integer.parseInt(modifyProductIDTxt.getText());
        String name = modifyProductNameTxt.getText();
        if(name.length() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Name field cannot be blank.");
            alert.showAndWait();
        }
        else{
        field = "Inv";
        int stock = Integer.parseInt(modifyProductInvTxt.getText());
        field = "Price";
        double price = Double.parseDouble(modifyProductPriceTxt.getText());
        field = "Max";
        int max = Integer.parseInt(modifyProductMaxTxt.getText());
        field = "Min";
        int min = Integer.parseInt(modifyProductMinTxt.getText());
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
        ObservableList<Part> parts = modifyProductAssociatedPartTblVw.getItems();
        Product product = new Product(id, name, price, stock, min, max);
        for(Part part : parts){
            product.addAssociatedPart(part);
        }
        Inventory.updateProduct(spot, product);
        
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
     
     The code here is identical to the method in the Add Product form.
      @param event The event of pressing a key while the search field is selected.
     */
     @FXML
    void onActionAllPartsSearchKeyPressed(KeyEvent event) {
        
        modifyProductAllPartTblVw.getSelectionModel().clearSelection();
        if(event.getCode().equals(KeyCode.ENTER)){
        String search = modifyProductPartSearchTxt.getText();
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
            modifyProductAllPartTblVw.setItems(Inventory.getAllParts());
            Part p = Inventory.lookUpPart(Integer.parseInt(search));
            int count = 0;
            if(p != null){
                for(Part a : modifyProductAllPartTblVw.getItems()){
                    if(a.getId()==p.getId()){
                        modifyProductAllPartTblVw.getSelectionModel().select(count);
                    }
                    count++;
                }
            }
            else {
                modifyProductAllPartTblVw.getSelectionModel().clearSelection();
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part could not be found.");
            alert.showAndWait();
            }
            
        }
        else if(!isNull){
            ObservableList<Part> sublist = Inventory.lookUpPart(search);
            
            if(sublist.size() == 0){
                modifyProductAllPartTblVw.setItems(Inventory.getAllParts());
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part could not be found.");
            alert.showAndWait();
            }
            else{
                modifyProductAllPartTblVw.setItems(sublist);
            }
        }
        else{
            modifyProductAllPartTblVw.setItems(Inventory.getAllParts());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part could not be found.");
            alert.showAndWait();
            
        }
        }
    }
    
    /**
     RUNTIME ERROR
     This method receives the product to be modified and displays its data in the text fields. It also stores the index of the original product in the Inventory's product list
     so that that position can be written to later.
     
     One runtime error I ran into was resolved while I was writing this method. Initially I had the Modify Product form save its new product into the slot of its ID in the Inventory's
     product list. However, this caused a crash when the ID of the products exceeded the number of items in the list due to products being deleted. This was solved,
     by tracking the index of the original product in receiveProduct and using it here to ensure we are writing the product to the same place we read it from.
     
     @param selectedProduct The product to be modified
     @param position The index of the product to be modified in the Inventory's product list
     */
    public void recieveProduct(Product selectedProduct, int position){
        modifyProductIDTxt.setText(""+selectedProduct.getId());
        modifyProductNameTxt.setText(selectedProduct.getName());
        modifyProductPriceTxt.setText(""+selectedProduct.getPrice());
        modifyProductInvTxt.setText(""+selectedProduct.getStock());
        modifyProductMaxTxt.setText(""+selectedProduct.getMax());
        modifyProductMinTxt.setText(""+selectedProduct.getMin());
        ObservableList<Part> parts = selectedProduct.getAllAssociatedParts();
        for(Part part : parts){
            associatedParts.add(part);
        }
        spot = position;
        original = selectedProduct;
    }
    /**
      Initializes the controller class for the modify product form. It sets up the tableviews, ensuring that the columns are prepared for the data they will be receiving.
     
      @param url The URL used to initialize
      @param rb The ResourceBundle used to initialize.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        modifyProductAllPartTblVw.setItems(Inventory.getAllParts());
        allPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductAssociatedPartTblVw.setItems(associatedParts);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }   }
