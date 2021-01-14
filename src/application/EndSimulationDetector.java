package application;

import Model.Q;

public class EndSimulationDetector implements Runnable {

	private SimulatorController sc;
	private Q q;
	private int numberOfProducts;
	public EndSimulationDetector(SimulatorController sc, Q q, int numberOfProducts) {
		this.sc = sc;
		this.q = q;
		this.numberOfProducts = numberOfProducts;
	}
	@Override
	public void run() {
		while(numberOfProducts != q.getSize()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sc.simulationEnded();
	}

}
