package scotland.yard;

public class Edge {
     private final Node source;
     private final Node goal;
     private final int weight;
     private final char type;
     public Edge(Node source, Node goal, int weight,char type) {
            this.source = source;
            this.goal = goal;
            this.weight = weight;
            this.type = type;
     }

     public Node getGoal() {
            return goal;
     }

     public Node getSource() {
            return source;
     }
     public int getWeight() {
            return weight;
     }
     public char getType() {
         return type;
  }

     @Override
     public String toString() {
             return source + " " + goal;
     }
}
