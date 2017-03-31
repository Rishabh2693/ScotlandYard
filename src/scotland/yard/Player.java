package scotland.yard;

public interface Player {
	Node getCurrentPosition();
	Node randomAlgorithm();
	Node greedyAlgorithm();
	Node gameTreeSearch();
}
