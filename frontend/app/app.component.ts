import { group } from '@angular/animations';
import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import konva from "konva"
import { Layer } from 'konva/lib/Layer';
import { Rect } from 'konva/lib/shapes/Rect';
import { Stage } from 'konva/lib/Stage';
import { bindCallback, isEmpty, last, withLatestFrom } from 'rxjs';
import { __values } from 'tslib';
import { copy_shape } from './copy';
import { object_shape } from './object_shape';
import { service } from './service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'paint';
  color:any="#4a98f7";


  all_myshape:any[]=[];
 // temp_all_myshape:any[]=[];
  pro:any
  stage:any;
  layer:any;
  rect:any; 
  triangle:any;    //all for draw in konva
  circle:any;
  line:any;
  ellipse:any;
  square:any;
  transform:any;
  lastline:any;
  copy_shape:any;
  max_id:any=0;
  id_shape:number=1;
  pre_id:any=0;
  pre_pre_id:any=0;
  s:any;
  isdraw:boolean=false;
  isdraw1:boolean=false;    //all are flages which are indicate to draw or not 
  isdraw2:boolean=false;
  isdraw3:boolean=false;
  
  id_Q=0;
  id_M=1;

  side:any=3;

  flag_rect:any;
  flage_circle:any;

  flag_remove:any;

  event_id:any;
  map = new Map<string,number>;

  all:any=[];

  
  
  last:any;

  

  make_object:object_shape=new object_shape();

  constructor(private ser: service){}

  

  ngOnInit(){
   
 this.stage=new konva.Stage({
      container:"board",
      width:1500,
      height:850,
     });

   this.layer= new konva.Layer();
   this.stage.add(this.layer);

    this.transform = new konva.Transformer();
   this.layer.add(this.transform);


  //  this.stage.on("click",(e:any)=>{
  //   this.event_id=e.target.attrs.id;
  //   var shape=this.stage.findOne("#"+this.event_id);

  // if(this.event_id==undefined){
  //    this.transform.nodes([]);
  //   }
  //   else{
    
  //   this.transform.nodes([shape]);
  //   shape.draggable(true);
    
  //   }    
     
     
    //  })
    
}


clear_flags(){
 
  this.flag_rect=false;
  this.flage_circle=false;
 this.flag_remove=false;

}

clear_request(){
  this.stop();
  this.ser.clear().subscribe
    (
      res=>{
        

      error:(error: HttpErrorResponse) =>
      {
        alert(error.message);
      }

     }  
   )

this.id_M=1;
this.id_Q=0;
this.all=[];
this.clear_allshapes();

}

