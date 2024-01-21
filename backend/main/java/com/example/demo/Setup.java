package com.example.demo;

import com.example.demo.services.*;
import com.example.demo.models.*;
import com.example.demo.services.SnapShot.Director;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
@Service
public class Setup {


    private UnitBuilder unitBuilder;
    private static Setup instance;
    private Setup(){}
    public static Setup getInstance() {
        if (instance == null) {
            instance = new Setup();
            instance.unitBuilder=new UnitBuilder();
            System.out.println("Masa2el4aer");
        }
        return instance;
    }
        public void Simulate(List<String> colors){
        int n=colors.size();
            System.out.println(n);
        for (int i=0;i<n;i++){
            Product product=new Product("p"+String.valueOf(i),colors.get(i));
            ProducerConsumer.getInstance().produce(product);
        }
        ProducerConsumer.getInstance().Consume();
    }
    public void addQueue(String name){
        DataQueue Q=new DataQueue(name);
        this.unitBuilder.addQueue(Q);
    }
   public void addMachine(String name){
       Machine machine=new Machine(name);
       this.unitBuilder.addMachine(machine);
   }
   public void createMachineLink(String source,String destination){
        this.unitBuilder.link(source,destination);
   }
   public BlockingQueue<Product> sendQ(String Qname){
        State state =this.unitBuilder.toUnit();
        DataQueue dataQueue = state.getQueue(Qname);
        return dataQueue.getProductsQueue();
   }

   public ToState updateUnit (){
       return unitBuilder.toState(unitBuilder.toUnit());
    }

   public void Reset(){
        this.unitBuilder.Delete();
   }
    public ToState ReSimulate(){

        return unitBuilder.toState(Director.getInstance().getStateFromMemento());
    }

}
