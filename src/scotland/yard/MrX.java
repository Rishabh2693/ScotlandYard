package scotland.yard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MrX implements Player {

	Node current;
	Graph g;
	int tickets [] = new int [2];
	/**
	 * @param args
	 */
	MrX(Graph graph) {
		this.g = graph;
		tickets[0]=2;
		tickets[1]=4;
	}
	public int getTickets(String s){
		if(s.equals("2x"))
			return tickets[0];
		if(s.equals("black"))
			return tickets[1];
		return 0;
	}
	public Node getCurrentPosition() {
		return current;
	}
	public void setCurrentPosition(Node n) {
		this.current = n;
	}
	public boolean isCaught(List<Node> detectives){
		for(int i=0;i<detectives.size();i++){
			if(detectives.get(i).getId()==current.getId())
				return true;
		}
		return false;
	}
	public void chooseGreedyInit(List<Detective> detectives, int[] startNodes) {
		Dijkstra d = new Dijkstra(g);
		Node n = new Node();
		int len = 0, pos, dPos, max;
		max = len;
		for(int index = 0; index < startNodes.length; index++) {
			pos = startNodes[index] - 1;
			if(!g.getNodes().get(pos).getOccupied()) {
				len = 0;
				for(int i=0; i<detectives.size(); i++) {
					//System.out.println(detectives.get(i).getCurrentPosition() + " MrX " + g.getNodes().get(pos));
					len+= d.execute(detectives.get(i).getCurrentPosition(), g.getNodes().get(pos)).size();
				}
				//System.out.println("Max: " + max + "Leng: " +len);
				if(max < len) {
					max = len;
					n = g.getNodes().get(pos);
				}
			}
		}
		this.current = n;
	}
 
	public Node randomAlgorithm() {
		List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !edge.getGoal().getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        }
        
        if(neighbors.size() > 0) {
	        int r = (int) Math.floor((0+Math.random()*(neighbors.size()-0.01)));
	        Node n = neighbors.get(r);
	        return n;
        }
        else {
        	return null;
        }
        
	}

	//maximize minimum
	public Node greedyAlgorithm(List<Node> detectives) {
		
		List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !edge.getGoal().getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        }
        if(neighbors.size()==0)
        	return null;
        Dijkstra dijkstra = new Dijkstra(g);
        List<Node> path = dijkstra.execute(detectives.get(0),current);
        Node closest = detectives.get(0);
        if(path != null) {
	        int min = path.size();
	        for(int i=0;i<detectives.size();i++){
	        	if(min>dijkstra.execute(detectives.get(i),current).size())
	        	{
	        		min = dijkstra.execute(detectives.get(i),current).size();
	        		closest = detectives.get(i);
	        	}
	        	
	        }
        }
        path = dijkstra.execute(closest,neighbors.get(0));
        Node move = neighbors.get(0);
        if(path != null) {
	        int max = path.size();
	        for(int i=0;i<neighbors.size();i++){
	        	if(max<dijkstra.execute(closest,neighbors.get(i)).size())
	        	{
	        		max = dijkstra.execute(neighbors.get(i),closest).size();
	        		move = neighbors.get(i);
	        	}
	        	
	        }
        }
        
		return move;
	}
	
	//maximize sum of distance
	public Node greedyAlgorithm1(List<Node> detectives) {
		
		List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !edge.getGoal().getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        }
        if(neighbors.size()==0)
        	return null;
        Dijkstra dijkstra = new Dijkstra(g);
        List<Node> path;
        int max = 0;
        Node move = neighbors.get(0);
        for(int i=0;i<neighbors.size();i++){
        	int tempSum = 0;
        	for(int j=0;j<detectives.size();j++) {
        		path = dijkstra.execute(detectives.get(j),neighbors.get(i));
        		if(path != null)
        			tempSum += path.size();
        	}
        	if(max<tempSum) {
        		max = tempSum;
        		move = neighbors.get(i);
        	}
        	
        }
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