clear_allshapes(){


  for(let j=0;j<this.all_myshape.length;j++){

    this.all_myshape[j].remove();
      
  }
  this.all_myshape=[];

  this.transform.nodes([]);
}


 ss:any=1;
 drawrect(){
      if(this.ss==1){
        this.ser.start().subscribe
        (
          {
            next: () =>
            {
              // Nothing to be returned
            },
            error:(error: HttpErrorResponse) =>
            {
              alert(error.message);
            }
    
         }
        )
        this.ss=0;
      }
    
    
       this.clear_flags();
      this.flag_rect=true;

      this.stage.on("mousedown",() =>{
        if(this.flag_rect==true){
            this.isdraw=true;
            this.rect=new konva.Rect({
            x:this.stage.getPointerPosition().x,
            y:this.stage.getPointerPosition().y,
            width:90,
            height:60,
            stroke:"black",
            strokeScaleEnabled:false,
            id:"Q"+this.id_Q,
            name:"Rectangle",
            strokeWidth:3,
            fill:"yellow",
            skewX:0,
            skewY:0,
            draggable:false,
            
                          });


     
      var simpleText = new konva.Text({
        x:this.rect.x()+30,
        y: this.rect.y()+20,
        text:this.rect.id(),
        fontSize: 25,
        fontFamily: 'Calibri',
        fill: 'black',
        id:"r"+this.id_Q,
      });
      this.id_Q++;
      this.flag_rect=false;      
      this.all_myshape.push(this.rect);
      this.all_myshape.push(simpleText);
      this.all.push(this.rect.id())
      this.map.set(this.rect.id(),-1)

      this.ser.add_Q(this.rect.id()).subscribe
      (
        {
          next: () =>
          {
            // Nothing to be returned
          },
          error:(error: HttpErrorResponse) =>
          {
            alert(error.message);
          }
  
       }
      )
      
      this.layer.add(this.rect).batchDraw();
      this.layer.add(simpleText);

                       

        }
                                     })
    

    

  }
  drawcircle(){
    this.clear_flags();
    this.flage_circle=true;
    this.stage.on("mousedown",() =>{
      if(this.flage_circle==true){
          this.isdraw1=true;
            
          
          this.circle=new konva.Circle({
          x:this.stage.getPointerPosition().x,
          y:this.stage.getPointerPosition().y,
          radius:35,
          stroke:"black",
          id:"M"+this.id_M,
          name:"Circle",
          strokeWidth:3,
          fill:"#4a98f7",
          skewX:0,
          skewY:0,
          draggable:false,
             });

        var simpleText = new konva.Text({
        x:this.circle.x()-20,
        y: this.circle.y()-10,
        text:this.circle.id(),
        fontSize: 25,
        fontFamily: 'Calibri',
        fill: 'black',
        id:"c"+this.id_M,
      });

    this.id_M++;  
   

    this.all.push(this.circle.id())
    this.flage_circle=false;
    this.all_myshape.push(this.circle);
    this.all_myshape.push(simpleText);
    this.map.set(this.circle.id(),-1)


    this.ser.add_M(this.circle.id()).subscribe
    (
      {
        next: (x) =>
        {
         // console.log(x.f)
          // Nothing to be returned
        },
        error:(error: HttpErrorResponse) =>
        {
          alert(error.message);
        }

     }
    )
    this.layer.add(this.circle).batchDraw();
     this.layer.add(simpleText);
        
      }
       })                             
}

put_arrow(from_id:any,to_id:any){
  
  //console.log(this.all)

  if((from_id.includes("Q")&&to_id.includes("Q"))||(from_id.includes("M")&&to_id.includes("M"))){
        alert("you cant connect m->m or Q->Q");
        return;
  }
  if(from_id.length==""||to_id==""){
    return
  }
  var shape1=this.stage.findOne("#"+from_id);
  var shape2=this.stage.findOne("#"+to_id);

  var arrow:any;
  if(from_id.includes("Q")){
    arrow = new konva.Arrow({
    points: [shape1.x()+90,shape1.y()+30,shape2.x()-30 ,shape2.y()-5],
    pointerLength: 10,
    pointerWidth: 10,
    fill: 'black',
    stroke: 'black',
    strokeWidth: 3,
  });
  this.layer.add(arrow);
  this.all_myshape.push(arrow);

  this.ser.add_link(to_id,from_id).subscribe
  (
    {
      next: () =>
      {
        // Nothing to be returned
      },
      error:(error: HttpErrorResponse) =>
      {
        alert(error.message);
      }

   }
  )



  }
  else if(from_id.includes("M")){
    if(this.map.get(from_id)==1){alert("M cannot give two Q")}
    arrow = new konva.Arrow({
      points: [shape1.x()+20 ,shape1.y(),shape2.x(),shape2.y()+30],
      pointerLength: 10,
      pointerWidth: 10,
      fill: 'black',
      stroke: 'black',
      strokeWidth: 3,
    });
    this.map.set(from_id,1);
    this.layer.add(arrow);
    this.all_myshape.push(arrow);

    this.ser.add_link(to_id,from_id).subscribe
  (
    {
      next: () =>
      {
        // Nothing to be returned
      },
      error:(error: HttpErrorResponse) =>
      {
        alert(error.message);
      }

   }
  )

  }

 


}
rand_color:any=[];
Run(){
 
  this.stop();
 // this.last=product-1;
  
  for(let i =0;i<parseInt(this.pro);i++){
  this.rand_color[i]='#'+(0x1000000+Math.random()*0xffffff).toString(16).substr(1,6)
  if(this.rand_color[i]=="#4a98f7"){
    this.rand_color[i]='#'+(0x1000000+Math.random()*0xffffff).toString(16).substr(1,6);
  }
  }
  console.log(this.rand_color);
  //var a:any=["Q0","M1"]
  // var shape1=this.stage.findOne("#"+"M1");
  // //shape1.fill(x);
  // var tween = new konva.Tween({
  //   node:shape1,
  //   duration: 1,
  //   blurRadius:0,
  //   easing: konva.Easings.EaseInOut,
  // });

  
  this.ser.run(this.rand_color).subscribe
  (
    {
      next: () =>
      {

        //this.note();

        console.log("x");
        
        
      },
      error:(error: HttpErrorResponse) =>
      {
        alert(error.message);
      }

   }
  )
 
  this.note();
  
  
}
 te:any=2;
