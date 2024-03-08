// --== CS400 Fall 2023 File Header Information ==--
// Name: <Zihan Xu>
// Email: <zxu536@wisc.edu>
// Group: <B03>
// TA: <Zheyang Xiong>
// Lecturer: <LEC 002>
// Notes to Grader: <I cite the insert method from website. The website page I have post in the method comment of that method>

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    protected static class RBTNode<T> extends Node<T> {
        public int blackHeight = 0; // 1: black, 0: red

        public RBTNode(T data) {
            super(data);
        }

        public RBTNode<T> getUp() {
            return (RBTNode<T>) this.up;
        }

        public RBTNode<T> getDownLeft() {
            return (RBTNode<T>) this.down[0];
        }

        public RBTNode<T> getDownRight() {
            return (RBTNode<T>) this.down[1];
        }
    }

    @Override
    public boolean insert(T data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("Cannot insert null data into the tree.");
        }

        RBTNode<T> newNode = new RBTNode<>(data);
        boolean inserted = super.insertHelper(newNode);

        if (inserted) {
            enforceRBTreePropertiesAfterInsert(newNode);
            ((RBTNode<T>)root).blackHeight = 1;
            }
        return inserted;
    }




    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> node) {
        if (node == null || node.getUp() == null || node.getUp().getUp() == null) {
            return;
        }

        RBTNode<T> parent = node.getUp();
        RBTNode<T> grandparent = parent.getUp();
        RBTNode<T> uncle = (parent.isRightChild()) ? grandparent.getDownLeft() : grandparent.getDownRight();

        int uncleColor = (uncle == null) ? 1 : uncle.blackHeight;

        if (parent.blackHeight == 0 && node.blackHeight == 0) {
            if (uncleColor == 1) {
                if ((node.isRightChild() && parent.isRightChild()) || (!node.isRightChild() && !parent.isRightChild())) {
                    int tempColor = parent.blackHeight;
                    parent.blackHeight = grandparent.blackHeight;
                    grandparent.blackHeight = tempColor;
                    rotate(parent, grandparent);
                } else {
                    int tempColor = node.blackHeight;
                    node.blackHeight = grandparent.blackHeight;
                    grandparent.blackHeight = tempColor;
                    rotate(node, parent);
                    rotate(node, grandparent);
                }
            } else {
                grandparent.blackHeight = (grandparent.getUp() == null) ? 1 : 0;
                parent.blackHeight = 1;
                if (uncle != null) uncle.blackHeight = 1;
                if (grandparent.getUp() != null && grandparent.blackHeight == 0 && grandparent.getUp().blackHeight == 0) {
                    enforceRBTreePropertiesAfterInsert(grandparent);
                }
            }
        }
    }







    @Test
    public void testSingleInsert() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        assertTrue(tree.insert(114514), "Insert should succeed");
        assertNotNull(tree.root, "Root should not be null");
        assertEquals(114514, tree.root.data, "Root data should be 114514");
        assertEquals(1, ((RBTNode<T>)tree.root).blackHeight, "Root should be black");
    }

    @Test
    public void testMultipleInserts() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        assertTrue(tree.insert(100), "Insert 100 should succeed");
        assertTrue(tree.insert(50), "Insert 50 should succeed");
        assertTrue(tree.insert(150), "Insert 150 should succeed");

        // Validate tree structure and properties
        assertNotNull(tree.root, "Root should not be null");
        assertEquals(100, tree.root.data, "Root data should be 100");
        assertEquals(1, ((RBTNode<T>)tree.root).blackHeight, "Root should be black");

        RBTNode<Integer> leftChild = ((RBTNode<Integer>)tree.root).getDownLeft();
        assertNotNull(leftChild, "Left child should not be null");
        assertEquals(50, leftChild.data, "Left child data should be 50");
        assertEquals(0, ((RBTNode<T>)tree.root).getDownLeft().blackHeight, "Left child should be red");

        RBTNode<Integer> rightChild = ((RBTNode<Integer>)tree.root).getDownRight();
        assertNotNull(rightChild, "Right child should not be null");
        assertEquals(150, rightChild.data, "Right child data should be 150");
        assertEquals(0, ((RBTNode<T>)tree.root).getDownRight().blackHeight, "Right child should be red");
    }

    @Test
    public void testComplexInsertsAndRecoloring() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        // Insert elements
        assertTrue(tree.insert(100), "Insert 100 should succeed");
        assertTrue(tree.insert(50), "Insert 50 should succeed");
        assertTrue(tree.insert(150), "Insert 150 should succeed");
        assertTrue(tree.insert(25), "Insert 25 should succeed");
        assertTrue(tree.insert(75), "Insert 75 should succeed");
        assertTrue(tree.insert(125), "Insert 125 should succeed");
        assertTrue(tree.insert(175), "Insert 175 should succeed");

        // Validate root
        assertNotNull(tree.root, "Root should not be null");
        assertEquals(100, tree.root.data, "Root data should be 100");
        assertEquals(1, ((RBTNode<T>)tree.root).blackHeight, "Root should be black");

        // Insert 65 and validate
        assertTrue(tree.insert(65), "Insert 65 should succeed");

        // Note: The variable 'insertedNode' is not defined in the provided code.
        // If you have a way to retrieve the recently inserted node, use that method.
        // For the sake of this example, I'll retrieve it using a method like `findNode`.
        RBTNode<Integer> insertedNode = (RBTNode<Integer>) tree.findNode(65); // Assuming findNode returns the node with the provided data
        assertNotNull(insertedNode, "Inserted node (65) should not be null");
        assertEquals(0, insertedNode.blackHeight, "Inserted node (65) should be red");

        // Validate tree properties
        assertEquals(1, ((RBTNode<T>)tree.root).blackHeight, "Root should be black after insert 65");
    }


}

