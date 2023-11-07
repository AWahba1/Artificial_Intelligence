import java.util.*;
public class LLAP extends GenericSearch {

    public void solve(String inputString, String strategy, boolean visualize) {
        SearchProblem problem = new SearchProblem(inputString);
        switch (strategy) {
            case "BF":
                super.search(problem, new QueueContainer());
                break;
            case "DF": case "ID":
                super.search(problem, new StackContainer());
                break;
            case "UC":   
                super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("UC")));
                break;
            case "GR1":  // separate between 2 heuristics
                super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("GR1")));
                break;
            case "GR2":  // separate between 2 heuristics
                super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("GR2")));
                break;
            case "AS1":  // separate between 2 heuristics
                super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("AS1")));
                break;
            case "AS2":  // separate between 2 heuristics
                super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("AS2")));
                break;
        }
    }

    public Comparator<Node> getComparatorBasedOnStrategy(String strategy){
        switch(strategy){
            case "GR1": return new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.getHeuristicValue() - o2.getHeuristicValue();
                    }
                };
            case "GR2": return new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.getHeuristicValue() - o2.getHeuristicValue();
                    }
                };
            case "AS1": return new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.getPathCost() - o2.getPathCost() + o1.getHeuristicValue()
                                - o2.getHeuristicValue();
                    }
                };
            case "AS2": return new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.getPathCost() - o2.getPathCost() + o1.getHeuristicValue()
                                - o2.getHeuristicValue();
                    }
                }; 
            default: return new Comparator<Node>() { // UC Case
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.getPathCost() - o2.getPathCost();
                    }
                };
        }
        

    }
   
}
