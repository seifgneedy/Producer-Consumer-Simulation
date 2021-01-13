package application;

import java.net.URL;
import java.util.*;
import Model.*;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;

public class SimulatorController implements Initializable {
    @FXML 
    AnchorPane canvas ;

    List<M> ms = new ArrayList<>();
    List<Q> qs=new ArrayList<>();
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //TODO : write some code here
    }
    @FXML
    public void closeClicked() {
        System.exit(0);
    }
    public void clearCanvas(){
        canvas.getChildren().clear();
    }

   
}
