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
	boolean caught = false;
	List<PImage> tickets = new ArrayList<PImage>();	//bus, taxi, underground, black, x2
	
	Graph g;
	int Number_of_Detectives = 5;
	List<Detective> dect = new ArrayList<Detective>();
	MrX x;
	
	int[] startNodes = {13, 26, 29, 34, 50, 53, 91, 94, 103, 112, 117, 132, 138, 141, 155, 174, 197, 198};
	HashMap<Integer, int[]> colorMap = new HashMap<Integer, int[]>();
	
	public static void main(String[] args) {
		PApplet.main("scotland.yard.GameMap");
	}
	
	public void settings() {
		size(1200, 800);
		smooth();
	}

	public void setup() {
		g = new SmallGraph();
		
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
		
		//assign random positions to detectives
		Random r = new Random();
		for(int i=0; i<Number_of_Detectives; i++) {
			pos = startNodes[r.nextInt(18)] - 1;
			if(!g.getNodes().get(pos).occupied) {
				dect.add(new Detective(g, g.getNodes().get(pos), i));
				dect.get(i).getCurrentPosition().occupied = true;
				//System.out.println(g.getNodes().get(pos));
			} else {
				i--;
			}
		}
		
		//assign dummy nodes
		/*while(g.getNodes().get(pos).occupied) {
			pos = startNodes[r.nextInt(18)] - 1;
		}
		x = new MrX(g.getNodes().get(pos));
		x.getCurrentPosition().occupied = true;*/
		x = new MrX(g);
		x.chooseGreedyInit(dect, startNodes);
		
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
		
		if(frameCount % 100 == 0 && !caught) {
			gameLoop();
		}
		
		//detective
		for(int i=0; i<Number_of_Detectives; i++) {
			stroke(colorMap.get(i)[0], colorMap.get(i)[1], colorMap.get(i)[2]);
			noFill();
			strokeWeight(5);
			ellipse(dect.get(i).getCurrentPosition().getX(), dect.get(i).getCurrentPosition().getY(), 20, 20);
		}
				
		//mrX
		stroke(255);
		noFill();
		strokeWeight(4);
		rect(x.getCurrentPosition().getX()-10, x.getCurrentPosition().getY()-10, 20, 20);
	}
	
	public void gameLoop() {
		List<Node> dectPos = new ArrayList<Node>();
		List<Node> xPos = new ArrayList<Node>();
		Node temp = new Node();
		for(int i=0; i<dect.size(); i++) {
			dectPos.add(dect.get(i).getCurrentPosition());
		}
		xPos.add(x.getCurrentPosition());
		
		Node next = x.gameTreeSearch(dectPos, 'a');
		if(next == null) {
			caught = true;
		} else {
			x.setCurrentPosition(next);
			xPos.set(0, next);
		
			char type;
			for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(xPos.get(0))&& edge.getGoal().equals(next)) {
                        type = edge.getType();
                }
			} 
			
			for(int i=0; i<dect.size(); i++) {
				//System.out.print(i + " ");
//				next = dect.get(i).gameTreeSearch(xPos, 'T');
				next = dect.get(i).greedyAlgorithm(xPos);
				if(next == null) {
					caught = true;
				} else {
					dect.get(i).setCurrentPosition(next);
					dectPos.set(i, next);
				}
			}
			
			if(x.isCaught(dectPos)) {
				caught = true;
			}
		}
	}

}
