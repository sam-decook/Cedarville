package com.mycompany.project6;

/**
 * The PostOrderWalk class extends EulerTour and provides a post-order walk
 * of
 * a binary tree
 *
 * @author samdecook
 * @version 1.0
 *          Created November 2021
 */
public class PostOrderWalk extends EulerTour {
    /**
     * Constructor to make a PostOrderWalk
     * 
     * @param newTree tree to be walked
     */
    public PostOrderWalk(BinaryTree newTree) {
        super(newTree);
    }

    /**
     * Performs the post-order walk
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
     * Post-order visit operation - print out element
     * 
     * @param pos position to be printed
     */
    @Override
    protected void visitPreorder(Position pos) {
        System.out.print(pos.element() + " ");
    }
}
