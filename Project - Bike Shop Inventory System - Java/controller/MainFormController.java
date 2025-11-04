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
  FXML Controller for the Main Form of the Inventory Management System. Handles the tableviews, searches, and buttons on the main form. Passes the scene back and forth.
  A very time-consuming method to write.
 
  @author Alex Beno
 */
public class MainFormController implements Initializable {

    @FXML
    private Button addPartBtn;

    @FXML
    private Button addProductBtn;

    @FXML
    private Button deletePartBtn;

    @FXML
    private Button deleteProductBtn;

    @FXML
    private Button exitMainFormBtn;

    @FXML
    private TextField mainFormPartSearchTxt;

    @FXML
    private TableView<Part> mainFormPartTblVw;

    @FXML
    private TextField mainFormProductSearchTxt;

    @FXML
    private TableView<Product> mainFormProductTblVw;

    @FXML
    private Button modifyPartBtn;

    @FXML
    private Button modifyProductBtn;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    Stage stage;
    Parent scene;
    
    /**
      This method switches the scene to the Add Part form when the Add Part button is pressed. 
      @param event The event of clicking the Add Part button.
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
      This method switches the scene to the Add Product form when the Add Product button is pressed. 
      @param event The event of clicking the Add Product button.
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
      This method deletes a part from the list of parts. It opens up a dialog to confirm whether the user wants the part deleted.
      
      
      @param event The event of clicking on the delete part button.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        if(mainFormPartTblVw.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Select a part to delete.");
            alert.showAndWait();
        }
        else{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            Inventory.deletePart(mainFormPartTblVw.getSelectionModel().getSelectedItem());
        }
    }

    /**
     LOGICAL ERROR
     
      This method deletes a Product from the list of products. It opens a dialog to confirm whether or not the user wants the product deleted.
      It also checks to ensure that the user has selected a product, and that the selected product has no associated parts.
      One logical error I ran into occurred when writing this method. Even when a product was not selected, pressing the delete product button would result in the dialog
      opening and confirming if I wanted to delete the part. I realized I needed to check if a part was selected before proceeding with the rest of the method.
      
      @param event The event of clicking on the delete product button.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        
        if(mainFormProductTblVw.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Select a product to delete.");
            alert.showAndWait();
        }
        else if(!((""+mainFormProductTblVw.getSelectionModel().getSelectedItem().getAllAssociatedParts()).equals("[]"))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Cannot delete a product while it has associated parts.");
            alert.showAndWait();
        }
        else{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            Inventory.deleteProduct(mainFormProductTblVw.getSelectionModel().getSelectedItem());
        }
    }

    /**
     FUTURE ENHANCEMENT
     This method exits the program when the exit button is clicked.
     Currently, nothing is saved after the program exits. One future enhancement would be to save the information from the Inventory Management System.
     
     @param event The event of clicking on the exit button.
     */
    @FXML
    void onActionExitMainForm(ActionEvent event) {

        System.exit(0);
    }

