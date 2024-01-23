package com.example.demo.models;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.example.demo.Setup;
import com.example.demo.services.*;
import com.example.demo.models.*;
import com.example.demo.services.SnapShot.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class DataQueue implements Runnable {

	private String Name;
	private BlockingQueue<Product> productsQueue;
	private ArrayList<Machine> availableMachines;
	private ArrayList<Machine> nextMachines;

	public boolean lastQueue = false;

	private Object Lock = new Object();

	public DataQueue(String Name) {

		this.Name = Name;
		this.productsQueue = new ArrayBlockingQueue<Product>(10000);
		this.availableMachines = new ArrayList<Machine>();
		this.nextMachines=new ArrayList<>();
	}

	public BlockingQueue<Product> getproductsqueue() {
		return productsQueue;
	}

	public void addAvailableMachine(Machine m) {
		for (int i = 0; i < this.availableMachines.size(); i++) {
			if (this.availableMachines.get(i).getName().equalsIgnoreCase(m.getName()))
				return;
		}
		this.availableMachines.add(m);
	}
	public void addNextMachine(Machine machine){
		this.nextMachines.add(machine);
	}

	public void addProduct(Product p) {
		this.productsQueue.add(p);

	}

	public void StartQueue(Product p) {

		this.productsQueue.add(p);

		try {
			Thread.sleep(1000); // Adjust the delay time as needed
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		new Thread(this).start();


	}

	public BlockingQueue<Product> getProductsQueue() {
		return productsQueue;
	}

	public void setProductsQueue(BlockingQueue<Product> productsQueue) {
		this.productsQueue = productsQueue;
	}

	public ArrayList<Machine> getAvailableMachines() {
		return availableMachines;
	}

	public void setAvailableMachines(ArrayList<Machine> availableMachines) {
		this.availableMachines = availableMachines;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public boolean isFullProductQueue() {
		return this.productsQueue.size() >= 10;
	}

	public void Simulate(Product p) {
		synchronized (Lock) {
			Machine available = getAvailableMachine();
			try {
				Random r = new Random();
				int time = 1000 + r.nextInt(1000);
				this.Lock.wait(time);
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}

			while (available == null) {
				try {
					System.out.println("Simulate is wait");
					this.Lock.wait();
					System.out.println("Simulate is Not wait");
					available = getAvailableMachine();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			available.StartMachine(p, Lock);

		}

	}

	private Machine getAvailableMachine() {
		for (int i = 0; i < this.availableMachines.size(); i++) {
			if (this.availableMachines.get(i).isAvalible()) {
				return this.availableMachines.get(i);
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "UnitQueue [Name=" + Name + ", productsQueue=" + productsQueue + ", availableMachines="
				+ availableMachines + "]";
	}

	@Override
	public void run() {
		while (!this.productsQueue.isEmpty()) {
			synchronized (productsQueue) {
				if (this.lastQueue) {

					if(this.availableMachines.size() == 0)
						return;
//					System.out.println(this.);
					for (int i = 0; i < this.productsQueue.size(); i++) {

						Director.getInstance().getState().getFinishedProducts().add(this.productsQueue.poll());

					}
					System.out.println("Finished Product at "+this.getName()+" : "+ Director.getInstance().getState().getFinishedProducts());
				} else {
					Product product = this.productsQueue.poll();
					if (product != null) {
						synchronized (Director.getInstance().Lock) {
							Machine available = getAvailableMachine();
							while (available == null) {
								try {
									System.out.println("thread queue " + this.Name + " wait");
									Director.getInstance().Lock.wait();
									System.out.println("thread queue " + this.Name + "NOt wait");
									available = getAvailableMachine();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							available.StartMachine(product, Director.getInstance().Lock);

						}

					}
				}
			}
		}
	}
}