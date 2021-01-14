package application;

import Model.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MachineCircle extends Circle implements MachineObserver {
	AnchorPane canvas;
	CareTaker careTaker;
	public MachineCircle(double sceneX, double d, int i,AnchorPane canvas,CareTaker careTaker) {
		super(sceneX, d,i);
		this.canvas=canvas;
		this.careTaker=careTaker;
	}

	@Override
	public void update(Color c) {
		setFill(c);
		Originator originator =new Originator();
		originator.setState(new State(canvas.getChildren(),System.currentTimeMillis()));
		careTaker.add(originator.saveStateToMemento());
	}
	
}
