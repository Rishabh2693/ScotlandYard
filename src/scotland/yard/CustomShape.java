package scotland.yard;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

class CustomShape extends SteeringParams{
	  PApplet parent;
	  float MaxAcc;
	  float maxSpeed;
	  ArrayList<PVector> breadcrumb = new ArrayList<PVector>();
		 
	  CustomShape(float x, float y,PApplet p) {
	    acceleration = new PVector(0,0);
	    velocity = new PVector(0,0);
	    position = new PVector(x,y);
	    maxSpeed = 4;
	    MaxAcc = (float) 0.1;
	    parent = p;
	  }
	 
	  void update() {
		position.add(velocity);  
	    velocity.add(acceleration);
	    velocity.limit(maxSpeed);
	    acceleration.mult(0);
	  }
	 
	 
	  void drawBreadcrumbs(){
		  if(parent.frameCount%20==0){
			  breadcrumb.add(new PVector(position.x+550,position.y+450));
		  }
		  for(PVector a:breadcrumb){
			  parent.beginShape();
			  parent.ellipse(a.x, a.y, 10, 10);
			  parent.endShape();
		  }
	  }
	  void arrive(PVector target,float ros,float rod) {
		    PVector dir = PVector.sub(target,position);
		 
		    float dist = dir.mag();
		    dir.normalize();
		    if (dist < ros){
		    	velocity.mult(0.000001f);
		    	dist = 0;
		    }
		    else if (dist < rod) {
		      float m = PApplet.map(dist,0,rod,0,maxSpeed);
		      dir.mult(m);
		    } else {
		      dir.mult(maxSpeed);
		    }
		 
		    PVector acc = PVector.sub(dir,velocity);
		    acc.limit(MaxAcc);
		    acceleration.add(acc);
		    allign(target);
		  }
	  void allign(PVector target){
		  if(velocity.mag()>0){
			  float rot  = velocity.heading();
			  setOrientation(rot+PApplet.PI/2);
		  }
	  }
	  void display() {
	    parent.pushMatrix();
	    parent.translate(position.x+550,position.y+450);
	    parent.rotate(getOrientation());
	    parent.beginShape();
	    parent.fill(0);
	  	parent.triangle(-14,-6,14,-6,0,-28);
	  	parent.ellipse(0, 0, 30, 30);
	    parent.endShape();
	    parent.popMatrix();
	  }
}