package Model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

public class Q implements Runnable {
	Queue<Product> products;
	LinkedList<M> machines;
	QueueObserver qObserver;

	public Q() {
		machines = new LinkedList<>();
		products = new LinkedBlockingQueue<>();
	}

	public Q(LinkedList<M> m,QueueObserver queueObserver) {
		qObserver=queueObserver;
		machines = m;
		products = new LinkedBlockingDeque<>();
	}
	public void setqObserver(QueueObserver qObserver) {
		this.qObserver = qObserver;
	}
	public void addMachine(M machine) {
		machines.add(machine);
	}
	public int getSize() {
		return products.size();
	}
	
	public void clearProducts() {
		products.clear();
	}
	public synchronized boolean addProduct(Product product) {
		boolean boo = products.offer(product);
		qObserver.update(Integer.toString(products.size()));
		this.notify();
		return boo;
	}
	
	public LinkedList<M> getMachines(){
		return machines;
	}
	public boolean isEmpty() {
		return products.isEmpty();
	}

	@Override
	public void run() {
		for (;;) {
			synchronized (this) {
				while (products.isEmpty() || machines.isEmpty()) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				int randomNum = new Random().nextInt(machines.size());
				M machine = machines.get(randomNum);
				while (machine.isWorking()) {
					try {
						this.wait(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Product product = products.poll();
				machine.processProduct(product);
				qObserver.update(Integer.toString(products.size()));
			}
		}		
	}
}
