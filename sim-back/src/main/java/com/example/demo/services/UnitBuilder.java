package com.example.demo.services;


import com.example.demo.ToMachine;
import com.example.demo.ToQueue;
import com.example.demo.ToState;
import com.example.demo.services.*;
import com.example.demo.models.*;

import com.example.demo.services.SnapShot.*;
import com.example.demo.services.SnapShot.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public class UnitBuilder {

	private State ourState;

	public UnitBuilder() {
		this.ourState = Director.getInstance().getState();
	}

	public UnitBuilder(State state) {
		this.ourState = state;
	}

	public void CreateMachine(String Name,String PrevQueue ,String NextQueue) {
		Machine m = this.ourState.getMachine(Name);
		if (m == null) {
			m = new Machine(Name);
		}
		DataQueue prev = this.ourState.getQueue(PrevQueue);
		if (prev == null) {
			prev = new DataQueue( PrevQueue);
			addQueue(prev);
		}
		prev.addAvailableMachine(m);
		prev.lastQueue = false;
		DataQueue next = this.ourState.getQueue(NextQueue);
		if (next == null) {
			next = new DataQueue(NextQueue);
			next.lastQueue = true;
			addQueue(next);
		}
		m.setNextQueue(next);
		addMachine(m);
	}

	public void link(String First,String Second) {
		if(First.contains("M"))
		{
			Machine m = this.ourState.getMachine(First);
			DataQueue destination = this.ourState.getQueue(Second);
			addQueue(destination);
			m.setNextQueue(destination);
			addMachine(m);
			destination.lastQueue = true;

		}
		else
		{
			DataQueue source = this.ourState.getQueue(First);
			Machine m = this.ourState.getMachine(Second);
			addQueue(source);
			source.addAvailableMachine(m);
			source.lastQueue = false;
			addMachine(m);

		}
	}

	public void addMachine (Machine m) {
		this.ourState.addMachine(m);
	}

	public void addQueue(DataQueue q) {
		this.ourState.addQueue(q);
	}

	public State toUnit() {
		return this.ourState;
	}
	public void Delete(){
		this.ourState.reset();
	}

	public ToState toState(State state){
		ArrayList<ToQueue> toQueues=new ArrayList<>();
		ArrayList<ToMachine> toMachines=new ArrayList<>();
		System.out.println("Hi Mum");

		System.out.println(state.getFinishedProducts());
		System.out.println(state.getfactorqueue());

		for(DataQueue dataQueue: state.getfactorqueue()){
			System.out.println(dataQueue.getproductsqueue().size());

			ToQueue toQueue=new ToQueue(dataQueue.getName(),String.valueOf(dataQueue.getproductsqueue().size()));
			toQueues.add(toQueue);
		}
		for (Machine machine: state.getFactoryMachines()){
			ToMachine toMachine=new ToMachine(machine.getName(), machine.getColor(), machine.getProduct(),machine.getMachineCurrenttime());
			toMachines.add(toMachine);
		}

		ToState toState=new ToState();
		toState.SetMachines(toMachines);
		toState.Setqueues(toQueues);
		return toState;
	}
	@Override
	public String toString() {
		return "UnitBuilder [OurUnit=" + ourState + "]";
	}


}
