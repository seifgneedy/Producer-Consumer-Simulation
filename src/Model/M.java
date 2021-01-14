package Model;

import java.util.*;

import javafx.scene.paint.Color;

public class M implements Runnable {
	private static final Color defaultColor = Color.GRAY;
	Q nextQ;
	boolean working;
	Product currentProduct;
	int workingTime;
	MachineObserver cObserver;
	public M() {
		currentProduct = null;
		workingTime = new Random().nextInt(6500) + 1000;
	}

	public M(Q nextQ,MachineObserver circle) {
		this();
		cObserver=circle;
		this.nextQ = nextQ;
	}

	public void setObserver(MachineObserver cObserver) {
		this.cObserver = cObserver;
	}
	public void setNextQ(Q nextQ) {
		this.nextQ = nextQ;
	}

	public Color getColor(){
		if(currentProduct==null)
			return defaultColor;
		return currentProduct.getColor();
	}

	public synchronized void processProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
		System.out.println("Machine will be notified");
		this.notify();
	}
	public boolean isWorking() {
		return currentProduct != null;
	}
	public Q getNextQ() {
		return nextQ;
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
					//useProduct color and update
					cObserver.update(currentProduct.getColor());
					Thread.sleep(workingTime);
					for(int k=0; k<3; k++) {
						//return to default color and update
						cObserver.update(defaultColor);
						Thread.sleep(200);
						//useProduct color and update
						cObserver.update(currentProduct.getColor());
						Thread.sleep(200);
					}
					//return to default color and update
					cObserver.update(defaultColor);

					nextQ.addProduct(currentProduct);
					currentProduct=null;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
