/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author hpcslag
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private Label label;
    @FXML
    private Font x1;
    @FXML
    private TextArea servLog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        servLog.setText("\n Hello world \n");
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
    public void addTextArea(String text){
        label.setText("GG");
        servLog.setText("OKOKOKOKOKOKOKOK \n OKOKOKOKOKOK \n OKOKOKOKOK");
    }
    
}
