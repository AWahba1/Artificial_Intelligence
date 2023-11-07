public class GenericSearch {

    // Generic Search Problem method
    public void search(SearchProblem problem, DataContainer dataContainer) {
        dataContainer.add(problem.getRoot());
        while (true) {
            if (dataContainer.isEmpty()) {
                return;
            }
            Node node = dataContainer.remove();
            if (problem.isGoal(node.getState())) {
                return;
            }

            // expand logic
            List<Node> children = problem.expand(node);
            for (Node child : children) {
                dataContainer.add(child);
            }
        }
    }
    
}
    