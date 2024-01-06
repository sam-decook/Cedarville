package com.mycompany.project6;

/**
 * The ListBinaryTree class implements BinaryTree using ST (Sibling tree) nodes
 * 
 * @author samdecook
 * @version 1.0
 *          Created November 2021
 * 
 *          Provides methods to work with a BinaryTree. However, it lacks
 *          methods
 *          to change and modify the tree.
 */
public class ListBinaryTree implements BinaryTree {
    private STNode root = null;
    private int size;

    /**
     * Constructor, makes a tree with specified root node
     * 
     * @param r root node of tree
     */
    public ListBinaryTree(STNode r) {
        root = r;
        size = 1;
    }

    /**
     * Constructor, makes an empty tree
     */
    public ListBinaryTree() {
        root = null;
        size = 0;
    }

    /**
     * @return a reference to the root node
     * @throws EmptyTreeException if the tree is empty
     */
    public Position root() throws EmptyTreeException {
        if (root == null) {
            throw new EmptyTreeException();
        }

        return (Position) root;
    }

    /**
     * @param pos is the node whose left child will be returned
     * @return a reference to left child of pos
     */
    public Position leftChild(Position pos) throws InvalidPositionException {
        if (!(pos instanceof STNode)) {
            throw new InvalidPositionException();
        }

        STNode n = (STNode) pos;

        if (n.getLeftChild() == null) {
            return null;
        } else {
            return (Position) n.getLeftChild();
        }
    }

    /**
     * @param pos is the node whose right child will be returned
     * @return a reference to right child of pos
     */
    public Position rightChild(Position pos) throws InvalidPositionException {
        if (!(pos instanceof STNode)) {
            throw new InvalidPositionException();
        }

        STNode n = (STNode) pos;

        if (n.getLeftChild() == null) {
            return null;
        } else {
            if (n.getLeftChild().getSibling() == null) {
                return null;
            } else {
                return (Position) n.getLeftChild().getSibling();
            }
        }
    }

    /**
     * @param pos is the node whose sibling will be returned
     * @return a reference to the sibling of pos
     * @throws InvalidPositionException if the position is null or wrong
     */
    public Position sibling(Position pos) throws InvalidPositionException {
        if (!(pos instanceof STNode)) {
            throw new InvalidPositionException();
        }

        STNode n = (STNode) pos;

        if (n.getSibling() == null) { // n could be left or right child
            if (n.getParent().getLeftChild() == n) { // n is the left child
                return null; // and has no sibling
            } else {
                return n.getParent().getLeftChild();// Since n was right child
            }
        } else {
            return n.getSibling();
        }
    }

    /**
     * @param pos is the node whose parent will be returned
     * @return a reference to the parent of pos
     * @throws InvalidPositionException if the position is null or wrong
     */
    public Position parent(Position pos) throws InvalidPositionException {
        if (!(pos instanceof STNode)) {
            throw new InvalidPositionException();
        }

        STNode n = (STNode) pos;

        if (n.getParent() == null) {
            return null;
        } else {
            return (Position) n.getParent();
        }
    }

    /**
     * @param pos is the node which will be examined
     * @return true if node has 1 or 2 childen
     * @throws InvalidPositionException if the position is null or wrong
     */
    public boolean isInternal(Position pos) throws InvalidPositionException {
        if (!(pos instanceof STNode)) {
            throw new InvalidPositionException();
        }

        // It has 1 or 2 children if there is a left child
        return ((STNode) pos).getLeftChild() != null;
    }

    /**
     * @param pos is the node which will be examined
     * @return true if node has no children
     * @throws InvalidPositionException if the position is null or wrong
     */
    public boolean isExternal(Position pos) throws InvalidPositionException {
        return !(isInternal(pos));
    }

    /**
     * @param pos is the node which will be examined
     * @return true if the node is the root node
     * @throws InvalidPositionException if the position is null or wrong
     */
    public boolean isRoot(Position pos) throws InvalidPositionException {
        if (!(pos instanceof STNode)) {
            throw new InvalidPositionException();
        }

        return ((STNode) pos) == root;
    }

    /**
     * @return the number of Positions (nodes) in the tree
     */
    public int size() {
        return size;
    }

    /**
     * 
     * @param s the size of the tree
     */
    public void setSize(int s) {
        size = s;
    }

    /**
     * @return true if the tree currently contains no Positions
     */
    public boolean isEmpty() {
        return root == null;
    }

    public static void main(String[] args) {
        ListBinaryTree myTree = new ListBinaryTree();
        myTree.fillTree();

        Position root = myTree.root;

        System.out.println("Pre-order walk");
        PreOrderWalk pre = new PreOrderWalk(myTree);
        pre.execute(root);

        System.out.println("\nIn-order walk");
        InOrderWalk in = new InOrderWalk(myTree);
        in.execute(root);

        System.out.println("\nPost-order walk");
        PostOrderWalk post = new PostOrderWalk(myTree);
        post.execute(root);
    }

    public void fillTree() {
        root = new STNode(0, null, null, null);
        STNode node1 = new STNode(1, root, null, null);
        STNode node2 = new STNode(2, root, null, null);
        root.setLeftChild(node1);
        node1.setSibling(node2);

        STNode node3 = new STNode(3, node1, null, null);
        STNode node4 = new STNode(4, node1, null, null);
        node1.setLeftChild(node3);
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

        size = 11;
    }
}
