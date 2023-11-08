package code;
import java.util.*;
public class PriorityQueueContainer implements DataContainer {
    
    private PriorityQueue<Node> queue;
    public PriorityQueueContainer( Comparator<Node> comparator) {
        queue = new PriorityQueue<Node>(comparator);
    }
    
    public void add(Node node) {
        queue.add(node);
    }
    
    public Node remove() {
        return queue.remove();
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
