import java.util.*;

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

    public int getHeuristicValue() {
        // heuristic fn logic
    }
}