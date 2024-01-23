package com.example.demo.models;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
public class State {
	private ArrayList<DataQueue> FactoryQueues;
	private ArrayList<Machine> FactoryMachines;
	private ArrayList<Product> FinishedProducts;



	public State() {
		FactoryQueues = new ArrayList<DataQueue>();
		FactoryMachines = new ArrayList<Machine>();
		setFinishedProducts(new ArrayList<Product>());
	}

	public ArrayList<DataQueue> getfactorqueue() {
		return FactoryQueues;
	}

	public ArrayList<Machine> getFactoryMachines() {
		return FactoryMachines;
	}

	@SuppressWarnings("unchecked")
	public State Copy() {
		State s = new State();
		s.FactoryQueues = (ArrayList<DataQueue>) this.FactoryQueues.clone();
		s.FactoryMachines = (ArrayList<Machine>) this.FactoryMachines.clone();
		return s;
	}

	public void addQueue(DataQueue q) {
		FactoryQueues.add(q);
	}

	public DataQueue getFirstQueue() {
		return FactoryQueues.get(0);
	}
	public DataQueue getLastQueue() {
		return FactoryQueues.get(FactoryQueues.size()-1);
	}



	public void removeQueue(int index) {
		FactoryQueues.remove(index);
	}

	public int QueueSize() {
		return this.FactoryQueues.size();
	}

	public boolean isEmptyQueues() {
		return this.FactoryQueues.size() == 0;
	}

	public DataQueue getQueue(String Name) {
		for (int i = 0; i < FactoryQueues.size(); i++) {
			if (FactoryQueues.get(i).getName().equalsIgnoreCase(Name)) {
				return FactoryQueues.get(i);
			}
		}
		return null;
	}

	public Machine getMachine(String Name) {
		for (int i = 0; i < FactoryMachines.size(); i++) {
			if (FactoryMachines.get(i).getName().equalsIgnoreCase(Name)) {
				return FactoryMachines.get(i);
			}
		}
		return null;
	}

	public void addMachine(Machine m) {
		FactoryMachines.add(m);
	}

	public void removeMachine(int index) {
		FactoryMachines.remove(index);
	}

	public void reset(){
		FactoryQueues.clear();
		FactoryMachines.clear();
		FinishedProducts.clear();
	}

	public boolean addProduct(Product p) {
		if (!FactoryQueues.isEmpty()) {
			FactoryQueues.get(0).addProduct(p);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Unit [FactoryQueues=" + FactoryQueues + ", FactoryMachines=" + FactoryMachines + "]";
	}

	public ArrayList<Product> getFinishedProducts() {
		return FinishedProducts;
	}

	public void setFinishedProducts(ArrayList<Product> finishedProducts) {
		FinishedProducts = finishedProducts;
	}

}