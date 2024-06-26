package code;

import java.util.List;

public class GenericSearch {

    // Generic Search Problem method
    public Node search(SearchProblem problem, DataContainer dataContainer) {
        dataContainer.add(problem.getRoot());
        while (true) {
            if (dataContainer.isEmpty()) {
                return null;
            }
            Node node = dataContainer.remove();
            if (problem.isGoalState(node)) {
                return node;
            }

            List<Node> children = problem.expand(node);
            for (Node child : children) {
                dataContainer.add(child);
            }
        }
    }
    
}
    