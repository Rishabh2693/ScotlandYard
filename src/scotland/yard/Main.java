package scotland.yard;

import scotland.yard.CustomShape;
import processing.core.PApplet;
import processing.core.PVector;
import scotland.yard.Room;;
public class Main extends PApplet {

	CustomShape cS; 
	Room r;
	 int f = 0;
	public static void main(String[] args){
		    PApplet.main("scotland.yard.Main");
		  }
	
		  public void settings(){
		    size(1000,1000);
		  }
		  
		  public void setup() {	    
			  cS = new CustomShape(mouseX,mouseY,this);	
			  r = new Room(this);
			  background(255);
		  }

		  public void draw() {
			  background(255);
			 
			 
			  	
			  if(cS.position.y<80-940&&cS.position.x<920-50)
				  cS.arrive(new PVector(950-50,50-940),3,100);
			  else if(cS.position.x>920-50&&cS.position.y<920-940)
				  cS.arrive(new PVector(950-50,950-940),3,100);
			  else if(cS.position.y>920-940&&cS.position.x>80-50)
				  cS.arrive(new PVector(50-50,950-940),3,100);
			  else if(cS.position.y>80-940&&cS.position.x<80-50)
				  cS.arrive(new PVector(50-50,50-940),3,100);	
				  
			  
			  cS.update();
			 // cS.drawBreadcrumbs();
		      cS.display();
		      r.diplayRoom();
		    
		  }

}
