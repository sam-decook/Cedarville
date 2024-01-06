package com.mycompany.project6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the ListBinaryTree class and its functionality
 *
 * @author samdecook
 * @version 1.0
 *          Created November 2021
 * 
 *          Testing is done in one function so it can be compared to the objects
 *          used
 *          to make the tree, ensuring the functions worked as intended
 */
public class ListBinaryTreeTest {
    /**
     * Test of methods of class ListBinaryTree.
     */
    @Test
    public void testListBT() {
        // I will test the methods as the tree is built
        ListBinaryTree myTree = new ListBinaryTree();

        assertEquals(0, myTree.size());
        assertTrue(myTree.isEmpty());

        STNode root = new STNode(0, null, null, null);
        myTree = new ListBinaryTree(root);

        assertTrue(myTree.isRoot(root));
        assertFalse(myTree.isInternal(root));
        assertTrue(myTree.isExternal(root));
        assertEquals(1, myTree.size());
        assertFalse(myTree.isEmpty());

        STNode node1 = new STNode(1, root, null, null);
        STNode node2 = new STNode(2, root, null, null);
        root.setLeftChild(node1);
        node1.setSibling(node2);

        STNode node3 = new STNode(3, node1, null, null);
        STNode node4 = new STNode(4, node1, null, null);
        node1.setLeftChild(node3);

        // Test while there is a left child but not a right
        assertNull(myTree.sibling(node3));

        node3.setSibling(node4);

        STNode node5 = new STNode(5, node2, null, null);
        STNode node6 = new STNode(6, node2, null, null);
        node2.setLeftChild(node5);
        node5.setSibling(node6);

        STNode node7 = new STNode(7, node3, null, null);
        STNode node8 = new STNode(8, node3, null, null);
        node3.setLeftChild(node7);
        node7.setSibling(node8);

        STNode node9 = new STNode(9, node4, null, null);
        STNode node10 = new STNode(10, node4, null, null);
        node4.setLeftChild(node9);
        node9.setSibling(node10);

        myTree.setSize(11);

        // Main test set
        assertEquals(node1, myTree.leftChild(root));
        assertEquals(node2, myTree.rightChild(root));
        assertEquals(node2, myTree.sibling(node1));
        assertEquals(node1, myTree.sibling(node2));
        assertEquals(root, myTree.parent(node1));
        assertEquals(root, myTree.parent(node2));
        assertFalse(myTree.isRoot(node1));
        assertTrue(myTree.isExternal(node5));
        assertTrue(myTree.isExternal(node6));
        assertTrue(myTree.isInternal(node2));

        // Testing the exceptions
        ListBinaryTree emptyTree = new ListBinaryTree();

        Exception e1 = assertThrows(EmptyTreeException.class, () -> {
            emptyTree.root();
        });
        String msg1 = e1.getMessage();
        assertTrue(msg1.equals("Empty Tree"));

        STNode pos = null;
        Exception e2 = assertThrows(InvalidPositionException.class, () -> {
            emptyTree.sibling(pos);
        });
        String msg2 = e2.getMessage();
        assertTrue(msg2.equals("Invalid Position"));
    }
}
