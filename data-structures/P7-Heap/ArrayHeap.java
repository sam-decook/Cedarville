package com.mycompany.project7;

/**
 * Title: Project #7
 * Description: Implements a heap using an array, provides an add() and
 * removeRoot() method
 * Copyright: Copyright (c) 2021
 * 
 * @author Sam DeCook
 * @version 1.0
 */
public class ArrayHeap extends ArrayBinaryTree implements Heap {
    Comparator heapComp;

    /**
     * Makes a new instance of ArrayHeap with default size
     * 
     * @param newComp the object used to compare the elements
     */
    public ArrayHeap(Comparator newComp) {
        this(newComp, DEFAULT_SIZE);
    }

    /**
     * Makes a new instance of ArrayHeap
     * 
     * @param newComp the object used to compare the elements
     * @param newSize the starting size of the array
     */
    public ArrayHeap(Comparator newComp, int newSize) {
        super(newSize);
        heapComp = newComp;
    }

    /**
     * Makes a new item with the key and element and adds to the heap
     * 
     * @param newKey     the object used to determine the smallest element
     * @param newElement the element that makes up the heap
     * @throws InvalidObjectException if the key is not the same object that is
     *                                used by the specific heap
     */
    public void add(Object newKey, Object newElement)
            throws InvalidObjectException {
        if (!heapComp.isComparable(newKey)) {
            throw new InvalidObjectException("Key is not a comparable object");
        }

        // Check if array needs to be expanded
        if (btArray.length == size) { // Array is full, double capacity
            ArrayPosition[] newArray = new ArrayPosition[btArray.length * 2];

            // Copy elements
            for (int i = 0; i < btArray.length; i++) {
                newArray[i] = btArray[i];
                btArray[i] = null; // For garbage collection (right?)
            }

            btArray = newArray;
        }

        // Make new ArrayPosition with a new Item, append to end of the array
        Item newItem = new Item(newKey, newElement);
        ArrayPosition newPos = new ArrayPosition(size, newItem);
        btArray[size] = newPos;
        size++;

        // Restore heap-order property
        bubbleUp(newPos);
    }

    /**
     * Removes the root (the smallest) and fixes the heap
     * 
     * @return the element at the root
     * @throws EmptyHeapException if the heap is empty
     */
    public Object removeRoot() throws EmptyHeapException {
        if (size == 0) {
            throw new EmptyHeapException("Cannot remove from empty heap");
        }

        // Get first (root) and last into variables
        ArrayPosition root = btArray[0];
        ArrayPosition last = btArray[size - 1];

        // Remove last node
        btArray[size - 1] = null;
        size--;

        // Check if the heap only has one node (the root)
        if (root != last) { // The tree is not empty
            // Set root equal to last node
            btArray[0] = last;
            btArray[0].setIndex(0);

            // Fix the heap
            bubbleDown(btArray[0]);
        } // The tree is empty, and you just return the old root's element

        // Return root, the removed value
        Item x = (Item) root.element();
        return x.element();
    }

    private void bubbleUp(ArrayPosition pos) {
        if (parent(pos) != null) {
            ArrayPosition parent = (ArrayPosition) parent(pos);

            // Integers
            Object curr = ((Item) pos.element()).element();
            Object comp = ((Item) parent.element()).element();

            if (heapComp.isLessThan(curr, comp)) {
                // Swap if the nodes are out of order
                swap(pos, parent);

                bubbleUp(pos); // "pos" is now where "parent" was
            }
        }
    }

    private void bubbleDown(ArrayPosition position) {
        ArrayPosition lChild = null;
        ArrayPosition rChild = null;

        Integer pos = (Integer) ((Item) position.element()).element();
        Integer lc = null;
        Integer rc = null;

        // Check if there is left and or right children
        if (leftChild(position) != null) {
            // Get ArrayPosition and Integer
            lChild = (ArrayPosition) leftChild(position);
            lc = (Integer) ((Item) lChild.element()).element();
        }
        if (rightChild(position) != null) {
            rChild = (ArrayPosition) rightChild(position);
            rc = (Integer) ((Item) rChild.element()).element();
        }

        // Work through different scenarios
        if (rChild == null) {
            if (lChild != null) { // There is just one node
                if (heapComp.isGreaterThan(pos, lc)) {
                    swap(position, lChild);
                }
            } // No children nodes, bubbleDown is finished

        } else { // Both nodes are full
            if (heapComp.isGreaterThan(pos, lc) ||
                    heapComp.isGreaterThan(pos, rc)) { // If greater, swap

                if (heapComp.isLessThan(lc, rc)) { // Swap with smaller
                    swap(position, lChild);
                } else {
                    swap(position, rChild);
                }

                bubbleDown(position); // Continue to bubble down
            } // bubbleDown is finished
        }
    }

    private void swap(ArrayPosition pos1, ArrayPosition pos2) {
        // Swap pos1 with pos2 and swap indices
        int index1 = pos1.getIndex();
        int index2 = pos2.getIndex();
        btArray[index1] = pos2;
        btArray[index2] = pos1;
        pos1.setIndex(index2);
        pos2.setIndex(index1);
    }

    public static void main(String[] args) {
        Comparator myComp = new IntegerComparator();
        Heap myHeap = new ArrayHeap(myComp, 8);

        int x;
        for (int i = 0; i < 10000; i++) {
            x = (int) (Math.random() * 10000) + 1;
            myHeap.add(x, x);
        }

        System.out.println(myHeap.size());

        while (!myHeap.isEmpty()) {
            Integer removedItem = (Integer) myHeap.removeRoot();
            System.out.println("Removed " + removedItem);
        }

        System.out.println("All nodes removed");
    }
}
