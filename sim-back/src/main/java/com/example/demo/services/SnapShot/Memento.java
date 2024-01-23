package com.example.demo.services.SnapShot;

import com.example.demo.models.*;
import com.example.demo.services.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public class Memento {
	private State state;

	public Memento(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

}
