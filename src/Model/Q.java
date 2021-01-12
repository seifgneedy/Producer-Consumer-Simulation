package Model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

public class Q implements Runnable {
	Queue<Product> products;
	LinkedList<M> machines;

	public Q() {
		machines = new LinkedList<>();
		products = new LinkedBlockingQueue<>();
	}

	public Q(LinkedList<M> m) {
		machines = m;
		products = new LinkedBlockingDeque<>();
	}

	public void addMachine(M machine) {
		machines.add(machine);
	}

	public synchronized boolean addProduct(Product product) {
		boolean boo = products.offer(product);
		this.notify();
		return boo;
	}

	public boolean isEmpty() {
		return products.isEmpty();
	}

	@Override
	public void run() {
		for (;;) {
			synchronized (this) {
				while (products.isEmpty()) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				Product product = products.poll();
				int randomNum = new Random().nextInt(machines.size());
				M machine = machines.get(randomNum);
				while (machine.isWorking()) {
					try {
						this.wait(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				machine.processProduct(product);
			}
		}		
	}
}
