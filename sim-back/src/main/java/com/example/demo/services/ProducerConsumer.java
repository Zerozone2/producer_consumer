package com.example.demo.services;

import com.example.demo.services.SnapShot.*;
import com.example.demo.models.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class ProducerConsumer {
	State u = Director.getInstance().getState();
	private static ProducerConsumer instance;
	private ProducerConsumer() {

	}
	public static ProducerConsumer getInstance() {
		if (instance == null)
			instance = new ProducerConsumer();
		return instance;
	}
	public void reset() {
		u = Director.getInstance().getState();
	}
	public void produce(Product p) {
		synchronized (this) {
//			System.out.println("produce" + p.getFirstName());
			while (u.getFirstQueue().isFullProductQueue()) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			u.addProduct(p);
			notify();
		}
	}


	public void Consume() {

		while (true) {
			synchronized (this) {
				while (u.getFirstQueue().getproductsqueue().isEmpty()) {
					try {
						System.out.println("Consume is Wait");
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Director.getInstance().saveStateToMemonto(this.u);
				u.getFirstQueue().Simulate(u.getFirstQueue().getProductsQueue().poll());
				notify();
			}
		}
	}



}
