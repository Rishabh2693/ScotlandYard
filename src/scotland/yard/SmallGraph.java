package scotland.yard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class SmallGraph implements Graph {
	
	
	 List<Node> nodess;
     List<Edge> edgess;

	SmallGraph(){
		
		nodess = new ArrayList<Node>();
        edgess = new ArrayList<Edge>();
        List<Integer> obs = new ArrayList<Integer>(); 
        	
        	try {
        		String path = new File("").getAbsolutePath();                        
    			File file = new File(path + "/src/NODEXY.txt");
    			FileReader fileReader = new FileReader(file);
    			BufferedReader bufferedReader = new BufferedReader(fileReader);
    			String line;
    			
    			while ((line = bufferedReader.readLine()) != null) {
    				StringTokenizer st = new StringTokenizer(line);
    			    int j=0; 
    			    String []t = new String[3];
    				while (st.hasMoreTokens()) {
    			        t[j]=st.nextToken();
    			        j++;
    			     }
    				Node location = new Node(Integer.parseInt(t[0])-1, "Node_" + Integer.parseInt(t[0]),Integer.parseInt(t[1]),Integer.parseInt(t[2]));
    	            nodess.add(location);
    			}
    			fileReader.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
        	try {
        		String path = new File("").getAbsolutePath();                        
    			File file = new File(path + "/src/nodes.txt");
    			FileReader fileReader = new FileReader(file);
    			BufferedReader bufferedReader = new BufferedReader(fileReader);
    			String line;
    			
    			while ((line = bufferedReader.readLine()) != null) {
    				StringTokenizer st = new StringTokenizer(line);
    			    int j=0; 
    			    String []t = new String[4];
    				while (st.hasMoreTokens()) {
    			        t[j]=st.nextToken();
    			        j++;
    			     }
    				addNewEdge(Integer.parseInt(t[0])-1,Integer.parseInt(t[1])-1,Integer.parseInt(t[2]),t[3].charAt(0));
    	            
    			}
    			fileReader.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
           	
	}
	private void addNewEdge(int sourceLocNo, int destLocNo,
            int cost,char a) {
    Edge lane = new Edge(nodess.get(sourceLocNo), nodess.get(destLocNo), cost, a );
    edgess.add(lane);
	}
	public List<Node> getNodes() {
        return nodess;
	}
	public List<Edge> getEdges() {
        return edgess;
	}
	public static void main(String args[]){
		/*SmallGraph g = new SmallGraph();
		List<Node> a = g.getNodes();
		for(int i=0;i<a.size();i++){
			System.out.println(a.get(i).getId()+" "+a.get(i).getName()+" "+a.get(i).getX()+" "+a.get(i).getY());
		}*/
		
	}
}
