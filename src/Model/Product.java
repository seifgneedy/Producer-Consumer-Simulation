package Model;

import java.awt.Color;
import java.util.Random;

public class Product {
	private Color color;
	public Product(){
		color=new Color(new Random().nextInt(256),new Random().nextInt(256),new Random().nextInt(256));
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color=color;
	}
}
