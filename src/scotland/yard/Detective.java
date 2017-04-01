package scotland.yard;

import java.util.ArrayList;
import java.util.List;

public class Detective implements Player {

	Node current;
	Graph g;
	int id;
	int []tickets = new int[3];
	/**
	 * @param args
	 */
	Detective(Node n,int id){
		this.g = new SmallGraph();
		this.current = n;
		this.id = id;
		tickets[0]=10;
		tickets[1]=8;
		tickets[2]=4;
	}
	public int getTickets(String s){
		if(s.equals("taxi"))
			return tickets[0];
		if(s.equals("bus"))
			return tickets[1];
		if(s.equals("underground"))
			return tickets[2];
		return 0;
	}
	public Node getCurrentPosition() {
		return current;
	}
	public void setCurrentPosition(Node n) {
		this.current = n;
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
