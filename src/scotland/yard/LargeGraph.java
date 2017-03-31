package scotland.yard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LargeGraph implements Graph {
	
     public static LargeGraph instance;
	 static List<Node> nodess;
     static List<Edge> edgess;
     public static synchronized LargeGraph getInstance(){
    	 if(instance == null)
    		 instance = new LargeGraph();

 		return instance;
     }
     LargeGraph(){
		
		nodess = new ArrayList<Node>();
        edgess = new ArrayList<Edge>();
        for (int i = 0; i < 40000; i++) {
            Node location = new Node(i, "Node_" + i,(int)Math.floor(i%200)*5,(int)Math.floor(i/200)*5);
            nodess.add(location);
        }    	
        for(int i=0;i<40000;i++){
    		int r = (int) (0+Math.random()*10);
    		if(r<7&&i>200){
    				addNewEdge(i,i-200,5,'u');
    		}
    		r = (int) (0+Math.random()*10);
    		if(r<7&&i+200<40000){
    				addNewEdge(i,i+200,5,'u');
    		}
    		r = (int) (0+Math.random()*10);
    		if(r<7&&i%200>0){
    				addNewEdge(i,i-1,5,'u');
    		}
    		r = (int) (0+Math.random()*10);
    		if(r<7&&i%200<199){
    				addNewEdge(i,i+1,5,'u');
    		}
    	
        }
    
        
	}
	private static void addNewEdge(int sourceLocNo, int destLocNo,
            int cost,char a) {
    Edge lane = new Edge(nodess.get(sourceLocNo), nodess.get(destLocNo), cost,a );
    edgess.add(lane);
	}
	
	public List<Node> getNodes() {
        return nodess;
	}
	public List<Edge> getEdges() {
        return edgess;
	}
	
}