playEvent:any;
note(){
  
  var s=this.ser;
  let sta=this.stage;
  this.te=2;
  
   this.playEvent = setInterval(function(this:any){

    s.notify().subscribe(
      
      (x) => {


      // x.FactoryQueues;
      // x.FactoryMachines;
       //var shape1=this.stage.findOne("#"+from_id);

        //  console.log(x.machines[0].name);
      for(let i=0;i<x.machines.length;i++){
         //console.log(x.machines[0].name)
         //let y=x.machines[0].name;
  
         var shape1=sta.findOne("#"+x.machines[i].name);
         shape1.fill(x.machines[i].color);

         var sliced = x.machines[i].name.slice(1,  x.machines[i].name.length);
         var shape2=sta.findOne("#c"+sliced);
         //let a=parseInt(x.queues[0].size);
         if(x.machines[i].productName==""){
          shape2.text( x.machines[i].name);
          shape2.fontSize(25);

         }
         
         else {shape2.text( x.machines[i].name+x.machines[i].productName);
         shape2.fontSize(18);
         }

      }
    //   for(let i=0;i<x.productName.length;i++){
    //     //console.log(x.machines[0].name)
    //     //let y=x.machines[0].name;
 
    //     var shape1=sta.findOne("#"+x.machines[i].name);
    //     shape1.fill(x.machines[i].color);
    //  }

      // // //console.log(x);
      // // if(x==5){
      // //     clearInterval(playEvent);
      
      
     //  for(let i=0;i<x.queues.length();i++){
        var sliced = x.queues[0].name.slice(1,  x.queues[0].name.length);
         var shape1=sta.findOne("#r"+sliced);
         let a=parseInt(x.queues[0].size);
         
         if(a==0){
          shape1.text( x.queues[0].name);
          shape1.fontSize(25);
         }
        
        
         else{shape1.text( x.queues[0].name+" n"+ (a+1));
         shape1.fontSize(18);}
        //  if(x[x.queues.length()-1].size==last){

        //   clearInterval(playEvent);

        //  }
       // }

      
      
        
      // }
    })
   
})
}
stop(){

  clearInterval(this.playEvent);
  
}

re_Run(){

  this.stop();
  this.ser.run(this.rand_color).subscribe
  (
    {
      next: () =>
      {

        //this.note();

        console.log("x");
        
        
      },
      error:(error: HttpErrorResponse) =>
      {
        alert(error.message);
      }

   }
  )
 
  this.note();

  // this.stop();
  // this.ser.re_run().subscribe
  // (
  //   {
  //     next: () =>
  //     {

  //       //this.note();
  //       this.Run();
  //       // console.log("x");
        
        
        
  //     },
  //     error:(error: HttpErrorResponse) =>
  //     {
  //       alert(error.message);
  //     }

  //  }
  // )

}
get_pro(product:any){

  if(product==''){
    alert("enter number of product");
    return;
  }
this.pro=product;


}
}