package com.example.demo.models;

import com.example.demo.services.SnapShot.Director;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;


public class Machine implements Runnable {

	private DataQueue nextQueue;
	private long time;
	private String machineCurrenttime;
	private String Name;
	private Product product;
	private String color="#ffffff";
	private boolean avalible = true;

	private Object Lock;
	private Object ThreadLock;

	public Machine( String Name) {
		this.Name = Name;
		Random r = new Random();
		this.time = 2000 + r.nextInt(2000);
		System.out.println("Machine Time = "+this.time);
	}

	public DataQueue getNextQueue() {
		return nextQueue;
	}

	public String getMachineCurrenttime() {
		return machineCurrenttime;
	}

	public String getColor() {
		return color;
	}

	public void setNextQueue(DataQueue nextQueue) {
		this.nextQueue = nextQueue;
	}

	public long getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getProduct() {

		if(this.product==null){
			return "";
		}

		return product.getFirstName();
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void StartMachine(Product product, Object Lock) {
		this.product = product;
		this.ThreadLock = Lock;
		this.color=product.getColor();
		this.avalible = false;
		new Thread(this).start();
	}

	@Override
	public void run() {

		if (this.Lock != null) {
			runInstead(this/* .Lock */);
		} else if (this.ThreadLock != null) {
			runInstead(this.ThreadLock);
		}
	}

	private void runInstead(Object Lock) {
		long startTime = System.currentTimeMillis();
		synchronized (Lock) {
			try {
				showRemainingTime(startTime, Lock);
//				this.nextQueue.getproductsqueue().add()
				while (this.nextQueue.isFullProductQueue()) {
					Lock.wait(1000);
				}

				System.out.println("Machine " + this.Name + " ends " + this.product.getFirstName());
				this.color="#ffffff";
				System.out.println(Director.getInstance().getState().toString());
				this.avalible = true;
				Product p=this.product;
				this.product=null;
				this.nextQueue.StartQueue(p);

				//added
//				this.nextQueue.getProductsQueue().add(p);

				if (this.Lock != null) {
					synchronized (this.Lock) {
						this.Lock.notify();
					}
				}
				if (ThreadLock != null) {
					synchronized (ThreadLock) {
						this.ThreadLock.notifyAll();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void showRemainingTime(long StartTime, Object Lock) {
		synchronized (Lock) {
			try {

				// added by mohamed ibrahim
//				Product p=this.product;
//				this.product=null;
//				this.nextQueue.StartQueue(p);

				long t = this.time - (System.currentTimeMillis() - StartTime);
				while (t > 0) {
					String ti = Long.toString(t / 1000);
					System.out.println("Machine "+this.Name+" time : "+ti);
					this.machineCurrenttime=String.valueOf(ti);

					Lock.wait(1000);
					t = this.time - (System.currentTimeMillis() - StartTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isAvalible() {
		return avalible;
	}

	public void setAvailable(boolean b) {
		this.avalible = b;
	}

	public void setLock(Object lock) {
		Lock = lock;
	}

	public void setThreadLock(Object threadLock) {
		ThreadLock = threadLock;
	}

}