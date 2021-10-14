/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SILOH
 */
public class MainWindowController implements Initializable {
    
    @FXML
    private Label errorDisplay;
    @FXML
    private TextField carReg;
    @FXML
    private ComboBox<Integer> weekDays;
    @FXML
    private Button btnGenerate;
    @FXML
    private TextField hours;
    @FXML
    private TextField duration;
    @FXML
    private ComboBox<Integer> disabled;
    @FXML
    private Label showCarPlate;
    @FXML
    private Label showHours;
    @FXML
    private Label showCongestion;
    @FXML
    private Label printSuccess;
    ObservableList<Integer>list = FXCollections.observableArrayList(1,2,3,4,5,6,7);
    ObservableList<Integer> lists = FXCollections.observableArrayList(0,1);
    @FXML
    private TextArea textReceiptShow;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        weekDays.setItems(list);
        disabled.setItems(lists);
        textReceiptShow.setText("");
        printout();
        weekDays.getSelectionModel().selectFirst();
        disabled.getSelectionModel().selectFirst();
    }   
    

   
    @FXML
    public void HandleEvents(MouseEvent event) {

        if (event.getSource() == btnGenerate) {
            
            if (checker().equals("Success")) {
                int total;
                printSuccess(Color.GREEN, "Receipt Printed & saved");
                System.out.println("test done succefully");
                Alert dg = new Alert(Alert.AlertType.INFORMATION);
                dg.setTitle("Mkenya mzalendo ");
                dg.setContentText("Ticket printed Succeffuly");
                dg.setHeaderText("KCG");
                dg.show();
                
                String hour = hours.getText();
                int wkDay = weekDays.getValue();
                String vReg = carReg.getText();
                String stayTime = duration.getText();
                int PWD = disabled.getValue();
                int intstayTime = Integer.parseInt(stayTime);
                
                if(PWD == 1){
                    total=0;
                }else{
                    if (intstayTime>3){
                       total=((intstayTime-3)*300)+(3*150);
                    }else{
                        total = intstayTime*150;
                    }
                }
                showCarPlate.setText(vReg);
                showHours.setText(stayTime);
                showCongestion.setText(""+total);
                System.out.println(total);
                printTicket(vReg,intstayTime,hour,wkDay,PWD,total);
                printout();
                
            }
            
        }
    }

  
    
    private String checker() {
        String status = "Success";
        String vReg = carReg.getText();
        int weekday = weekDays.getValue();
        String hour = hours.getText();
        String stayTime = duration.getText();
        int PWD = disabled.getValue();
        try{
        int intHour = Integer.parseInt(hour);
        int intstayTime = Integer.parseInt(stayTime);
        if(intHour <0 || intHour>23  ) {
            setLblError(Color.TOMATO, "Incorrect 24 Hour Format");
           status = "Error";
        }else{
            
        }
        }catch(Exception e){
             setLblError(Color.TOMATO, "Enter Iteger number for the Last Four Inputs ");
             status = "Error";
        }
        if(vReg.isEmpty()|| hour.isEmpty()  || stayTime.isEmpty() ){
            setLblError(Color.TOMATO, "Empty credentials not allowed ");
            status = "Error";
        } else {
           
        }
        System.out.println(vReg);
        return status;
    }
    
    private void setLblError(Color color, String text) {
        errorDisplay.setTextFill(color);
        errorDisplay.setText(text);
        System.out.println(text);
    }
    private void printSuccess(Color color, String text) {
        printSuccess.setTextFill(color);
        printSuccess.setText(text);
        System.out.println(text);
    }
    
     public void printTicket(String vReg,int intstayTime, String hour,int wkDay, int PWD,int total) {
        try (FileWriter writer = new FileWriter("congessionFee.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            
            bw.write("-------------------KCCA Congestion Fee Tickets--------------------------\n");
            bw.write("                  Car plate          :"+vReg+"\n ");
            bw.write("                  Number of Hours   :"+hour+"\n");
            bw.write("                  Congestion Fee     :"+total+"\n");
            bw.write("     Tip: travelling with a friend saves money and reduces traffic\n\n");

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
   }
    
     public void printout() {
        
         try (BufferedReader br = new BufferedReader(new FileReader("congessionFee.txt"))) {
            String strCurrentLine;
            while ((strCurrentLine = br.readLine()) != null) {
             System.out.println(strCurrentLine);
             //textReceiptShow.clear();
             textReceiptShow.appendText(strCurrentLine);
             textReceiptShow.appendText("\n");
            }
            br.close();
           } catch (IOException e) {
            e.printStackTrace();
           }
     }
}
