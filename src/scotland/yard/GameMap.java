package scotland.yard;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class GameMap extends PApplet {
	PShape s;
	int pos;
	PImage background, xBoard;
	List<PImage> tickets = new ArrayList<PImage>();	//bus, taxi, underground, black, x2
	
	Graph g;
	int Number_of_Detectives = 5;
	List<Detective> dect = new ArrayList<Detective>();
	MrX x;
	
	int[] startNodes = {13,26, 29, 34, 50, 53, 91, 94, 103, 112, 117, 132, 138, 141, 155, 174, 197, 198};
	HashMap<Integer, int[]> colorMap = new HashMap<Integer, int[]>();
	
	public static void main(String[] args) {
		PApplet.main("scotland.yard.GameMap");
	}
	
	public void settings() {
		size(1200, 800);
		smooth();
	}

	public void setup() {
		String path = new File("").getAbsolutePath();
		background = loadImage(path + "/src/ScotlandYardBG.jpg");
		background.resize(1000, 800);
		
		xBoard = loadImage(path + "/src/log.jpg");
		tickets.add(loadImage(path + "/src/bus.png"));
		tickets.add(loadImage(path + "/src/taxi.png"));
		tickets.add(loadImage(path + "/src/underground.png"));
		tickets.add(loadImage(path + "/src/black-ticket.jpg"));
		tickets.add(loadImage(path + "/src/2x.png"));
		
		for(int i=0; i<tickets.size(); i++) {
			tickets.get(i).resize(45, 25);
		}
		
		g = new SmallGraph();
		x = new MrX(g.getNodes().get(10));
		Random r = new Random();
		for(int i=0; i<Number_of_Detectives; i++) {
			pos = startNodes[r.nextInt(18)];
			pos -= 1;
			//System.out.println(pos + " " + g.getNodes().get(pos).occupied);
			if(!g.getNodes().get(pos).occupied) {
				dect.add(new Detective(g.getNodes().get(pos), i));
				g.getNodes().get(pos).occupied = true;
				dect.get(i).getCurrentPosition().occupied = true;
			} else {
				//System.out.println(pos + " - " + g.getNodes().get(pos).occupied);
				i--;
			}
		}
		
		for(int i=0; i<199; i++) {
			System.out.println((i+1)+" " +g.getNodes().get(i).occupied);
		}
		colorMap.put(0, new int[]{0,0,255});
		colorMap.put(1, new int[]{255,0,0});
		colorMap.put(2, new int[]{0,255,0});
		colorMap.put(3, new int[]{0,255,255});
		colorMap.put(4, new int[]{255,255,0});
	}

	public void draw() {
		image(background, 0, 0);
		image(xBoard, 1000, 550);
		for(int i=0; i<tickets.size(); i++) {
			image(tickets.get(i), 1000, tickets.get(i).height*i);
		}
		
		/*s = createShape();
		s.beginShape();
		s.stroke(0,0,255);
		s.noFill();
		s.strokeWeight(5);
		s.vertex(0, 0);
		s.bezierVertex(15, 0, 25, 20, 0, 20);
		s.endShape(CLOSE);
		shape(s, 47, 490);*/
		
		//detective
		for(int i=0; i<Number_of_Detectives; i++) {
			stroke(colorMap.get(i)[0], colorMap.get(i)[1], colorMap.get(i)[2]);
			noFill();
			strokeWeight(5);
			ellipse(dect.get(i).getCurrentPosition().getX(), dect.get(i).getCurrentPosition().getY(), 20, 20);
		}
				
		//mrX
		stroke(0);
		noFill();
		strokeWeight(5);
		rect(x.getCurrentPosition().getX(), x.getCurrentPosition().getY(), 20, 20);
	}

}
