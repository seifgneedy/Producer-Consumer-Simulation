package application;

import Model.MachineObserver;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MachineCircle extends Circle implements MachineObserver {
	
	public MachineCircle(double sceneX, double d, int i) {
		super(sceneX, d,i);
	}

	@Override
	public void update(Color c) {
		setFill(c);
	}
	
}
