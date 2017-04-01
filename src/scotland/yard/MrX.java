package scotland.yard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MrX implements Player {

	Node current;
	Graph g;
	/**
	 * @param args
	 */
	MrX() {
		this.g = new SmallGraph();
		//this.current = n;
	}
	public Node getCurrentPosition() {
		return current;
	}
	public void setCurrentPosition(Node n) {
		this.current = n;
	}
	
	public void chooseGreedyInit(List<Detective> detectives, int[] startNodes) {
		Dijkstra d = new Dijkstra(g);
		Node n = new Node();
		int len = 0, pos, dPos, max;
		max = len;
		for(int index = 0; index < startNodes.length; index++) {
			pos = startNodes[index] - 1;
			if(!g.getNodes().get(pos).occupied) {
				len = 0;
				for(int i=0; i<detectives.size(); i++) {
					//System.out.println(detectives.get(i).getCurrentPosition() + " MrX " + g.getNodes().get(pos));
					dPos = detectives.get(i).getCurrentPosition().getId()-1;
					len += d.execute(g.getNodes().get(dPos), g.getNodes().get(pos)).size();
				}
				//System.out.println("Max: " + max + "Leng: " +len);
				if(max < len) {
					max = len;
					n = g.getNodes().get(pos);
				}
			}
		}
		this.current = n;
		this.current.occupied = true;
	}
 
	public Node randomAlgorithm() {
		List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !current.getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        }
        int r = (int) Math.floor((0+Math.random()*(neighbors.size()-0.01)));
        current.occupied=false;
        Node n = neighbors.get(r);
        n.occupied = true;
        return n;
        
	}

	//maximize minimum
	public Node greedyAlgorithm(List<Node> detectives) {
		
		List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !current.getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        }
        Dijkstra dijkstra = new Dijkstra(g);
        int min = dijkstra.execute(detectives.get(0),current).size();
        Node closest = detectives.get(0);
        for(int i=0;i<detectives.size();i++){
        	if(min>dijkstra.execute(detectives.get(i),current).size())
        	{
        		min = dijkstra.execute(detectives.get(i),current).size();
        		closest = detectives.get(i);
        	}
        	
        }
        int max = dijkstra.execute(closest,neighbors.get(0)).size();
        Node move = neighbors.get(0);
        for(int i=0;i<neighbors.size();i++){
        	if(max<dijkstra.execute(closest,neighbors.get(i)).size())
        	{
        		max = dijkstra.execute(neighbors.get(i),closest).size();
        		move = neighbors.get(i);
        	}
        	
        }
        
        move.occupied = true;
        current.occupied = false;
		return move;
	}
	
	//maximize sum of distance
	public Node greedyAlgorithm1(List<Node> detectives) {
		
		List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !current.getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        }
        Dijkstra dijkstra = new Dijkstra(g);
        
        int max = 0;
        Node move = neighbors.get(0);
        for(int i=0;i<neighbors.size();i++){
        	int tempSum = 0;
        	for(int j=0;j<detectives.size();j++)
        		tempSum+=dijkstra.execute(detectives.get(j),neighbors.get(i)).size();
        	if(max<tempSum)
        	{
        		max = tempSum;
        		move = neighbors.get(i);
        	}
        	
        }
        move.occupied = true;
        current.occupied = false;
		return move;
	}
	public Node gameTreeSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	


}
