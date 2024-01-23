package com.example.demo.models;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public class Product {

	private String color ;
	private String name;
	
	public String getColor() {

		return this.color;
	}

	private String handleColorString(String s) {
		if (s.length()==1) {
			s="0"+s;
		}
		return s.toUpperCase();
	}

	public Product(String fName,String color) {

		this.name = fName;
		this.color=color;
		//this.color=
	}

	public String getFirstName() {
		return this.name;
	}

	public void setFirstName(String fName) {

		this.name=fName;
	}



	@Override
	public String toString() {
		return "Product [color=" + color + ", name=" + name + "]";
	}

	public void setcolor(String color) {
		this.color = color;
	}


}