    /**
     This method opens the modify part form when the modify part button is pressed. It detects when the button is pressed, ensures that a part is selected to modify,
     and then sends that part to the modify part form. If none are found, a dialog box informs the user.
     @param event The event of clicking on the modify part button.
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        
        
        
        try{
        Part x = mainFormPartTblVw.getSelectionModel().getSelectedItem();
        String s = ""+mainFormPartTblVw.getSelectionModel().getSelectedIndices();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
        loader.load();
        
        ModifyPartFormController modifyController = loader.getController();
        
        modifyController.recievePart(x,Integer.parseInt(s.substring(1,s.length()-1)));
        
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Select a part to modify.");
            alert.showAndWait();
        }
        
    }

    /**
     This method opens the modify product form when the modify product button is pressed. It detects when the button is pressed, ensures that a product is selected to modify,
     and then sends that product to the modify product form. If none are found, a dialog box informs the user.
     @param event The event of clicking on the modify product button.
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
        loader.load();
        Product x = mainFormProductTblVw.getSelectionModel().getSelectedItem();
        ModifyProductFormController modifyController = loader.getController();
        String s = ""+mainFormProductTblVw.getSelectionModel().getSelectedIndices();
        
        modifyController.recieveProduct(x,Integer.parseInt(s.substring(1,s.length()-1)));
        
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Select a product to modify.");
            alert.showAndWait();
        }
    }
    
    
    
     /**
     LOGICAL ERROR
     This method searches the list of parts when the enter key is pressed while the part search field is selected. It accepts an ID or a Name, and iterates through the list until it finds one.
     If searching by ID, it highlights the part, and if by name it narrows the list.
      
     One logical error I ran into occurred while writing this method. If I searched for a Name, and narrowed the list, and then my next search was for an ID, I found that it could
     sometimes highlight the wrong part. This was due to me not resetting the tableview back to its default every time the search began. The ID I had found was correct, but the index it associated
     with that ID was wrong because the list had been narrowed by a prior search. Just resetting the tableview at the start of each search solved this.
     @param event The event of pressing a key while the part search is highlighted.
     */
    @FXML
    void onActionPartSearchPressed(KeyEvent event) {
        mainFormPartTblVw.getSelectionModel().clearSelection();
        if(event.getCode().equals(KeyCode.ENTER)){
        String search = mainFormPartSearchTxt.getText();
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
            mainFormPartTblVw.setItems(Inventory.getAllParts());
            Part p = Inventory.lookUpPart(Integer.parseInt(search));
            int count = 0;
            if(p != null){
                for(Part a : mainFormPartTblVw.getItems()){
                    if(a.getId()==p.getId()){
                        mainFormPartTblVw.getSelectionModel().select(count);
                    }
                    count++;
                }
            }
            else {
                mainFormPartTblVw.getSelectionModel().clearSelection();
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part could not be found.");
            alert.showAndWait();
            }
            
        }
        else if(!isNull){
            ObservableList<Part> sublist = Inventory.lookUpPart(search);
            
            if(sublist.size() == 0){
                mainFormPartTblVw.setItems(Inventory.getAllParts());
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part could not be found.");
            alert.showAndWait();
            }
            else{
                mainFormPartTblVw.setItems(sublist);
            }
        }
        else{
            mainFormPartTblVw.setItems(Inventory.getAllParts());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part could not be found.");
            alert.showAndWait();
            
        }
        }
    }
    
    /**
     This method searches the list of products when the enter key is pressed while the part search field is selected. It accepts an ID or a Name, and iterates through the list until it finds one.
     If searching by ID, it highlights the product, and if by name it narrows the list.
     @param event The event of pressing a key while the product search is highlighted.
     */
    @FXML
    void onActionProductSearchPressed(KeyEvent event) {
        mainFormProductTblVw.getSelectionModel().clearSelection();
        if(event.getCode().equals(KeyCode.ENTER)){
        String search = mainFormProductSearchTxt.getText();
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
            mainFormProductTblVw.setItems(Inventory.getAllProducts());
            Product p = Inventory.lookUpProduct(Integer.parseInt(search));
            int count = 0;
            if(p != null){
                for(Product a : mainFormProductTblVw.getItems()){
                    if(a.getId()==p.getId()){
                        mainFormProductTblVw.getSelectionModel().select(count);
                    }
                    count++;
                }
            }
            else {
                mainFormProductTblVw.getSelectionModel().clearSelection();
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Product could not be found.");
            alert.showAndWait();
            }
        }
        else if(!isNull){
            if(Inventory.lookUpProduct(search).size() == 0){
            mainFormProductTblVw.setItems(Inventory.getAllProducts());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Product could not be found.");
            alert.showAndWait();
            }
            else mainFormProductTblVw.setItems(Inventory.lookUpProduct(search));
        }
        else{
            mainFormProductTblVw.setItems(Inventory.getAllProducts());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Product could not be found.");
            alert.showAndWait();
        }
        }
    }
    /**
     * Initializes the controller class for the main form. It sets up the tableviews, ensuring that the columns are mapped to the correct parts of the observablelists.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainFormPartTblVw.setItems(Inventory.getAllParts());
        mainFormProductTblVw.setItems(Inventory.getAllProducts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    
    
}
