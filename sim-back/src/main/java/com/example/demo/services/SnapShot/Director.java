package com.example.demo.services.SnapShot;

import com.example.demo.models.*;
import com.example.demo.services.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public class Director {

	private Director() {

	}

	private State state;
	private static Director instance;


	public Object Lock = new Object();
	public Object Lock2 = new Object();
	public Object FullLock = new Object();

	public static Director getInstance() {
		if (instance == null) {
			instance = new Director();
			instance.setState(new State());
		}
		return instance;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void saveStateToMemonto(State state) {

		Memento memento=new Memento(state);
		CareTaker.getInstance().add(memento);
	}

	public State getStateFromMemento() {
		Memento memento =CareTaker.getInstance().get(0);
		System.out.println(memento.getState().getfactorqueue());
		this.state=memento.getState();
		return this.state;
	}

}
