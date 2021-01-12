package Model;

import java.util.*;
import java.awt.Color;

public class M implements Runnable {
	private static final Color defaultColor = Color.GRAY;
	Q nextQ;
	boolean working;
	Product currentProduct;
	int workingTime;

	public M() {
		currentProduct = null;
		workingTime = new Random().nextInt(7500);
	}

	public M(Q nextQ) {
		this();
		this.nextQ = nextQ;
	}

	public void setNextQ(Q nextQ) {
		this.nextQ = nextQ;
	}

	public Color getColor(){
		if(currentProduct==null)
			return defaultColor;
		return currentProduct.getColor();
	}

	public void processProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
		this.notify();
	}
	public boolean isWorking() {
		return currentProduct==null;
	}

	@Override
	public void run() {
		for (;;) {
			synchronized (this) {
				while (currentProduct == null) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					/// observer here 
					Thread.sleep(workingTime);
					// flash color
					nextQ.addProduct(currentProduct);
					currentProduct=null;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	/**
	public boolean consume() {
		if(working) return false;
		this.run();
		return true;
	}
	**/
	
}
