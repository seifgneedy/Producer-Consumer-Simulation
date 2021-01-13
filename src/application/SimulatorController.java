package application;

import java.net.URL;
import java.util.*;
import Model.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;

public class SimulatorController implements Initializable {
    @FXML 
    AnchorPane canvas;

    @FXML
    Button startSimulationButton;
    @FXML
    Button replaySimulationButton;
    @FXML
    Button resetSimulatioButton;
    @FXML
    Button resetButton;

    CareTaker careTaker=new CareTaker();
    Originator originator=new Originator();

    boolean addQ,addM;
    List<Rectangle> qRectangles=new ArrayList<>();
    List<Circle> mCircles=new ArrayList<>();
    List<M> ms = new ArrayList<>();
    List<Q> qs=new ArrayList<>();
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //TODO : write some code here.
        canvas.getChildren();
    }

    @FXML
    public void addQueue(){
        addQ=true;
        addM=false;
    }

    @FXML
    public void addMachine(){
        addM=true;
        addQ=false;
    }

    @FXML
    public void connect(){

    }

    @FXML
    public void startSimulation(){

    }

    @FXML
    public void replaySimulation(){
        
    }

    @FXML
    public void resetSimulation(){
        careTaker = new CareTaker();
    }


    @FXML 
    public void reset(){
        careTaker = new CareTaker();
        clearCanvas();
        ms=new ArrayList<>();
        qs=new ArrayList<>();
        mCircles=new ArrayList<>();
        qRectangles=new ArrayList<>();
    }

    @FXML
    public void closeClicked() {
        System.exit(0);
    }
    public void clearCanvas(){
        canvas.getChildren().clear();
    }

   
}
