import java.util.Iterator;
import java.util.Stack;

/**
 * This class represents an iterable multi-key red-black tree, extending the functionality of a
 * standard red-black tree to support multiple keys per node.
 *
 * @param <T> The type of the keys stored in the tree, which must be comparable.
 */
public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T> {

    private T iterationStartPoint = null; // The starting point for iteration
    private int totalKeys = 0; // The total number of keys in the tree

    /**
     * Inserts a single key into the tree.
     *
     * @param key The key to insert.
     * @return true if a new node was created, false if the key was added to an existing node.
     */
    @Override
    public boolean insertSingleKey(T key) {
        KeyList<T> newKeyList = new KeyList<>(key);
        Node<KeyListInterface<T>> existingNode = this.findNode(newKeyList);

        if (existingNode != null) {
            existingNode.data.addKey(key);
            totalKeys++;
            return false;
        }

        this.insert(newKeyList);
        totalKeys++;
        return true;
    }

    /**
     * Returns the total number of keys in the tree.
     *
     * @return The total number of keys.
     */
    @Override
    public int numKeys() {
        return totalKeys;
    }

    /**
     * Sets the starting point for iteration.
     *
     * @param startPoint The starting point for iteration.
     */
    @Override
    public void setIterationStartPoint(Comparable<T> startPoint) {
        this.iterationStartPoint = (T) startPoint;
    }

    /**
     * Returns an iterator over the keys in the tree.
     *
     * @return An iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Stack<Node<KeyListInterface<T>>> nodeStack = initializeStack();
            private Iterator<T> keyIterator = !nodeStack.isEmpty() ? nodeStack.peek().data.iterator() : null;

            /**
             * Initializes the stack for iteration, starting from the iteration start point.
             *
             * @return The initialized stack.
             */
            private Stack<Node<KeyListInterface<T>>> initializeStack() {
                Stack<Node<KeyListInterface<T>>> stack = new Stack<>();
                Node<KeyListInterface<T>> current = root;

                while (current != null) {
                    if (iterationStartPoint == null || current.data.iterator().next().compareTo(iterationStartPoint) >= 0) {
                        stack.push(current);
                        current = current.down[0];
                    } else {
                        current = current.down[1];
                    }
                }

                return stack;
            }

            /**
             * Checks if there are more keys to iterate over.
             *
             * @return true if there are more keys, false otherwise.
             */
            @Override
            public boolean hasNext() {
                return (keyIterator != null && keyIterator.hasNext()) || !nodeStack.isEmpty();
            }

            /**
             * Returns the next key in the iteration.
             *
             * @return The next key.
             */
            @Override
            public T next() {
                while (keyIterator == null || !keyIterator.hasNext()) {
                    if (nodeStack.isEmpty()) {
                        return null; // or throw new NoSuchElementException();
                    }
                    Node<KeyListInterface<T>> nextNode = nodeStack.pop();
                    keyIterator = nextNode.data.iterator();
                    pushLeftChildren(nextNode.down[1]);
                }
                return keyIterator.next();
            }

            /**
             * Pushes the left children of a node onto the stack.
             *
             * @param node The node whose left children are to be pushed.
             */
            private void pushLeftChildren(Node<KeyListInterface<T>> node) {
                while (node != null) {
                    nodeStack.push(node);
                    node = node.down[0];
                }
            }
        };
    }
}
