package com.mycompany.project6;

/**
 * The InOrderWalk class extends EulerTour and provides an in-order walk of a
 * binary tree
 * 
 * @author samdecook
 * @version 1.0
 *          Created November 2021
 */
public class InOrderWalk extends EulerTour {
    /**
     * Constructor to make a InOrderWalk
     * 
     * @param newTree tree to be walked
     */
    public InOrderWalk(BinaryTree newTree) {
        super(newTree);
    }

    /**
     * Performs the in-order walk
     * 
     * @param pos the position where the walk starts
     */
    public void execute(Position pos) {
        performTour(pos);
    }

    /**
     * Prints out the element
     * 
     * @param pos position to be printed out
     */
    @Override
    protected void visitExternal(Position pos) {
        System.out.print(pos.element() + " ");
    }

    /**
     * In-order visit operation - print out element
     * 
     * @param pos position to be printed
     */
    @Override
    protected void visitInorder(Position pos) {
        System.out.print(pos.element() + " ");
    }
}
