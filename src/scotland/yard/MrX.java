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

	public Node greedyAlgorithm() {
		// TODO Auto-generated method stub
		return null;
	}

	public Node gameTreeSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
