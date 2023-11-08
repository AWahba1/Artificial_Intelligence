package code;
import java.util.Comparator;

public class LLAPSearch extends GenericSearch {

    public static String solve(String inputString, String strategy, boolean visualize) {
    	GenericSearch gs = new GenericSearch();
        SearchProblem problem = new SearchProblem(inputString, strategy, visualize);
        Node reachedNode = null;
        String result = "";
        switch (strategy) {
            case "BF":
                reachedNode = gs.search(problem, new QueueContainer());
                break;
            case "DF": 
                reachedNode = gs.search(problem, new StackContainer());
                break;
            case "ID":
                {   
                    int cutoff = 0;
                    while (reachedNode == null)
                        {
                            problem = new SearchProblem(inputString, strategy, visualize);
                            problem.setCutOff(cutoff);
                            reachedNode = gs.search(problem, new StackContainer());
                            cutoff++;
                        }
                    break;
                }
               
            case "UC":   
                reachedNode = gs.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("UC")));
                break;
            case "GR1":
                reachedNode = gs.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("GR1")));
                break;
            case "GR2": 
                reachedNode = gs.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("GR2")));
                break;
            case "AS1":  
                reachedNode = gs.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("AS1")));
                break;
            case "AS2": 
                reachedNode = gs.search(problem, new PriorityQueueContainer(getComparatorBasedOnStrategy("AS2")));
                break;
        }
        if (reachedNode == null) {
            result = "NOSOLUTION";
        } else {
            result = reachedNode.getPath(visualize) + ";" + reachedNode.getPathCost() + ";" + (problem.getNodesCounter() +1);
        }
        System.out.println("");
        System.out.println(result);
        return result;
    }

    public static Comparator<Node> getComparatorBasedOnStrategy(String strategy){
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
