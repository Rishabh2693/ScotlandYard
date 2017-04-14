package scotland.yard;

import java.util.ArrayList;
import java.util.List;

public class Detective implements Player {

	Node current;
	SmallGraph g;
	int id;
	int []tickets = new int[3];
	List<Node> possible_locations;
	int temp = 0;
	/**
	 * @param args
	 */
	Detective(SmallGraph g, Node n,int id){
		this.g = g;
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
		g.removeEdge(107, 114);
		g.removeEdge(156, 114);
		g.removeEdge(114, 156);
		g.removeEdge(114, 107);
		g.removeEdge(156, 193);
		g.removeEdge(193, 156);
		List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !edge.getGoal().getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        }
        
        g.addEdge(107, 114);
		g.addEdge(156, 114);
		g.addEdge(114, 156);
		g.addEdge(114, 107);
		g.addEdge(156, 193);
		g.addEdge(193, 156);
        if(neighbors.size() > 0) {
	        int r = (int) Math.floor((0+Math.random()*(neighbors.size()-0.01)));
	        current.occupied=false;
	        Node n = neighbors.get(r);
	        n.occupied = true;
	        return n;
        } else {
        	return null;
        }
        
	}

	public Node greedyAlgorithm(List<Node> mrX) {
		List<Node> neighbors = new ArrayList<Node>();
		g.removeEdge(107, 114);
		g.removeEdge(156, 114);
		g.removeEdge(114, 156);
		g.removeEdge(114, 107);
		g.removeEdge(156, 193);
		g.removeEdge(193, 156);
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !edge.getGoal().getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        } 
        //System.out.println(" " + neighbors.size() + " " + mrX.get(0) + " " + neighbors.get(0));
        Dijkstra dijkstra = new Dijkstra(g);
        List<Node> path = dijkstra.execute(mrX.get(0),neighbors.get(0));
        Node closest = neighbors.get(0);
        if(path != null) {
	        int min = path.size();
	        for(int i=0;i<neighbors.size();i++){
	        	//System.out.println(neighbors.get(i));
	        	path = dijkstra.execute(mrX.get(0),neighbors.get(i));
	        	if(path != null)
		        	if(min>path.size())
		        	{
		        		if(!neighbors.get(i).occupied)
		        		{	min = dijkstra.execute(mrX.get(0),neighbors.get(i)).size();
		        			closest = neighbors.get(i);
		        		}
		        	}
	        	
	        }
        }
        closest.occupied = true;
        current.occupied = false;
        g.addEdge(107, 114);
		g.addEdge(156, 114);
		g.addEdge(114, 156);
		g.addEdge(114, 107);
		g.addEdge(156, 193);
		g.addEdge(193, 156);
		return closest;
	}

	
public Node gameTreeSearch(List<Node> last_mrX, char t) 
{
		List<Node> old_possible_locations = new ArrayList<Node>();
		old_possible_locations = possible_locations;
		
//		System.out.println(possible_locations);
//		System.out.println(temp);
//		System.out.println(last_mrX);
		if(old_possible_locations==null)
		{
			possible_locations = last_mrX;
		}
		else if(old_possible_locations.size()!=0)
		{
			possible_locations = new ArrayList<Node>();
			for(int i =0; i < old_possible_locations.size();i++)
			{
				for (Edge edge : g.getEdges()) 
				{
					if (edge.getSource().equals(old_possible_locations.get(i))&& !edge.getGoal().getOccupied() && edge.getType()==t && !possible_locations.contains(edge.getGoal())) 
					{
						possible_locations.add(edge.getGoal());
					}
				} 
			}
		}
		
		g.removeEdge(107, 114);
		g.removeEdge(156, 114);
		g.removeEdge(114, 156);
		g.removeEdge(114, 107);
		g.removeEdge(156, 193);
		g.removeEdge(193, 156);
		// get Detective's neighbours
		List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : g.getEdges()) {
                if (edge.getSource().equals(current)
                                && !edge.getGoal().getOccupied()) {
                        neighbors.add(edge.getGoal());
                }
        } 
        
        Dijkstra dijkstra = new Dijkstra(g);
        int min = 10;
        if(neighbors.size()==0){
        	g.addEdge(107, 114);
			g.addEdge(156, 114);
			g.addEdge(114, 156);
			g.addEdge(114, 107);
			g.addEdge(156, 193);
			g.addEdge(193, 156);
			
        	return null;
        }
        Node closest = neighbors.get(0);
        for(int j =0; j < possible_locations.size(); j++)
        {
        	for(int i=0;i<neighbors.size();i++){
        		if(possible_locations.get(j)!=neighbors.get(i))
        		{
        			if(min>dijkstra.execute(possible_locations.get(j),neighbors.get(i)).size())
        			{
        				if(!neighbors.get(i).occupied)
        				{	
        					min = dijkstra.execute(possible_locations.get(j),neighbors.get(i)).size();
        					closest = neighbors.get(i);
        				}
        			}		
        		}
        		
        		else {
        			current.occupied = false;
        			possible_locations.get(j).occupied=true;
        			
        			
        			if(temp!=0 && temp!=last_mrX.get(0).getId())
        			{	
//        	        	System.out.println("AM I HERE?");
        				possible_locations = null;
        			}
        	        temp=last_mrX.get(0).getId();
        	        g.addEdge(107, 114);
        			g.addEdge(156, 114);
        			g.addEdge(114, 156);
        			g.addEdge(114, 107);
        			g.addEdge(156, 193);
        			g.addEdge(193, 156);
        			return neighbors.get(i);
        		}
        	}        	
        }
        
//        System.out.println(temp);
        if(temp!=0 && temp!=last_mrX.get(0).getId())
		{	
        	System.out.println("AM I HERE?");
			possible_locations = null;
		}
        temp=last_mrX.get(0).getId();
        
        closest.occupied = true;
        current.occupied = false;
        g.addEdge(107, 114);
		g.addEdge(156, 114);
		g.addEdge(114, 156);
		g.addEdge(114, 107);
		g.addEdge(156, 193);
		g.addEdge(193, 156);
		return closest;
        
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
