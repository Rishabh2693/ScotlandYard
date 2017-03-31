package scotland.yard;

import java.util.ArrayList;
import java.util.List;

public class MrX implements Player {

	Node current;
	Graph g;
	/**
	 * @param args
	 */
	MrX(Node n){
		this.g = new SmallGraph();
		this.current = n;
	}
	public Node getCurrentPosition() {
		return current;
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
		return move;
	}
	
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
