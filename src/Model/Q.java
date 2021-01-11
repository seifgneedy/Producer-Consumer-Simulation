package Model;

import java.util.LinkedList;
import java.util.Queue;

public class Q {
	Queue<Product> products;
	LinkedList<M> waitingMs;
	public Q() {
		products = new LinkedList<>();
		waitingMs = new LinkedList<M>();
	}
	public void attach(M machine) {
		if(waitingMs.contains(machine)) {
			return;
		}
		waitingMs.add(machine);
	}	
	
	public void detach(M machine) {
		waitingMs.remove(machine);
	}
	public void addProduct(Product product) {
		products.add(product);
		for (M m : waitingMs) {
			if(m.consume()) {
				return;
			}
		}
	}
	
	public Product getProduct(M m) {
		
	}
	
	public boolean isEmpty() {
		return products.isEmpty();
	}
	
}
