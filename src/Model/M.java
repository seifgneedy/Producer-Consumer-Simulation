package Model;

import java.util.ArrayList;

public class M implements Runnable {
	ArrayList<Q> prevQs;
	Q nextQ;
	boolean working;
	Product currentProduct;
	int workingTime;
	@Override
	public void run() {
		for(;;) {
			synchronized (prevQs) {
				while(!working) {
					
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
