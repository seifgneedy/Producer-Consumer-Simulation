package application;

import Model.Product;
import Model.Q;

public class ProductFeeder implements Runnable {
	
	private Q q;
	int numberOfProducts;
	long time;
	public ProductFeeder(Q q, int n, long time) {
		this.q = q;
		this.numberOfProducts = n;
		this.time = time;
	}
	
	@Override
	public void run() {
		for(int i=1; i <= numberOfProducts; i++) {
			try {
				q.addProduct(new Product());
				Thread.sleep(time);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		
	}

}
