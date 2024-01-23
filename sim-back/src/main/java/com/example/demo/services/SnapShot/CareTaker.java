package com.example.demo.services.SnapShot;

import com.example.demo.models.State;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public class CareTaker {
	private static CareTaker instance;
	private  ArrayList<Memento> mementoList=new ArrayList<>();
	private CareTaker(){}
	public static CareTaker getInstance() {
		if (instance == null) {
			instance = new CareTaker();
		}
		return instance;
	}


	public ArrayList<Memento> getMementoList() {
		return mementoList;
	}

	public void add(Memento state) {
		mementoList.add(state);
	}

	public Memento get(int index) {
		return mementoList.get(index);
	}
}