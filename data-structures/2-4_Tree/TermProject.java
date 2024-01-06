package mycompany.termproject;

/**
 * Title: Term Project 2-4 Trees
 * Description: Insert random numbers into a 2,4 tree
 * then remove them from the inserting order of the numbers
 * 
 * @author Sam DeCook and Brooke Ackley
 * @version 1.0
 */
public class TwoFourTree implements Dictionary {

    private Comparator treeComp;
    private int size = 0;
    private TFNode treeRoot = null;

    public TwoFourTree(Comparator comp) {
        treeComp = comp;
    }

    private TFNode root() {
        return treeRoot;
    }

    private void setRoot(TFNode root) {
        treeRoot = root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    // Searches tree for node containing the item with a specific key
    // Returns the node containing the item matching the key;
    // if not found, returns the last node it searched (a leaf)
    private TFNode search(Object key) {
        TFNode node = treeRoot;
        int num;

        while (true) { // seach through tree
            num = FFGTE(node, key);

            if (num < node.getNumItems() &&
                    treeComp.isEqual(node.getItem(num).key(), key)) {
                // Check it won't go out of bounds
                return node; // if it matches the key
            } else if (node.getChild(num) == null) {
                // Search arrived at a leaf and didn't find anything
                // Returns the current node, this is used for insertElement
                return node;
            } else {
                // Not found in the node, continue down the correct child
                node = node.getChild(num);
            }
        }
    }

    /**
     * Searches dictionary to determine if key is present
     * 
     * @param key to be searched for
     * @return object corresponding to key; null if not found
     */
    @Override
    public Object findElement(Object key) {
        TFNode node = search(key); // find the node the element is in

        // Find the element in the node
        for (int i = 0; i < node.getNumItems(); i++) {
            if (node.getItem(i).key() == key) {
                return node.getItem(i).element();
            }
        }

        return null; // fell through loop, did not find element
    }

    // Find the first key in the node that is greater than or equal to the key
    private int FFGTE(TFNode node, Object key) {
        int i;
        for (i = 0; i < node.getNumItems(); i++) {
            if (treeComp.isGreaterThanOrEqualTo(node.getItem(i).key(), key)) {
                break;
            }
        }
        return i;
    }

    /**
     * Inserts provided element into the Dictionary
     * 
     * @param key     of object to be inserted
     * @param element to be inserted
     */
    @Override
    public void insertElement(Object key, Object element) {
        Item item = new Item(key, element);

        if (treeRoot == null) { // make a new root and insert the item
            treeRoot = new TFNode();
            treeRoot.addItem(0, item);

        } else {
            TFNode node = search(key);
            int ind = FFGTE(node, key);

            if (node.getChild(0) != null) { // node is internal, found duplicate
                // Find in-order successor
                node = node.getChild(ind + 1); // Go down right child...

                while (node.getChild(0) != null) { // ...then go left
                    node = node.getChild(0);
                }

                // Insert at the left-most position, the in-order successor
                node.insertItem(0, item);

            } else { // node is a leaf, insert normally
                node.insertItem(ind, item);
            }

            fixOverflow(node);
        }
        size++;
    }

    private void fixOverflow(TFNode node) {
        // have overflow
        if (node.getNumItems() > node.getMaxItems()) {
            Item item4 = node.deleteItem(3); // delete doesn't remove children
            Item item3 = node.deleteItem(2);

            TFNode parent;
            TFNode newNode = new TFNode();

            if (treeComp.isEqual(node.getItem(1).key(), item3.key())) {
                // The second and third item in the overflowed node are the same
                // Instead of moving the third item up, we need to move the
                // second so that the duplicate goes to the right
                Item item2 = node.deleteItem(1);

                if (node.getParent() == null) {
                    parent = new TFNode(); // make new parent (root)
                    parent.addItem(0, item2);
                    setRoot(parent);

                    newNode.insertItem(0, item3);
                    newNode.insertItem(1, item4);

                    // Set parent child relationships
                    node.setParent(parent);
                    parent.setChild(0, node);
                    newNode.setParent(parent);
                    parent.setChild(1, newNode);
                } else { // already has a parent
                    parent = node.getParent();
                    parent.insertItem(WCIT(node), item2);

                    newNode.insertItem(0, item3);
                    newNode.insertItem(1, item4);

                    // Set parent child relationships
                    newNode.setParent(parent);
                    parent.setChild(WCIT(node) + 1, newNode);
                }

                // If there are children, fix them
                if (node.getChild(0) != null) {
                    // Move children over
                    newNode.setChild(0, node.getChild(2));
                    newNode.setChild(1, node.getChild(3));
                    newNode.setChild(2, node.getChild(4));

                    // Remove old children
                    node.setChild(2, null);
                    node.setChild(3, null);
                    node.setChild(4, null);

                    // Fix their parents
                    newNode.getChild(0).setParent(newNode);
                    newNode.getChild(1).setParent(newNode);
                    newNode.getChild(2).setParent(newNode);
                }
            } else { // No duplicate, rearrange normally
                if (node.getParent() == null) {
                    parent = new TFNode(); // make new parent (root)
                    parent.addItem(0, item3);
                    setRoot(parent);

                    newNode.addItem(0, item4);

                    // Set parent child relationships
                    node.setParent(parent);
                    newNode.setParent(parent);
                    parent.setChild(0, node);
                    parent.setChild(1, newNode);
                } else { // already has a parent
                    parent = node.getParent();
                    parent.insertItem(WCIT(node), item3);

                    newNode.addItem(0, item4);

                    // Set parent child relationships
                    newNode.setParent(parent);
                    parent.setChild(WCIT(node) + 1, newNode);
                }

                // If there are children, fix them
                if (node.getChild(0) != null) {
                    // Move children over
                    newNode.setChild(0, node.getChild(3));
                    newNode.setChild(1, node.getChild(4));

                    // Remove old children
                    node.setChild(3, null);
                    node.setChild(4, null);

                    // Fix their parents
                    newNode.getChild(0).setParent(newNode);
                    newNode.getChild(1).setParent(newNode);
                }
            }

            fixOverflow(parent);
        }
    }

    // Returns the index of the node's location in the child array
    private int WCIT(TFNode node) {
        TFNode parent = node.getParent();

        for (int i = 0; i < parent.getNumItems() + 1; i++) {
            if (parent.getChild(i) == node) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Searches dictionary to determine if key is present, then
     * removes and returns corresponding object
     * 
     * @param key of data to be removed
     * @return object corresponding to key
     * @exception ElementNotFoundException if the key is not in dictionary
     */
    @Override
    public Object removeElement(Object key) throws ElementNotFoundException {
        TFNode node = search(key);
        int ind = FFGTE(node, key); // index where item to be removed is at
        Item item = node.getItem(ind);
        // keys not equal
        if (!treeComp.isEqual(item.key(), key)) {
            throw new ElementNotFoundException();
        }

        // Check if it is internal
        if (node.getChild(0) != null) { // it is, find in-order successor
            TFNode iosNode = node.getChild(ind + 1); // go down right child...

            while (iosNode.getChild(0) != null) { // ...then go left
                iosNode = iosNode.getChild(0);
            }

            // Delete ios to remove at the leaf, but save it
            Item ios = iosNode.removeItem(0);

            // Replace the item with the ios, removing the item, keeping the ios
            node.replaceItem(ind, ios);
            node = iosNode;

        } else { // it is a leaf, remove normally
            node.removeItem(ind);
        }

        fixUnderflow(node);

        size--;
        return item.element();
    }

    private void fixUnderflow(TFNode node) {
        if (node.getNumItems() == 0) { // underflow
            if (node == root()) { // removing item from root
                treeRoot = node.getChild(0);
                node.setParent(null);
                return;
            }
            TFNode parent = node.getParent();
            int child = WCIT(node);

            // First check if the l/r sibling exists
            // Has left if it isn't the 0th child
            // Has right if it less than the amount of items its parent has
            // Then check the amount of items, 2+ for transfer, 1 for fusion
            // Don't need to check num for fusion, will always have at least 1
            if (child != 0 && parent.getChild(child - 1).getNumItems() > 1) {
                // Left-transfer
                TFNode lChild = parent.getChild(child - 1);

                // Move parent item down into node
                int ind = child - 1; // index of parent item
                node.addItem(0, parent.getItem(ind));

                // Add last item of lChild (delete) to parent (replace)
                Item lastItem = lChild.deleteItem(lChild.getNumItems() - 1);
                parent.replaceItem(ind, lastItem);

                // Fix children
                if (lChild.getChild(0) != null) {
                    node.setChild(1, node.getChild(0)); // shift to make room
                    node.setChild(0, lChild.getChild(lChild.getNumItems() + 1));
                    node.getChild(0).setParent(node);
                    lChild.setChild(lChild.getNumItems() + 1, null);
                }

            } else if (child < parent.getNumItems() &&
                    parent.getChild(child + 1).getNumItems() > 1) {
                // Right-transfer
                TFNode rChild = parent.getChild(child + 1);

                // Move parent item down into node (child = index of parent item)
                node.addItem(0, parent.getItem(child));

                // Add first item of rChild (remove) to parent (replace)
                TFNode c = rChild.getChild(0); // get before it's deleted
                Item firstItem = rChild.removeItem(0);
                parent.replaceItem(child, firstItem);

                // Fix children
                if (node.getChild(0) != null) {
                    node.setChild(1, c);
                    node.getChild(1).setParent(node);
                }

            } else if (child != 0) {
                // Left-fusion
                TFNode temp = parent.getChild(child - 1);

                // Add item from parent to temp and remove item from parent
                temp.addItem(1, parent.removeItem(child - 1));

                // hook up temp to parent
                parent.setChild(child - 1, temp);

                // Set the chilren to the children of underflowed node
                if (node.getChild(0) != null) {
                    temp.setChild(2, node.getChild(0));
                    temp.getChild(2).setParent(temp);
                }

            } else {
                // Right-fusion
                TFNode temp = parent.getChild(child + 1);

                // Add in underflow node with Item from parent
                temp.insertItem(0, parent.removeItem(child));

                // Add Items from the closest right node
                temp.setChild(0, node.getChild(0));

                // node.addItem(1,temp.getItem(0));

                parent.setChild(child, temp);
                node.setParent(parent);

                // Do loop to add all children of right node to underflowed node
                if (temp.getChild(0) != null) {
                    temp.getChild(0).setParent(temp);

                }
            }
            if (node != root()) {
                fixUnderflow(parent);
            }

        }
    }

    public static void main(String[] args) {
        Comparator myComp = new IntegerComparator();
        TwoFourTree myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 10000;
        int[] test = new int[TEST_SIZE];

        System.out.println("Inserting");
        // Insert items into tree and array
        for (int i = 0; i < TEST_SIZE; i++) {
            test[i] = (int) (Math.random() * TEST_SIZE / 2 + 1);
            myTree.insertElement(test[i], test[i]);
            myTree.checkTree();
        }
        // print tree
        myTree.printAllElements();
        myTree.checkTree();

        System.out.println("Removing");
        // Removing items from tree using array
        for (int i = 0; i < TEST_SIZE; i++) {
            // print last 30 removes
            if (i > TEST_SIZE - 30) {
                System.out.println("Removing: " + test[i] + " (" + i + ")");
            }
            int out = (Integer) myTree.removeElement(test[i]);
            if (out != test[i]) {
                throw new TwoFourTreeException("main: wrong element removed");
            }
            if (i > TEST_SIZE - 30) {
                myTree.printAllElements();
            }
            myTree.checkTree();

        }
        System.out.println("done");
    }

    public void printAllElements() {
        int indent = 0;
        if (root() == null) {
            System.out.println("The tree is empty");
        } else {
            printTree(root(), indent);
        }
        System.out.println("\n");
    }

    public void printTree(TFNode start, int indent) {
        if (start == null) {
            return;
        }
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        printTFNode(start);
        indent += 4;
        int numChildren = start.getNumItems() + 1;
        for (int i = 0; i < numChildren; i++) {
            printTree(start.getChild(i), indent);
        }
    }

    public void printTFNode(TFNode node) {
        int numItems = node.getNumItems();
        for (int i = 0; i < numItems; i++) {
            System.out.print(((Item) node.getItem(i)).element() + " ");
        }
        System.out.println();
    }

    // checks if tree is properly hooked up, i.e., children point to parents
    public void checkTree() {
        checkTreeFromNode(treeRoot);
    }

    private void checkTreeFromNode(TFNode start) {
        if (start == null) {
            return;
        }

        if (start.getParent() != null) {
            TFNode parent = start.getParent();
            int childIndex = 0;
            for (childIndex = 0; childIndex <= parent.getNumItems(); childIndex++) {
                if (parent.getChild(childIndex) == start) {
                    break;
                }
            }
            // if child wasn't found, print problem
            if (childIndex > parent.getNumItems()) {
                System.out.println("Child to parent confusion");
                printTFNode(start);
            }
        }

        if (start.getChild(0) != null) {
            for (int childIndex = 0; childIndex <= start.getNumItems(); childIndex++) {
                if (start.getChild(childIndex) == null) {
                    System.out.println("Mixed null and non-null children");
                    printTFNode(start);
                } else {
                    if (start.getChild(childIndex).getParent() != start) {
                        System.out.println("Parent to child confusion");
                        printTFNode(start);
                    }
                    for (int i = childIndex - 1; i >= 0; i--) {
                        if (start.getChild(i) == start.getChild(childIndex)) {
                            System.out.println("Duplicate children of node");
                            printTFNode(start);
                        }
                    }
                }
            }
        }

        int numChildren = start.getNumItems() + 1;
        for (int childIndex = 0; childIndex < numChildren; childIndex++) {
            checkTreeFromNode(start.getChild(childIndex));
        }

    }
}
