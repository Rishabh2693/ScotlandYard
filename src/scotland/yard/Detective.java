package scotland.yard;

import java.util.ArrayList;
import java.util.List;

public class Detective implements Player {

	Node current;
	Graph g;
	int id;
	/**
	 * @param args
	 */
	Detective(Node n,int id){
		this.g = new SmallGraph();
		this.current = n;
		this.id = id;
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

	public Node greedyAlgorithm(List<Node> mrX) {
		List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !current.getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        } 
        Dijkstra dijkstra = new Dijkstra(g);
        int min = dijkstra.execute(mrX.get(0),neighbors.get(0)).size();
        Node closest = neighbors.get(0);
        for(int i=0;i<neighbors.size();i++){
        	if(min>dijkstra.execute(mrX.get(0),neighbors.get(i)).size())
        	{
        		if(!neighbors.get(i).occupied)
        		{	min = dijkstra.execute(mrX.get(0),neighbors.get(i)).size();
        			closest = neighbors.get(i);
        		}
        	}
        	
        }
        
        closest.occupied = true;
        current.occupied = false;
		return closest;
	}

	public Node gameTreeSearch() {
		return null;
	}
	public static void main(String[] args) {
		Graph g = new SmallGraph();
		Detective []d = new Detective[5];
	//	int r = (int) Math.floor((0+Math.random()*(20-0.01)));
	//	MrX x = new MrX(r,);
		
	//	Node [5]init = 
	//	for(int i=0;i<5;i++){
	//		d[i]=new Detective();
	//	}

	}


}
