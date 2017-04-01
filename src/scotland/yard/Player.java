package scotland.yard;

import java.util.List;

public interface Player {
	Node getCurrentPosition();
	Node randomAlgorithm();
	Node greedyAlgorithm(List<Node> detectives);
	Node gameTreeSearch(List<Node> last_mrX, char t);
	
}
