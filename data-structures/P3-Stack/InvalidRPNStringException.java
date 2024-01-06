package com.mycompany.project3;

/**
 * The exception class used for RPNCalculator
 * 
 * @author samdecook
 * @version 1.0
 *          File: InvalidRPNStringException.java
 *          Created September 2021
 * 
 *          Custom exception for RPNCalculator. Error message specifies error
 */
public class InvalidRPNStringException extends RuntimeException {
    public InvalidRPNStringException(String err) {
        super(err);
    }
}
