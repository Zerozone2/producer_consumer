<template>
    <div class = "navbar">
        <div class = "items">
          <button class = "btn btn-outline-dark" id="state1" @click = "addMachine">ADD MACHINE</button>
          <button class = "btn btn-outline-dark" id="state1" @click = "addQueue"  >ADD QUEUE</button>
          <button class = "btn btn-outline-dark" id="state1" @click = "join"      >JOIN</button>

          <button class = "btn btn-outline-dark" id="state1" @click="startSimFn">Start Sim</button>
          <button class = "btn btn-outline-dark" id="state2" @click="resetSimFn">Replay Sim</button>
          <button class = "btn btn-outline-dark" id="state2" @click="startNewSimFn">Start New Sim</button>
          
          <label for="quantity">Enter the number of products:</label>
          <input type="number" id="quantity" name="quantity" min="1" max="100000">
          <button class = "btn btn-outline-success" @click = "numberOfProducts">Enter</button>
        </div>
    </div>
    <div id = "container">
    </div>
</template>

<script>
import Konva from 'konva' ;

export default {

name: 'SystemComponent',
  
  data() {
    return {
        queue_id   : 0,
        machine_id : 0,
        layer      : new Konva.Layer(),
        arrow      : null,
        // Rate for watching the api -> 1 sec
        rateCheck : 1000,
        products: [],
        currentInterval: null,
        objectsInLayer: [],
        oldState: []
    };
  },
  async created() {
    await fetch("http://localhost:8080/appStart")
  },
  methods:{
    async resetSimFn() {
      await fetch("http://localhost:8080/reset/sim", {
        method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
      })
    },
    async startSimFn() {
      
      if( this.products.length != 0 ) {
        let allState1Buttons = document.querySelectorAll("#state1")
        let allState2Buttons = document.querySelectorAll("#state2")

        allState1Buttons.forEach(button => {
          button.style.display = "none"
        })
        allState2Buttons.forEach(button => {
          button.style.display = "inline-block"
        })

        this.objectsInLayer = this.layer.children;
        
        this.currentInterval = setInterval(this.getIdFromBack, this.rateCheck)

        await fetch("http://localhost:8080/start/sim", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.products),
        })
      }
 
    },
    async startNewSimFn() {
        await fetch("http://localhost:8080/start/new/sim")
        this.queue_id = 0
        this.machine_id = 0
        this.products = []
        this.objectsInLayer = []
        this.layer.destroy()
        this.layer = new Konva.Layer()
        clearInterval(this.currentInterval)

        let allState1Buttons = document.querySelectorAll("#state1")
        let allState2Buttons = document.querySelectorAll("#state2")

        allState1Buttons.forEach(button => {
          button.style.display = "inline-block"
        })
        allState2Buttons.forEach(button => {
          button.style.display = "none"
        })
    },
    async getIdFromBack() {
      const result = await fetch("http://localhost:8080/notify")
      let state = await result.json()
      console.log(state)
      let machines = state["machines"]
      let queues = state["queues"]

      let machinesSize = machines.length
      let queuesSize = queues.length

      for(let i = 0; i<queuesSize ; i++) {
        this.drawState(queues[i])
      }

      for(let i = 0; i<machinesSize ; i++) {
        this.drawState(machines[i])
      }
    },
    drawState(fullObject) {

      let id = fullObject["name"]

      if (id.includes("Q")) {

        let circle = this.findObjectById1(this.objectsInLayer, id)

        circle[2].text( "Product Quantity = " + fullObject["size"] );
        console.log()
        this.layer.draw()

      } else if (id.includes("M")) {

        let circle = this.findObjectById1(this.objectsInLayer, id)
        circle[0].fill(fullObject["color"])
        console.log(circle[0].fill())
        this.layer.draw()

      }
    },
    findObjectById1(objectsArray, objectId) {
      for (var i = 0; i < objectsArray.length; i++) {
        if ( objectsArray[i] instanceof Konva.Circle && objectsArray[i].id().includes(objectId) ) {
          if(objectId.includes("Q")){
            return [objectsArray[i], objectsArray[i+1], objectsArray[i+2]];
          }else if (objectId.includes("M")){
            return [objectsArray[i], objectsArray[i+1]];
          }
        }
      }
      return [];
    },
    getRandomColor() {
      const letters = '0123456789ABCDEF';
      let color = '#';
      for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
      }
      return color;
    },
    addMachine() {
        const stage = new Konva.Stage({
            container : 'container',
            width     : window.innerWidth,
            height    : window.innerHeight,
        });

        stage.add(this.layer);

        const circle = new Konva.Circle({
            x         : stage.width() / 2,
            y         : stage.height() / 2,
            id        : 'M' + String(this.machine_id),
            radius    : 75,
            fill      : 'white',
            stroke    : 'black',
            draggable : true,
            name      : ""
        });

        this.layer.add(circle);

        const Machine_DTO = {
          id : circle.id() 
        };

        fetch(`http://localhost:8080/add_M/${circle.id()}`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
        })
        
        const textInside = new Konva.Text({
            text          : 'M'+ String(this.machine_id++),
            fontSize      : 12,
            fill          : 'black',
            align         : 'center',
            verticalAlign : 'middle',
        });

        textInside.position({
            x : circle.x() - 5,
            y : circle.y() - 10,
        });

        this.layer.add(textInside);

        circle.on('dragmove', () => {
            textInside.position({
                x : circle.x() - 5,
                y : circle.y() - 10,
            });

            this.layer.draw();
        });

        this.layer.draw();
    },

    addQueue() {    
        const stage = new Konva.Stage({
            container : 'container',
            width     : window.innerWidth,
            height    : window.innerHeight,
        });

        stage.add(this.layer);

        const circle = new Konva.Circle({
            x         : stage.width() / 2,
            y         : stage.height() / 2,
            id        : 'Q' + String(this.queue_id),
            radius    : 75,
            fill      : 'black',
            draggable : true,
        });

        this.layer.add(circle);

        const Queue_DTO = {
          id : circle.id() 
        };

        fetch(`http://localhost:8080/add_Q/${circle.id()}`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
        })

        const textInside = new Konva.Text({
            text          : 'Q'+ String(this.queue_id),
            fontSize      : 12,
            fill          : 'white',
            align         : 'center',
            verticalAlign : 'middle',
        });

        this.layer.add(textInside);

        const textOutside = new Konva.Text({
            text          : 'Product Quantity = 0',
            id            : String(this.queue_id++),
            fontSize      : 12,
            fill          : 'black',
            align         : 'center',
            verticalAlign : 'middle',
        });

        this.layer.add(textOutside);

        textInside.position({
            x : circle.x() - 5,
            y : circle.y() - 10,
        });

        textOutside.position({
            x : circle.x() - 50,
            y : circle.y() - circle.radius() - 20, 
        });

        circle.on('dragmove', () => {
            textInside.position({
                x : circle.x() - 5,
                y : circle.y() - 10,
            });

            textOutside.position({
                x : circle.x() - 50,
                y : circle.y() - circle.radius() - 20, 
            });

            this.layer.draw();
        });

        this.layer.draw();
    },

    join() {
      const circleArrowMap  = new Map() ;
      const selectedCircles = [] ;

      this.layer.on('click', (e) => {
        const shape = e.target ;
        if (shape.getClassName() === 'Circle') {
          const currentCircle = shape ;

          selectedCircles.push(currentCircle);

          if (selectedCircles.length === 2) {
            const [firstCircle, secondCircle] = selectedCircles ;

            const arrow = new Konva.Arrow({
              points: [firstCircle.x(), firstCircle.y(), secondCircle.x(), secondCircle.y()],
              pointerLength : 7,
              pointerWidth  : 7,
              fill          : 'darkgrey',
              stroke        : 'darkgrey',
              strokeWidth   : 2,
              id: `${firstCircle.id()}_${secondCircle.id()}`
            });

            const Join_DTO = {
              first_id  : firstCircle.id() ,
              second_id :secondCircle.id() 
              
            };

            fetch(`http://localhost:8080/link/${firstCircle.id()}/${secondCircle.id()}`, {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
            })

            this.layer.add(arrow);
            circleArrowMap.set(`${firstCircle._id}_${secondCircle._id}`, arrow);

            selectedCircles.forEach(circle => {
              circle.on('dragmove', () => {
                arrow.points([
                  firstCircle.x(),
                  firstCircle.y(),
                  secondCircle.x(),
                  secondCircle.y(),
                ]) ;
                this.layer.batchDraw() ;
              }) ;
            }) ;

            selectedCircles.length = 0 ;
          }
        }
      });
    },

    numberOfProducts(){
      const productNumber = document.getElementById('quantity').value
      this.products = Array.from({ length: productNumber }, () => this.getRandomColor());
    }

  }

}
</script>
@import url('https://fonts.googleapis.com/css2?family=Grand+Hotel&display=swap');

<style scoped>
  #container{
    top           : 10%       ;
    left          : 5px       ;
    position      : fixed     ;
    width         : 99.5%     ;
    height        : 89.5%     ;
    border        : 4px solid ;
    border-radius : 20px 20px 20px 20px ;  
  }
  .items{
    top      : 20px  ; 
    left     : 5px   ;
    position : fixed ;
  } 
  .btn{
    margin: 5px;
  }
  .productNumber{
    display: inline-block;
  }

  label{
    margin-left  : 1000px ;
    margin-right : 10px ;
    font-family: 'Grand Hotel', cursive;
  }

  #quantity{
    width: 200px;
    border-radius: 15px 15px 15px 15px;
    padding: 5px;
    margin-right: 10px;
  }

  #state2 {
    display: none;
  }
</style>