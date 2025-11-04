/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
  This class is the FXML Controller for the Add Part Form. The main purpose of the methods here are to read user data, make sure it fits, and then add it as a part to the main form's parts list.
  
  More time was spent in this section of the project than I expected. The variety of logical errors you have to account for with inputs is scary.
 
  @author Alex Beno
 */
public class AddPartFormController implements Initializable {

    @FXML
    private Button addPartCancelBtn;

    @FXML
    private TextField addPartIDTxt;

    @FXML
    private RadioButton addPartInHouseRBtn;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private Label addPartMacComLbl;

    @FXML
    private TextField addPartMacComTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private RadioButton addPartOutsourceRBtn;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private Button addPartSaveBtn;

    Stage stage;
    Parent scene;
    private int index;
    
    /**
       This method detects when the Cancel button is pressed and cancels the addition of a part. It lowers the count of the next ID to what it was before,
       and returns the user back to the main form.
       @param event The event of clicking the Cancel button.
    */
    @FXML
    void onActionCancelNewPart(ActionEvent event) throws IOException {
        Inventory.partCanceled();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
       This method changes the MachineID/CompanyName Label to Machine ID when the In House radio button is selected. It detects when the button is clicked and changes the text of the label.
       @param event The event of clicking the In House radio button.
    */
    @FXML
    void onActionInHouseNew(ActionEvent event) {
        addPartMacComLbl.setText("Machine ID");
    }

    /**
       This method changes the MachineID/CompanyName Label to Company Name when the Outsourced radio button is selected. It detects when the button is clicked and changes the text of the label.
       @param event The event of clicking the Outsourced radio button.
    */
    @FXML
    void onActionOutsourcedNew(ActionEvent event) {
        addPartMacComLbl.setText("Company Name");

    }

    /** LOGICAL ERROR
      
      This method saves the new part to the Inventory Management System. It reads the text fields for user input, checks them for errors, and then saves the part if they are valid.
       
       One logical error I ran into occurred while writing this method. As I was testing whether or not certain inputs would cause an error, the ID used for the parts quickly incremented out of control.
       The problem was that the ID would get incremented even if an error was thrown and the part didn't save or if the cancel button was hit instead. In those situations, the ID of the next part shouldn't go up.
       I realized that I needed to keep of the next ID stored outside of this method, and decrement the next ID if the part was canceled or an error was thrown.
       
       @param event The event of clicking the Save button.
    */
    @FXML
    void onActionSaveNewPart(ActionEvent event) throws IOException {
        String field = "Name";
        try{
            int id = index;
            String name = addPartNameTxt.getText();
            if(name.length() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Name field cannot be blank.");
            alert.showAndWait();
        }
        else{
            field = "Price";
            double price = Double.parseDouble(addPartPriceTxt.getText());
            field = "Inv";
            int stock = Integer.parseInt(addPartInvTxt.getText());
            field = "Min";
            int min = Integer.parseInt(addPartMinTxt.getText());
            field = "Max";
            int max = Integer.parseInt(addPartMaxTxt.getText());
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
            if(addPartInHouseRBtn.isSelected()){
                field = "Machine ID";
                int machineID = Integer.parseInt(addPartMacComTxt.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            }
            else{
                field = "Company Name";
                String companyName = addPartMacComTxt.getText();
                if(companyName.length() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Company Name field cannot be blank.");
            alert.showAndWait();
        }
        else{
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            }}
        
            
            }}
        }catch(NumberFormatException e){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values in text fields!\n"+e.getMessage()+"\nField: "+field);
            alert.showAndWait();
        }
        
    }
    
    /**
      Initializes the controller class for the Add Part Form. It increases the index of the next part, as this method starting up indicates we are making that part.
      It sets the ID field to that ID as well.
      
      @param url The URL used to initialize
      @param rb The ResourceBundle used to initialize.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        index = Inventory.getNextPartID();
        addPartIDTxt.setText(""+index);
    }    
    
}
