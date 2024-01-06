package com.mycompany.project6;

/**
 * The PreOrderWalk class extends EulerTour and provides a pre-order walk of a
 * binary tree
 *
 * @author samdecook
 * @version 1.0
 *          Created November 2021
 */
public class PreOrderWalk extends EulerTour {
    /**
     * Constructor to make a PreOrderWalk
     * 
     * @param newTree tree to be walked
     */
    public PreOrderWalk(BinaryTree newTree) {
        super(newTree);
    }

    /*
     * In EulerTour, I removed everything related to TraversalResults,
     * and made the functions return void (Guidance point j)
     */

    /**
     * Performs the pre-order walk
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
     * Pre-order visit operation - print out element
     * 
     * @param pos position to be printed
     */
    @Override
    protected void visitPreorder(Position pos) {
        System.out.print(pos.element() + " ");
    }
}
