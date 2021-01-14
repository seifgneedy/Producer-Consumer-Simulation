package application;

import java.util.Random;

import Model.Product;
import Model.Q;

public class ProductFeeder implements Runnable {
	
	private Q q;
	int numberOfProducts;
	public ProductFeeder(Q q, int n) {
		this.q = q;
		this.numberOfProducts = n;
	}
	
	@Override
	public void run() {
		for(int i=1; i <= numberOfProducts; i++) {
			try {
				q.addProduct(new Product());
				Thread.sleep(new Random().nextInt(4500)+500);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		
	}

}
