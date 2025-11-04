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
import model.Part;

/**
  FXML Controller class for the Modify Part Form. It takes a pre-existing Part, displays the associated data, and allows the user to edit and update it.
  
  It is very similar to the Add Part Form Controller.
 
  @author Alex Beno
 */
public class ModifyPartFormController implements Initializable {

    @FXML
    private Button modifyPartCancelBtn;

    @FXML
    private TextField modifyPartIDTxt;

    @FXML
    private RadioButton modifyPartInHouseRBtn;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private Label modifyPartMacComLbl;

    @FXML
    private TextField modifyPartMacComTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private RadioButton modifyPartOutsourceRBtn;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    private Button modifyPartSaveBtn;

    Stage stage;
    Parent scene;
    
    private Part original;
    private int spot;
    
    /**
       This method detects when the Cancel button is pressed and cancels the modification of a part. It returns the user back to the main form.
       @param event The event of clicking the Cancel button.
    */
    @FXML
    void onActionCancelModifyPart(ActionEvent event) throws IOException {
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
    void onActionInHouseModify(ActionEvent event) {
        modifyPartMacComLbl.setText("Machine ID");
    }

    /**
       This method changes the MachineID/CompanyName Label to Company Name when the Outsourced radio button is selected. It detects when the button is clicked and changes the text of the label.
       @param event The event of clicking the Outsourced radio button.
    */
    @FXML
    void onActionOutsourcedModify(ActionEvent event) {
        modifyPartMacComLbl.setText("Company Name");

    }

    /**
     LOGICAL ERROR
     This method saves the modified part to the Inventory Management System. It reads the text fields, checks them for errors, and then saves the modified part over its original in the Inventory.
     
     One Logical error I ran into occurred while I was writing this method. Since I was spamming through trying to test out a lot of inputs, I left the Name field blank. This ended up actually saving, leaving a
     part with no name! This was because "" is technically a valid string. To solve the issue, I had to ensure that the name and company name strings both were longer than zero.
     @param event The event of clicking the save button.
     */
    @FXML
    void onActionSaveModifyPart(ActionEvent event) throws IOException {
        String field = "Name";
        try{
        int id = Integer.parseInt(modifyPartIDTxt.getText());
        String name = modifyPartNameTxt.getText();
        if(name.length() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Name field cannot be blank.");
            alert.showAndWait();
        }
        else{
        field = "Price";
        double price = Double.parseDouble(modifyPartPriceTxt.getText());
        field = "Inv";
        int stock = Integer.parseInt(modifyPartInvTxt.getText());
        field = "Min";
        int min = Integer.parseInt(modifyPartMinTxt.getText());
        field = "Max";
        int max = Integer.parseInt(modifyPartMaxTxt.getText());
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
        if(modifyPartInHouseRBtn.isSelected()){
            field = "MachineID";
            int machineID = Integer.parseInt(modifyPartMacComTxt.getText());
            Inventory.updatePart(spot, new InHouse(id, name, price, stock, min, max, machineID));
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
        else{
            field = "Company Name";
            String companyName = modifyPartMacComTxt.getText();
            if(companyName.length() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Company Name field cannot be blank.");
            alert.showAndWait();
        }
        else{
            Inventory.updatePart(spot, new Outsourced(id, name, price, stock, min, max, companyName));
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
        }
        
            }
            
        }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values in text fields!\n"+e.getMessage()+"\nField: "+field);
            alert.showAndWait();
        }
    }
    
    /**
     This method receives the part to be modified from the main form, and updates the text fields to display information about the part. 
     It also saves the index of the part on the Inventory's main list, so that the part can be returned there later.
     @param selectedPart The part to be modified
     @param position The position on the Inventory's list that it came from
     */
    public void recievePart(Part selectedPart, int position){
        modifyPartIDTxt.setText(""+selectedPart.getId());
        modifyPartNameTxt.setText(selectedPart.getName());
        modifyPartPriceTxt.setText(""+selectedPart.getPrice());
        modifyPartInvTxt.setText(""+selectedPart.getStock());
        modifyPartMaxTxt.setText(""+selectedPart.getMax());
        modifyPartMinTxt.setText(""+selectedPart.getMin());
        if(selectedPart instanceof InHouse){
            modifyPartMacComTxt.setText(""+((InHouse) selectedPart).getMachineID());
            modifyPartMacComLbl.setText("Machine ID");
            modifyPartOutsourceRBtn.setSelected(false);
            modifyPartInHouseRBtn.setSelected(true);
        }
        else{
            modifyPartMacComTxt.setText(((Outsourced) selectedPart).getCompanyName());
            modifyPartMacComLbl.setText("Company Name");
            modifyPartOutsourceRBtn.setSelected(true);
            modifyPartInHouseRBtn.setSelected(false);
        }
        spot = position;
        original = selectedPart;
    }
    
    /**
      Initializes the controller class for the Modify Part Form.
      @param url The URL used to initialize
      @param rb The ResourceBundle used to initialize.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
