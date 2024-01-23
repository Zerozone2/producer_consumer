package com.example.demo;

import java.util.ArrayList;

public class ToState {
    public ArrayList<ToMachine> machines=new ArrayList<>();
    public ArrayList<ToQueue> queues=new ArrayList<>();
    public void SetMachines(ArrayList<ToMachine> machines){
        this.machines=machines;
    }
    public void Setqueues(ArrayList<ToQueue> queues){
        this.queues=queues;
    }

}
