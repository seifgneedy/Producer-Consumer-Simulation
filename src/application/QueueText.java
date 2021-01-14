package application;

import Model.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class QueueText extends Text implements QueueObserver {
    AnchorPane canvas;
	CareTaker careTaker;
    public QueueText(double x,double y,String text,AnchorPane canvas,CareTaker careTaker){
        super(x, y, text);
        this.canvas=canvas;
        this.careTaker=careTaker;
    }
    @Override
    public void update(String text) {
        setText(text);
        Originator originator =new Originator();
		originator.setState(new State(canvas.getChildren(),System.currentTimeMillis()));
		careTaker.add(originator.saveStateToMemento());
    }
    
}
