package com.mycompany.project4;

/**
 * The exception class for calls on an empty queue
 *
 * @author samdecook
 * @version 1.0
 */
public class QueueEmptyException extends RuntimeException {

    /**
     * Constructs an instance of <code>QueueEmptyException</code> with the
     * specified error message
     *
     * @param msg the error message
     */
    public QueueEmptyException(String msg) {
        super(msg);
    }
}
