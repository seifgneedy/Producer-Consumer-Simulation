package application;

import java.net.URL;
import java.util.*;
import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class SimulatorController implements Initializable {
	@FXML
	AnchorPane canvas;
	@FXML
	TextField textField;
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

	CareTaker careTaker = new CareTaker();
	Originator originator = new Originator();

	boolean addQ, addM;
	List<Rectangle> qRectangles = new ArrayList<>();
	List<QueueText> qTexts = new ArrayList<>();
	List<MachineCircle> mCircles = new ArrayList<>();
	List<M> ms = new ArrayList<>();
	List<Q> qs = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// add start Q
		Q q = new Q();
		qs.add(q);
		Rectangle r = new Rectangle(720, 260, 80, 90);
		r.setFill(Color.DARKGOLDENROD);
		QueueText t = new QueueText(720 + 20, 300, "Start", canvas, careTaker);
		t.setStyle("-fx-font-weight: bold");
		t.setFill(Color.BLACK);
		q.setqObserver(t);
		canvas.getChildren().add(r);
		canvas.getChildren().add(t);
		qTexts.add(t);
		qRectangles.add(r);
		r.setOnMouseReleased(ev -> {
			int i = 0;
			double x = ev.getSceneX();
			double y = ev.getSceneY() - 50;
			for (; i < mCircles.size(); i++) {
				MachineCircle c = mCircles.get(i);
				if (c.intersects(x, y, 1, 1)) {
					if (!addQ && !addM && !q.getMachines().contains(ms.get(i))
							&& (ms.get(i).getNextQ() == null || !ms.get(i).getNextQ().equals(q))) {
						q.addMachine(ms.get(i));
						Arrow a = new Arrow(r.getX() + 20, r.getY() + 15, c.getCenterX(), c.getCenterY());
						canvas.getChildren().add(a);
					}
				}
			}
		});
		t.setOnMouseReleased(ev -> {
			int i = 0;
			double x = ev.getSceneX();
			double y = ev.getSceneY() - 50;
			for (; i < mCircles.size(); i++) {
				MachineCircle c = mCircles.get(i);
				if (c.intersects(x, y, 1, 1)) {
					if (!addQ && !addM && !q.getMachines().contains(ms.get(i))
							&& (ms.get(i).getNextQ() == null || !ms.get(i).getNextQ().equals(q))) {
						q.addMachine(ms.get(i));
						Arrow a = new Arrow(r.getX() + 20, r.getY() + 15, c.getCenterX(), c.getCenterY());
						canvas.getChildren().add(a);
					}
				}
			}
		});
		// add end Queue
		Q q2 = new Q();
		qs.add(q2);
		Rectangle r2 = new Rectangle(0, 260, 80, 90);
		r2.setFill(Color.DARKCYAN);
		QueueText t2 = new QueueText(20, 300, "End", canvas, careTaker);
		t2.setStyle("-fx-font-weight: bold");
		t2.setFill(Color.BLACK);
		q2.setqObserver(t2);
		canvas.getChildren().add(r2);
		canvas.getChildren().add(t2);
		qTexts.add(t2);
		qRectangles.add(r2);

		textField.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					textField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	@FXML
	public void addQueue() {
		if (addQ) {
			addQ = false;
			addM = false;
			addQButton.setStyle(null);
		} else {
			if (addM)
				addMButton.setStyle(null);
			addQ = true;
			addM = false;
			addQButton.setStyle("-fx-background-color: #00cc44;");
		}
	}

	@FXML
	public void addMachine() {
		if (addM) {
			addQ = false;
			addM = false;
			addMButton.setStyle(null);
		} else {
			if (addQ)
				addQButton.setStyle(null);
			addM = true;
			addQ = false;
			addMButton.setStyle("-fx-background-color: #00cc44;");
		}
	}

	@FXML
	public void startSimulation() {
		qTexts.get(1).update("0");
		textField.setDisable(true);
		int numberOfProducts = Integer.parseInt(textField.getText());
		addQButton.setDisable(true);
		addMButton.setDisable(true);
		startSimulationButton.setDisable(true);
		replaySimulationButton.setDisable(true);
		resetSimulatioButton.setDisable(true);
		for (Q q : qs)
			new Thread(q).start();
		for (M m : ms)
			new Thread(m).start();
		ProductFeeder pf = new ProductFeeder(qs.get(0), numberOfProducts);
		new Thread(pf).start();
		EndSimulationDetector esd = new EndSimulationDetector(this, qs.get(1), numberOfProducts);
		new Thread(esd).start();
	}

	public void simulationEnded() {
		textField.setDisable(false);
		addQButton.setDisable(false);
		addMButton.setDisable(false);
		startSimulationButton.setDisable(false);
		replaySimulationButton.setDisable(false);
		resetSimulatioButton.setDisable(false);
		qs.get(1).clearProducts();
	}

	@FXML
	public void replaySimulation() {
		Replay replay=new Replay(this);
		new Thread(replay).start();
		// for (int i = 0; i < careTaker.getSize() - 1; i++) {
		// 	State firstState = careTaker.get(i).getState();
		// 	State secondState = careTaker.get(i + 1).getState();
		// 	long time = secondState.getTime() - firstState.getTime();
		// 	Platform.runLater(new Runnable() {
		// 		public void run() {
		// 			try {
		// 				Thread.sleep(time);
		// 				updateCanvas(firstState.getList());
		// 			} catch (InterruptedException e) {
		// 				e.printStackTrace();
		// 			}
		// 		}
		// 	});
        // //     Task<Void> sleeper = new Task<Void>() {
		// // 		@Override
		// // 		protected Void call() throws Exception {
		// // 			try {
		// // 				System.out.println("Going to sleep  "+time);

		// // 				Thread.sleep(time);
		// // 			} catch (InterruptedException e) {
		// // 			}
		// // 			return null;
		// // 		}
		// // 	};
		// // 	new Thread(sleeper).start();
        //  }
        // State lastState=careTaker.get(careTaker.getSize()-1).getState();
        // updateCanvas(lastState.getList());
	}
	
	public void updateCanvas(ObservableList<Node> list){
		canvas.getChildren().clear();
		for(Node node : list){
			canvas.getChildren().add(node);
		}
		System.out.println("canvas updated0564");
	}

    @FXML
    public void resetSimulation(){
        careTaker = new CareTaker();
    }
    
    @FXML
    public void onPaneClicked(MouseEvent e) {
    	boolean intersected =false; // no to draw shape onto anther shape    
    	for(int i=0; i<mCircles.size(); i++) {
			Circle c = mCircles.get(i);
			if( c.intersects(e.getSceneX(), e.getSceneY()-50, 1, 1)) {
		        intersected=true;
			}
		}
		for(int i=0; i<qRectangles.size(); i++) {
			Rectangle r = qRectangles.get(i);
			if( r.intersects(e.getSceneX(), e.getSceneY()-50, 1, 1)) {
				intersected=true;
			}
		}
    	if(!intersected) {
	    	if(addQ) {
	    		Q q = new Q();
	    		qs.add(q);
	    		Rectangle r = new Rectangle(e.getSceneX(), e.getSceneY()-50, 40, 30);
	    		r.setFill(Color.rgb(251, 251, 1));
	    		QueueText t = new QueueText(e.getSceneX() + 12, e.getSceneY()-30,"Q"+(qs.size()-1),canvas,careTaker);
	    		t.setStyle("-fx-font-weight: bold");
				t.setFill(Color.BLUE);
				q.setqObserver(t);
	    		canvas.getChildren().add(r);
	    		canvas.getChildren().add(t);
	    		qTexts.add(t);
				qRectangles.add(r);
	    		r.setOnMouseReleased(ev -> {
					int i = 0;
	    			double x = ev.getSceneX();
	    			double y = ev.getSceneY() - 50;
	    			for(; i<mCircles.size(); i++) {
	    				MachineCircle c = mCircles.get(i);
	    				if( c.intersects(x, y, 1, 1)) {
	    					if(!addQ && !addM && ! q.getMachines().contains(ms.get(i)) && (ms.get(i).getNextQ() == null || ! ms.get(i).getNextQ().equals(q)) ) {
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
	    				MachineCircle c = mCircles.get(i);
	    				if( c.intersects(x, y, 1, 1)) {
	    					if(!addQ && !addM && ! q.getMachines().contains(ms.get(i)) && (ms.get(i).getNextQ() == null || ! ms.get(i).getNextQ().equals(q)) ) {
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
	    		MachineCircle c = new MachineCircle(e.getSceneX(), e.getSceneY()-50, 20,canvas,careTaker);
				mCircles.add(c);
				c.setFill(m.getColor());
				m.setObserver(c);
	    		Text t = new Text(e.getSceneX()-9, e.getSceneY()-45,"M"+(ms.size()-1));
	    		t.setStyle("-fx-font-weight: bold");
	    		t.setFill(Color.WHITE);
	    		canvas.getChildren().add(c);
	    		canvas.getChildren().add(t);
	    		c.setOnMouseReleased(ev -> {
	    			int i = 1;
	    			double x = ev.getSceneX();
	    			double y = ev.getSceneY() - 50;
	    			for(; i<qRectangles.size(); i++) {
	    				Rectangle r = qRectangles.get(i);
	    				if( r.intersects(x, y, 1, 1)) {
	    					if(!addQ && !addM && m.getNextQ() == null && !qs.get(i).getMachines().contains(m)) {
		    					m.setNextQ(qs.get(i));
		    					Arrow a = new Arrow(c.getCenterX(), c.getCenterY(), r.getX()+20, r.getY()+15);
		    					canvas.getChildren().add(a);
	    					}
	    				}
	    			}
	    		});
	    		
	    		t.setOnMouseReleased(ev -> {
	    			int i = 1;
	    			double x = ev.getSceneX();
	    			double y = ev.getSceneY() - 50;
	    			for(; i<qRectangles.size(); i++) {
	    				Rectangle r = qRectangles.get(i);
	    				if( r.intersects(x, y, 1, 1)) {
	    					if(!addQ && !addM && m.getNextQ() == null && !qs.get(i).getMachines().contains(m)) {
		    					m.setNextQ(qs.get(i));
		    					Arrow a = new Arrow(c.getCenterX(), c.getCenterY(), r.getX()+20, r.getY()+15);
		    					canvas.getChildren().add(a);
	    					}
	    				}
	    			}
	    		});
	    	}
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
// The most beatiful Spaghetti design pattern code ever.