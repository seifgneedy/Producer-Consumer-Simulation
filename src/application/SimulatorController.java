package application;

import java.net.URL;
import java.util.*;
import Model.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class SimulatorController implements Initializable {
    @FXML 
    AnchorPane canvas;
    
    @FXML
    Button addQButton;
    @FXML
    Button addMButton;
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
    List<Text> qTexts = new ArrayList<>();
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
    	if(addQ) {
    		addQ = false;
    		addM = false;
    		addQButton.setStyle(null);
    	}else {
    		if(addM)
    			addMButton.setStyle(null);
	        addQ=true;
	        addM=false;
	        addQButton.setStyle("-fx-background-color: #00cc44;");
    	}
    }

    @FXML
    public void addMachine(){
    	if(addM) {
    		addQ = false;
    		addM = false;
    		addMButton.setStyle(null);
    	}else {
    		if(addQ)
    			addQButton.setStyle(null);
	        addM=true;
	        addQ=false;
	        addMButton.setStyle("-fx-background-color: #00cc44;");
    	}
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
    public void onPaneClicked(MouseEvent e) {
    	if(addQ) {
    		Q q = new Q();
    		qs.add(q);
    		Rectangle r = new Rectangle(e.getSceneX(), e.getSceneY()-50, 40, 30);
    		r.setFill(Color.rgb(251, 251, 1));
    		Text t = new Text(e.getSceneX() + 12, e.getSceneY()-30,"Q"+(qs.size()-1));
    		t.setStyle("-fx-font-weight: bold");
    		t.setFill(Color.BLUE);
    		canvas.getChildren().add(r);
    		canvas.getChildren().add(t);
    		qTexts.add(t);
    		qRectangles.add(r);
    		r.setOnMouseReleased(ev -> {
    			int i = 0;
    			double x = ev.getSceneX();
    			double y = ev.getSceneY() - 50;
    			for(; i<mCircles.size(); i++) {
    				Circle c = mCircles.get(i);
    				if( c.intersects(x, y, 1, 1)) {
    					if( ! q.getMachines().contains(ms.get(i)) && (ms.get(i).getNextQ() == null || ! ms.get(i).getNextQ().equals(q)) ) {
	    					q.addMachine(ms.get(i));
	    					Arrow a = new Arrow(r.getX()+20, r.getY()+15, c.getCenterX(), c.getCenterY());
	    					canvas.getChildren().add(a);
    					}
    				}
    			}
    		});
    		t.setOnMouseReleased(ev -> {
    			int i = 0;
    			double x = ev.getSceneX();
    			double y = ev.getSceneY() - 50;
    			for(; i<mCircles.size(); i++) {
    				Circle c = mCircles.get(i);
    				if( c.intersects(x, y, 1, 1)) {
    					if( ! q.getMachines().contains(ms.get(i)) && (ms.get(i).getNextQ() == null || ! ms.get(i).getNextQ().equals(q)) ) {
	    					q.addMachine(ms.get(i));
	    					Arrow a = new Arrow(r.getX()+20, r.getY()+15, c.getCenterX(), c.getCenterY());
	    					canvas.getChildren().add(a);
    					}
    				}
    			}
    		});
    	} else if(addM) {
    		M m = new M();
    		ms.add(m);
    		Circle c = new Circle(e.getSceneX(), e.getSceneY()-50, 20);
    		mCircles.add(c);
    		c.setFill(m.getColor());
    		Text t = new Text(e.getSceneX()-9, e.getSceneY()-45,"M"+(ms.size()-1));
    		t.setStyle("-fx-font-weight: bold");
    		t.setFill(Color.WHITE);
    		canvas.getChildren().add(c);
    		canvas.getChildren().add(t);
    		c.setOnMouseReleased(ev -> {
    			int i = 0;
    			double x = ev.getSceneX();
    			double y = ev.getSceneY() - 50;
    			for(; i<qRectangles.size(); i++) {
    				Rectangle r = qRectangles.get(i);
    				if( r.intersects(x, y, 1, 1)) {
    					if( m.getNextQ() == null && !qs.get(i).getMachines().contains(m)) {
	    					m.setNextQ(qs.get(i));
	    					Arrow a = new Arrow(c.getCenterX(), c.getCenterY(), r.getX()+20, r.getY()+15);
	    					canvas.getChildren().add(a);
    					}
    				}
    			}
    		});
    		
    		t.setOnMouseReleased(ev -> {
    			int i = 0;
    			double x = ev.getSceneX();
    			double y = ev.getSceneY() - 50;
    			for(; i<qRectangles.size(); i++) {
    				Rectangle r = qRectangles.get(i);
    				if( r.intersects(x, y, 1, 1)) {
    					if( m.getNextQ() == null && !qs.get(i).getMachines().contains(m)) {
	    					m.setNextQ(qs.get(i));
	    					Arrow a = new Arrow(c.getCenterX(), c.getCenterY(), r.getX()+20, r.getY()+15);
	    					canvas.getChildren().add(a);
    					}
    				}
    			}
    		});
    	}
    }
    
    @FXML
    public void closeClicked() {
        System.exit(0);
    }
    public void clearCanvas(){
        canvas.getChildren().clear();
    }

   
}
