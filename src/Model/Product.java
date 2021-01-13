package Model;

import java.util.Random;

import javafx.scene.paint.Color;

public class Product {
	private Color color;
	public Product(){
		color=Color.rgb(new Random().nextInt(256),new Random().nextInt(256),new Random().nextInt(256));
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color=color;
	}
}
