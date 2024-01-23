package com.example.demo;

import com.example.demo.models.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@CrossOrigin
@RestController
public class Controller {
      @Autowired
      public Setup setup;
    @ResponseBody
    @RequestMapping(value = "/appStart",method = RequestMethod.GET)
    public void appStart()
    {
        System.out.println("ok123");
        setup = Setup.getInstance();
        setup.Reset();
   }

    @ResponseBody
      @RequestMapping(value = "/add_Q/{id}",method = RequestMethod.POST)
      public void addQueue(@PathVariable String id)
      {
          System.out.println(id);
          setup.addQueue(id);
      }
      @ResponseBody
      @RequestMapping(value = "/add_M/{id}",method = RequestMethod.POST)
      public void addMachine(@PathVariable String id)
      {
          System.out.println(id);
          setup.addMachine(id);
      }



    @ResponseBody
    @RequestMapping(value = "/link/{first}/{second}",method = RequestMethod.POST)
    public void link(@PathVariable String first, @PathVariable String second)
    {
        System.out.println(first +" "+ second);
        setup.createMachineLink(first,second);
    }


    @ResponseBody
    @RequestMapping(value = "/start/sim",method = RequestMethod.POST)
    public void run(@RequestBody String[] products)
    {
        System.out.println("okokok");
        setup.Simulate(Arrays.asList(products));
    }
    @ResponseBody
    @RequestMapping(value = "/notify",method = RequestMethod.GET)
    public ToState note()
    {
        System.out.println("Test 1");
        System.out.println(setup.updateUnit() +"sdfadsfasfas______________________");
        return setup.updateUnit();
    }
    @ResponseBody
    @RequestMapping(value = "/reset/sim",method = RequestMethod.POST)
    public void re_run()
    {
        System.out.println("okokokokok");
        setup.ReSimulate();
    }
    @ResponseBody
    @RequestMapping(value = "/start/new/sim",method = RequestMethod.GET)
    public void clear()
    {

        setup.Reset();
    }


}
