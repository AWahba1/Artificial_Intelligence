package code;
import java.util.*;
public class StackContainer implements DataContainer {
    
    private Stack<Node> stack;
    public StackContainer() {
        stack = new Stack<Node>();
    }
    
    public void add(Node node) {
        stack.add(node);
    }
    
    public Node remove() {
        return stack.pop();
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
