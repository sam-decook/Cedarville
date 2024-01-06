package com.mycompany.project4;

/**
 * This class describes the Queue ADT interface
 * 
 * @author samdecook
 * @version 1.0
 * @param <E>
 */
public interface Queue<E> {
    public void enqueue(E element) throws InvalidDataException;

    public E dequeue() throws QueueEmptyException;

    public E front() throws QueueEmptyException;

    public int size();

    public boolean isEmpty();
}
