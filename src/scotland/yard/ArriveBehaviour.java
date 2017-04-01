package scotland.yard;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PVector;
public class ArriveBehaviour extends PApplet {
	CustomShape cS;
	PVector dest;
//	Room r;
	PVector d;
	LinkedList<Node> path;
	public static void main(String[] args){
		    PApplet.main("GraphGenerate.ArriveBehaviour");
		  }
	  		
		  public void settings(){
		    size(1000,1000);
		  }

		  public void setup() {
		     //  r = new Room(this);
			   dest = new PVector(0,0);
			  cS = new CustomShape(mouseX,mouseY,this);
			  d = new PVector(0,0);
			  path=new LinkedList<Node>();
		    
		  }

		  public void draw() {
			  if(cS.position.x<-550)
				  cS.position.x=450;
			  if(cS.position.y<-450)
				  cS.position.y=550;
			  if(cS.position.x>450)
				  cS.position.x=-550;
			  if(cS.position.y>550)
				  cS.position.y=-450;
			  
			  background(255);
			//  r.diplayRoom();
			  Graph g = new SmallGraph();
			  Astar a = new Astar(g);
			  
			  float sourcex = cS.position.x+550;
			  float sourcey = cS.position.y+450;
			  float desty = dest.y+450;
			  float destx = dest.x+550;
			  if(mousePressed==true)
				  {
				  	dest = new PVector(mouseX-550,mouseY-450);
				  	sourcex = cS.position.x+550;
				  	sourcey = cS.position.y+450;
				  	desty = dest.y+450;
				  	destx = dest.x+550;
				  	Node source = g.getNodes().get( (floor(sourcey/100)*10+floor(sourcex/100)));
				  	Node desti = g.getNodes().get( (floor(desty/100)*10+floor(destx/100)));
				  	path = a.execute(source, desti);
				  }
			   sourcex = cS.position.x+550;
			   sourcey = cS.position.y+450;	
				  if(path!=null&&path.size()>0){
						  if((floor((cS.position.y+450)/100)*10+floor((cS.position.x+550)/100))!=path.getFirst().getId()){
							  d = new PVector(path.getFirst().getX()+50-550,path.getFirst().getY()+50-450);
						  }
						  else{
							  path.removeFirst();
						  }
				  }
				  if(path!=null&&path.size()>0)
			  System.out.println(path.getFirst()+" "+path.size()+" "+(floor(cS.position.y+450/100)*10+floor(cS.position.x+550/100)));
			  cS.arrive(d,3,100);
			  cS.update();
			  cS.drawBreadcrumbs();
		      cS.display();
		      
		  }


}
