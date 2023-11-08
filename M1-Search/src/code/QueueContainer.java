package code;
import java.util.LinkedList;
import java.util.Queue;

public class QueueContainer implements DataContainer {
    
    private Queue<Node> queue;
    public QueueContainer() {
        queue = new LinkedList<Node>();
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
