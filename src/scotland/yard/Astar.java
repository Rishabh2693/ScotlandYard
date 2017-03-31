package scotland.yard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Astar {
	
	private final List<Node> nodes;
    private final List<Edge> edges;
    private Set<Node> closedList;
    private Set<Node> openList;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;
    private Map<Node, Float> total;
    int fill;
    int mem;
    public  Astar(Graph graph) {
    		fill = 0;
    		mem = 0;
            this.nodes = new ArrayList<Node>(graph.getNodes());
            this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public LinkedList<Node> execute(Node source,Node dest) {
            closedList = new HashSet<Node>();
            openList = new HashSet<Node>();
            distance = new HashMap<Node, Integer>();
            total = new HashMap<Node, Float>();
            predecessors = new HashMap<Node, Node>();
            distance.put(source, 0);
            ///Add heuristic function on the following line
            total.put(source,0+diff(source,dest));
            openList.add(source);
            while (openList.size() > 0) {
            	    mem=Math.max(mem,openList.size());
            	    fill=Math.max(fill, closedList.size()+openList.size());
                    Node node = getMinimum(openList);
                    if(node==dest)
                    	break;
                    closedList.add(node);
                    openList.remove(node);
                    List<Node> adjacentNodes = getNeighbors(node);
                    for (Node target : adjacentNodes) {
                            if (getShortestTotal(target) > getShortestTotal(node)
                                            + getDistance(node, target)) {
                                    distance.put(target, (int) (getShortestTotal(node)
                                                    + getDistance(node, target)));
                                  ///Add heuristic function on the following line
                                    total.put(target, distance.get(target)+diff(target,dest));
                                    predecessors.put(target, node);
                                    openList.add(target);
                            }
                    }
            }
           return getPath(dest); 
    }

    private List<Node> getNeighbors(Node node) {
            List<Node> neighbors = new ArrayList<Node>();
            for (Edge edge : edges) {
                    if (edge.getSource().equals(node)
                                    && !isClosed(edge.getGoal())) {
                            neighbors.add(edge.getGoal());
                    }
            }
            return neighbors;
    }

    private int getDistance(Node node, Node target) {
        for (Edge edge : edges) {
                if (edge.getSource().equals(node)
                                && edge.getGoal().equals(target)) {
                        return edge.getWeight();
                }
        }
        throw new RuntimeException("Should not happen");
    }
    
    private Node getMinimum(Set<Node> vertexes) {
            Node minimum = null;
            for (Node vertex : vertexes) {
                    if (minimum == null) {
                            minimum = vertex;
                    } else {
                            if (getShortestTotal(vertex) < getShortestTotal(minimum)) {
                                    minimum = vertex;
                            }
                    }
            }
            return minimum;
    }

    private Float getShortestTotal(Node destination) {
        Float d = total.get(destination);
        if (d == null) {
                return Float.MAX_VALUE;
        } else {
                return d;
        }
    }
    
    private boolean isClosed(Node vertex) {
            return closedList.contains(vertex);
    }

    public LinkedList<Node> getPath(Node target) {
            LinkedList<Node> path = new LinkedList<Node>();
            Node step = target;
            if (predecessors.get(step) == null) {
                    return null;
            }
            path.add(step);
            while (predecessors.get(step) != null) {
                    step = predecessors.get(step);
                    path.add(step);
            }
            Collections.reverse(path);
            return path;
    }
    private float manhattan(Node source,Node dest){
		
    	return Math.abs(source.getX()-dest.getX())+Math.abs(source.getY()-dest.getY());
    	
    }
    private float euclid(Node source,Node dest){
		
    	return (float) Math.sqrt((Math.abs(source.getX()-dest.getX())*Math.abs(source.getX()-dest.getX()))+(Math.abs(source.getY()-dest.getY())*Math.abs(source.getY()-dest.getY())));
    	
    }
    private float diff(Node source,Node dest){
		
    	return (float) Math.abs(source.getId()-dest.getId());
    	
    }
    public static void main(String[] args){
    	
                 Graph graph = LargeGraph.getInstance();
                 Astar dijkstra = new Astar(graph);
                 LinkedList<Node> path = dijkstra.execute(graph.getNodes().get(0),graph.getNodes().get(99));
                  
                 if(path==null) 
                 {
                	 System.out.println("Node is not connected in the random graph Re Run");
                	 return;
                 }
                 
                 for (Node vertex : path) {
                         System.out.println(vertex);
                 }
                 System.out.println( dijkstra.fill+" "+dijkstra.mem);
         }

        
    	
    }

