import java.util.*;
public class LLAP extends GenericSearch {

    public void solve(String inputString, String strategy, boolean visualize) {
        SearchProblem problem = new SearchProblem(inputString);
        Node reachedNode = null;
        String result = "";
        switch (strategy) {
            case "BF":
                reachedNode = super.search(problem, new QueueContainer());
                break;
            case "DF": case "ID":
                reachedNode = super.search(problem, new StackContainer());
                break;
            case "UC":   
                reachedNode = super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("UC")));
                break;
            case "GR1":  // separate between 2 heuristics
                reachedNode = super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("GR1")));
                break;
            case "GR2":  // separate between 2 heuristics
                reachedNode = super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("GR2")));
                break;
            case "AS1":  // separate between 2 heuristics
                reachedNode = super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("AS1")));
                break;
            case "AS2":  // separate between 2 heuristics
                reachedNode = super.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("AS2")));
                break;
        }
        if (reachedNode == null) {
            result = "NOSOLUTION";
        } else {
            result = reachedNode.getPath() + ";" + reachedNode.getPathCost() + ";" + problem.getNodesCounter();
        }
    }

    public Comparator<Node> getComparatorBasedOnStrategy(String strategy){
        switch(strategy){
            case "GR1": return new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.getHeuristicValue_1() - o2.getHeuristicValue_1();
                    }
                };
            case "GR2": return new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.getHeuristicValue_2() - o2.getHeuristicValue_2();
                    }
                };
            case "AS1": return new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.getPathCost() - o2.getPathCost() + o1.getHeuristicValue_1()
                                - o2.getHeuristicValue_1();
                    }
                };
            case "AS2": return new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.getPathCost() - o2.getPathCost() + o1.getHeuristicValue_2()
                                - o2.getHeuristicValue_2();
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
