package code;
import java.util.Stack;

enum Operator {
    REQUEST_FOOD,
    REQUEST_MATERIAL, 
    REQUEST_ENERGY,
    WAIT, 
    BUILD1,
    BUILD2
}

public class Node {
    private State state;
    private Node parent;
    private int depth;
    private int pathCost; // equivalent to amount spent
    private Operator operatorApplied;


    public Node(State state, Node parent, int depth, int pathCost, Operator operatorApplied) {
        this.state = state;
        this.parent = parent;
        this.depth = depth;
        this.pathCost = pathCost;
        this.operatorApplied = operatorApplied;
    }

    public State getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    public int getPathCost() {
        return pathCost;
    }

    public Operator getOperatorApplied() {
        return operatorApplied;
    
    }

    public int getHeuristicValue_1() {
        return (int) (100 - this.state.getProsperity()) / Math.max(SearchProblem.prosperityBUILD1, SearchProblem.prosperityBUILD2);
    }

    public int getHeuristicValue_2() {
        int buildsRemaining_1 =  (int) (100 - this.state.getProsperity()) / SearchProblem.prosperityBUILD1;
        int buildsRemaining_2 =  (int) (100 - this.state.getProsperity()) / SearchProblem.prosperityBUILD2;
        int foodNeeded_1 = Math.max(buildsRemaining_1 * SearchProblem.foodUseBUILD1 - this.state.getFood(), 0);
        int foodNeeded_2 = Math.max(buildsRemaining_2 * SearchProblem.foodUseBUILD2 - this.state.getFood(), 0);
        return Math.min(buildsRemaining_1 + foodNeeded_1, buildsRemaining_2 + foodNeeded_2);
    }

    public String getPath(boolean visualize) {
        Stack<Node> stack = new Stack<Node>();
        Node node = this;
        String result = "";
        while (node.getParent() != null) {
            stack.push(node);
            node = node.getParent();
        }
        
        if (visualize) {
	    	System.out.println("State: " + node.getState());
	    	System.out.println("Money_Spent: " + node.getPathCost());
	    	System.out.println("----------------------------------");
        }
    	
        while (!stack.isEmpty()) {
        	Node n = stack.pop();
        	       	
        	if (visualize)
        	{
        		System.out.println("State: " + n.getState());
            	System.out.println("Money_Spent: " + n.getPathCost());
        	}
        	
            switch(n.getOperatorApplied()) {
                case REQUEST_FOOD:
                    result += "RequestFood,";
                    break;
                case REQUEST_MATERIAL:
                    result += "RequestMaterials,";
                    break;
                case REQUEST_ENERGY:
                    result += "RequestEnergy,";
                    break;
                case WAIT:
                    result += "WAIT,";
                    break;
                case BUILD1:
                    result += "BUILD1,";
                    break;
                case BUILD2:
                    result += "BUILD2,";
                    break;
            }
            
            if (visualize)
            {
            	if (n.getOperatorApplied()!=null)
            	{
            		System.out.println("Operator_Applied: " + n.getOperatorApplied());
                	System.out.println("----------------------------------");
            	}
            }
            
            
        }
        return result.substring(0, result.length() - 1);
    }

}