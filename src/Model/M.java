package Model;

import java.util.ArrayList;

public class M implements Runnable {
	ArrayList<Q> prevQs;
	Q nextQ;
	boolean working;
	int workingTime;
	@Override
	public void run() {
		for(;;) {
			
		}
		
	}
	public boolean consume() {
		if(working) return false;
		this.run();
		return true;
	}
	
	
}
