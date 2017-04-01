package scotland.yard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
//References: http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
public class Dijkstra {
	private final List<Node> nodes;
    private final List<Edge> edges;
    private Set<Node> closedList;
    private Set<Node> openList;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;
    int fill;
    int mem;
    public  Dijkstra(Graph graph) {
    		fill = 0;
    		mem = 0;
            this.nodes = new ArrayList<Node>(graph.getNodes());
            this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public LinkedList<Node> execute(Node source,Node dest) {
            closedList = new HashSet<Node>();
            openList = new HashSet<Node>();
            distance = new HashMap<Node, Integer>();
            predecessors = new HashMap<Node, Node>();
            distance.put(source, 0);
            openList.add(source);
            mem++;
            while (openList.size() > 0) {
            		mem=Math.max(mem,openList.size());
            		fill=Math.max(fill, closedList.size()+openList.size());
                    Node node = getMin(openList);
                    if(node==dest)
                    	break;
                    closedList.add(node);
                    openList.remove(node);
                    List<Node> adjacentNodes = getNeighbors(node);
                    for (Node target : adjacentNodes) {
                            if (getShortestDistance(target) > getShortestDistance(node)
                                            + getDistance(node, target)) {
                                    distance.put(target, getShortestDistance(node)
                                                    + getDistance(node, target));
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

    private Node getMin(Set<Node> nodes) {
            Node minimum = null;
            for (Node node : nodes) {
                    if (minimum == null) {
                            minimum = node;
                    } else {
                            if (getShortestDistance(node) < getShortestDistance(minimum)) {
                                    minimum = node;
                            }
                    }
            }
            return minimum;
    }

    private int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) {
                return Integer.MAX_VALUE;
        } else {
                return d;
        }
    }
    
    private boolean isClosed(Node node) {
            return closedList.contains(node);
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
    
   
    	
}

