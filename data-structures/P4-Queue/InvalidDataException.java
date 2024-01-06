package com.mycompany.project4;

/**
 * The exception class for invalid data inputs
 * 
 * @author samdecook
 * @version 1.0
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Constructs an instance of <code>InvalidDataException</code> with the
     * specified error message
     *
     * @param msg the error message
     */
    public InvalidDataException(String msg) {
        super(msg);
    }
}
