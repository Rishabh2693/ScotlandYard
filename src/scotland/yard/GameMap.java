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
	int pos, xChance = 0;
	List<Node> dectPos = new ArrayList<Node>();
	List<Node> xPos = new ArrayList<Node>();
	Node lastPos;
	PImage background, xBoard;
	boolean caught = false;
	List<PImage> tickets = new ArrayList<PImage>();	//bus, taxi, underground, black, x2
	
	SmallGraph g;
	int Number_of_Detectives = 5;
	List<Detective> dect = new ArrayList<Detective>();
	MrX x;
	char type;
	List<Character> ticket_used = new ArrayList<Character>();
	
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
		lastPos = new Node();
		String path = new File("").getAbsolutePath();
		background = loadImage(path + "/src/ScotlandYardBG.jpg");
		background.resize(1000, 800);
		
		xBoard = loadImage(path + "/src/log.jpeg");
		tickets.add(loadImage(path + "/src/bus.png"));
		tickets.add(loadImage(path + "/src/taxi.png"));
		tickets.add(loadImage(path + "/src/underground.png"));
		tickets.add(loadImage(path + "/src/black-ticket.jpg"));
		tickets.add(loadImage(path + "/src/2x.png"));
		
		for(int i=0; i<tickets.size(); i++) {
			tickets.get(i).resize(45, 28);
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
		xBoard.resize(200, 250);
		image(xBoard, 1000, 0);
		
		// Draw ticket on MrX Board
		for(int i = 0; i < ticket_used.size(); i++)
		{
			if(ticket_used.get(i)=='B')
				image(tickets.get(0), 1015+65*((i)/8), 20+28*((i)%8));
			else if(ticket_used.get(i)=='T')
				image(tickets.get(1), 1015+65*((i)/8), 20+28*((i)%8));
			else if(ticket_used.get(i)=='U')
				image(tickets.get(2), 1015+65*((i)/8), 20+28*((i)%8));
			else if(ticket_used.get(i)=='K')
				image(tickets.get(3), 1015+65*((i)/8), 20+28*((i)%8));
		}
		
		//for(int i=0; i<tickets.size(); i++) {
			//image(tickets.get(i), 1000, tickets.get(i).height*i);
		//}
		
		if(frameCount % 10 == 0 && !caught && xChance < 23) {
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
//		if(xChance == 3 || xChance == 8 || xChance == 13 || xChance == 18 || xChance == 23 || caught) {
			stroke(255);
			noFill();
			strokeWeight(4);
			rect(x.getCurrentPosition().getX()-10, x.getCurrentPosition().getY()-10, 20, 20);
//		}
	}
	
	public void gameLoop() {
		xChance++;
		
		System.out.println(xChance);
		Node temp = new Node();
		
		if(xChance == 1) {
			for(int i=0; i<dect.size(); i++) {
				dectPos.add(dect.get(i).getCurrentPosition());
			}
			xPos.add(temp);
		}
		//xPos.add(x.getCurrentPosition());

		lastPos = x.current;
//		Node next = x.gameTreeSearch(dectPos, 'a');
//		Node next = x.randomAlgorithm();
		Node next = x.greedyAlgorithm1(dectPos);
		if(next == null) {
			caught = true;
		} else {
			x.setCurrentPosition(next);
			if(xChance == 3 || xChance == 8 || xChance == 13 || xChance == 18 || xChance == 23) {
				xPos.set(0, next);
			}
//			lastPos = next;
			type = 'T';
			if(xChance>=1){
			for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(lastPos)&& edge.getGoal().equals(next)) {
                        type = edge.getType();
                        ticket_used.add(type);
//                        System.out.println("NOw : "+edge.getSource().getId() + type);
                        break;
                }
			} 
			}
			
			lastPos = next;
			stroke(255);
			fill(0);
			rect(1015, 280, 150, 280);
			for(int i=0; i<dect.size(); i++) {
				textSize(12);
//				System.out.println(dect.size());
//				System.out.println(i);
				int no = i+1;
				fill(colorMap.get(i)[0], colorMap.get(i)[1], colorMap.get(i)[2]);
				text("Detective "+no, 1025, 300+(i*55));
				fill(255);
				String str = "Taxi: "+dect.get(i).tickets[0]+" Bus: "+dect.get(i).tickets[1]+" Underground: "+dect.get(i).tickets[2];
				text(str, 1025, 305+(i*55), 125, 100);
				if(xPos.get(0).getId() == 0) {
					next = dect.get(i).randomAlgorithm();
				} 
				else {
					next = dect.get(i).randomAlgorithm();
//					next = dect.get(i).greedyAlgorithm(xPos);
//					next = dect.get(i).gameTreeSearch(xPos, type);
					if(next==null || next.getId()==666){
						continue;	
					}
					
				}
				if(next == null) {
//					caught = true;
//					textSize(20);
//					fill(0);
//					text("Game Over!", 1050, 650);
//					text("Detectives Wins!", 1020, 700);
				} else {
					dect.get(i).setCurrentPosition(next);
					dectPos.set(i, next);
					
				}
			}
			
			if(x.isCaught(dectPos)) {
				caught = true;
				System.out.println("Here");
				textSize(20);
				fill(0);
				text("Game Over!", 1050, 650);
				text("Detectives Wins!", 1020, 700);
			} else {
				if(xChance == 23) {
					textSize(20);
					fill(0);
					text("Game Over!", 1050, 650);
					text("Mr X Wins!", 1030, 700);
				}
			}
		}
	}

}
