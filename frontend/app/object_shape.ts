import { ThisReceiver } from "@angular/compiler";

export class object_shape{



    first:any //id   
    second:any//name 
    third:any//x
    fourth:any//y
    fifth:any//width
    sixth:any;//height
    seventh:any;//radius
    eight:any;//fill
    ninth:any;//rotation
    tenth:any;//scaleX
    eleventh:any;//scaleY
    twelfth:any;
    thrteen:any;
    fourteen:any;
    object:any;




constructor(){

}

get_object(shape:any):any{
    if(shape.name()=="Rectangle"){

    this.first=shape.name();
    this.second=shape.id();
    this.third=shape.x();
    this.fourth=shape.y();
    this.fifth=shape.width();
    this.sixth=shape.height();
    this.seventh=shape.fill();
    this.eight=shape.rotation();
    this.ninth=shape.scaleX();
    this.tenth=shape.scaleY();
    this.eleventh=0;
    this.twelfth=0;
    this.thrteen=0;
    this.fourteen=0;
    }

    else if(shape.name()=="Circle"){

        this.first=shape.name();
        this.second=shape.id();
        this.third=shape.x();
        this.fourth=shape.y();
        this.fifth=shape.radius();
        this.sixth=shape.fill();
        this.seventh=shape.rotation();
        this.eight=shape.scaleX();
        this.ninth=shape.scaleY();
        this.tenth=0;
        this.eleventh=0;
        this.twelfth=0;
        this.thrteen=0;
        this.fourteen=0;

    }
    else if(shape.name()=="Line"){

        this.first=shape.name();
        this.second=shape.id();
        this.third=shape.x();
        this.fourth=shape.y();
        this.fifth=shape.width();
        this.sixth=shape.stroke();
        this.seventh=shape.rotation();
        this.eight=shape.scaleX();
        this.ninth=shape.scaleY();
        this.tenth=shape.points().at(0);//x1
        this.eleventh=shape.points().at(1);//y1
        this.twelfth=shape.points().at(2);//x2
        this.thrteen=shape.points().at(3);//y2
        this.fourteen=0;
    }
    else if(shape.name()=="Ellipse"){
        this.first=shape.name();
        this.second=shape.id();
        this.third=shape.x();
        this.fourth=shape.y();
        this.fifth=shape.radiusX();
        this.sixth=shape.radiusY();
        this.seventh=shape.fill();
        this.eight=shape.rotation();
        this.ninth=shape.scaleX();
        this.tenth=shape.scaleY();
        this.eleventh=0;
        this.twelfth=0;
        this.thrteen=0;
        this.fourteen=0;
    }
    else if(shape.name()=="Triangle"){

        this.first=shape.name();
        this.second=shape.id();
        this.third=shape.x();
        this.fourth=shape.y();
        this.fifth=shape.radius();
        this.sixth=shape.sides();
        this.seventh=shape.fill();
        this.eight=shape.rotation();
        this.ninth=shape.scaleX();
        this.tenth=shape.scaleY();
        this.eleventh=0;
        this.twelfth=0;
        this.thrteen=0;
        this.fourteen=0;
    }
    else if(shape.name()=="Pentagon"){

        this.first=shape.name();
        this.second=shape.id();
        this.third=shape.x();
        this.fourth=shape.y();
        this.fifth=shape.radius();
        this.sixth=shape.sides();
        this.seventh=shape.fill();
        this.eight=shape.rotation();
        this.ninth=shape.scaleX();
        this.tenth=shape.scaleY();
        this.eleventh=0;
        this.twelfth=0;
        this.thrteen=0;
        this.fourteen=0;
    }
    else if(shape.name()=="Square"){
        this.first=shape.name();
        this.second=shape.id();
        this.third=shape.x();
        this.fourth=shape.y();
        this.fifth=shape.width();
        this.sixth=shape.height();
        this.seventh=shape.fill();
        this.eight=shape.rotation();
        this.ninth=shape.scaleX();
        this.tenth=shape.scaleY();
        this.eleventh=0;
        this.twelfth=0;
        this.thrteen=0;
        this.fourteen=0;
    }
 return this;






}














}