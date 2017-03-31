package scotland.yard;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class GameMap extends PApplet {
	PShape s;
	PImage background, xBoard;
	List<PImage> tickets = new ArrayList<PImage>();	//bus, taxi, underground, black, x2
	
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
		
		stroke(0,0,255);
		noFill();
		strokeWeight(5);
		ellipse(52, 500, 20, 20);
	}

}
