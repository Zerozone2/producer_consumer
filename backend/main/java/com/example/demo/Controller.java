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
    @RequestMapping(value = "/appStart",method = RequestMethod.POST)
    public void appStart()
    {
        setup = Setup.getInstance();
   }

    @ResponseBody
      @RequestMapping(value = "/add_Q/{id}",method = RequestMethod.POST)
      public void addQueue(@PathVariable String id)
      {

          setup.addQueue(id);
      }
      @ResponseBody
      @RequestMapping(value = "/add_M/{id}",method = RequestMethod.POST)
      public void addMachine(@PathVariable String id)
      {
          setup.addMachine(id);
      }



    @ResponseBody
    @RequestMapping(value = "/link/{first}/{second}",method = RequestMethod.POST)
    public void link(@PathVariable String first, @PathVariable String second)
    {

        setup.createMachineLink(first,second);
    }


    @ResponseBody
    @RequestMapping(value = "/run",method = RequestMethod.POST)
    public void run(@RequestBody String[] products)
    {

        setup.Simulate(Arrays.asList(products));
    }
    @ResponseBody
    @RequestMapping(value = "/notify",method = RequestMethod.GET)
    public ToState note()
    {

        return setup.updateUnit();
    }
    @ResponseBody
    @RequestMapping(value = "/re_run",method = RequestMethod.GET)
    public void re_run()
    {

         setup.ReSimulate();
    }
    @ResponseBody
    @RequestMapping(value = "/clear",method = RequestMethod.GET)
    public void clear()
    {

        setup.Reset();
    }


}
