package scotland.yard;

import processing.core.PApplet;

public class Room {
	PApplet parent;
	
	Room(PApplet p){
		parent = p;
	}
	
	void diplayRoom(){
		parent.pushMatrix();
	    parent.beginShape();
	    parent.fill(255,255,0);
	    parent.rect(500, 0, 200, 200);
	    
	    parent.fill(255,0,0);
	    parent.rect(100, 100, 100, 50);
	    parent.fill(0);
	    parent.rect(100, 150, 100, 50);
	    parent.fill(255,0,0);
	    parent.rect(100, 200, 100, 200);
	    parent.fill(0);
	    parent.rect(100, 400, 100, 50);
	    parent.fill(255,0,0);
	    parent.rect(100, 450, 100, 50);
	    
	    parent.fill(100,100,100);
	    parent.ellipse(250, 900, 100, -200);
	    parent.rect(200, 1000, 100, -50);
	    parent.rect(500, 900, -100, -100);
	    parent.fill(200);
	    
	    parent.ellipse(445, 850, -50, -80);
	    parent.fill(255,255,0);
	    parent.rect(1000, 700, -200, -200);
	    
	    parent.fill(99,93,51);
	    parent.ellipse(750,850,100,100);
	    
	    parent.fill(255,0,0);
	    parent.rect(400, 400, 100, 100);
	    parent.rect(500, 500, 100, 100);
	   
	    parent.fill(189,183,107);
	    parent.rect(400, 400, 100, 10);
	    parent.rect(500, 500, 10, 100);
	    
	    
	  	parent.fill(0);
	  	parent.rect(300, 0, 100, 300);
	  	parent.rect(1000, 400, -300, -100);
	  	parent.rect(500, 1000, 100, -300);
	  	parent.rect(0, 600, 300, 100);
	  	
	  	parent.fill(0);
	  	for(int i=-1;i<1000;i+=100){
	  		parent.rect(i,0,2,1000);
	  	}
	  	for(int i=-1;i<1000;i+=100){
	  		parent.rect(0,i,1000,2);
	  	}
	  	parent.fill(99,93,51);
	    parent.ellipse(850,150,100,100);
	    
	  	parent.endShape();
	    parent.popMatrix();
	}

}
