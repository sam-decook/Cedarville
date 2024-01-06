package com.mycompany.project4;

/**
 * This class implements the Queue ADT, providing an array-based queue object
 * 
 * @author samdecook
 * @version 1.0
 *          Created October 2021
 * 
 * @param <E>
 *            Description: This class provides a queue ADT, allowing users to
 *            add or remove
 *            objects, see what the front object is, and check the size/if it is
 *            empty.
 *            It does not inherit from any class nor is expected to be inherited
 *            from.
 */
public class ArrayQueue<E> implements Queue<E> {
    private static final int DEFAULT_CAPACITY = 100;

    private int size; // The amount of objects in the queue
    private int front; // The index of the start of the queue
    private E[] queue; // The array which implements the queue

    /**
     * Constructs an instance of ArrayQueue with default space
     */
    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an instance of ArrayQueue with user defined space
     * 
     * @param size, the size of the underlying array
     */
    public ArrayQueue(int size) {
        this.size = 0;
        front = 0;
        queue = (E[]) new Object[size];
    }

    /**
     * Puts <code>element</code> at the back of the queue
     *
     * @param element
     * @throws InvalidDataException if element is null
     */
    @Override
    public void enqueue(E element) throws InvalidDataException {
        if (element != null) {
            if (size == queue.length) { // The array is full
                expandArray();
            }

            // Mod provides wrapping functionality
            queue[(front + size++) % queue.length] = element;

        } else {
            throw new InvalidDataException("Input cannot be null");
        }
    }

    /**
     * Returns and removes object from the front of the queue
     * 
     * @return E
     * @throws QueueEmptyException if queue is empty
     */
    @Override
    public E dequeue() throws QueueEmptyException {
        if (size != 0) {
            E output = queue[front];
            queue[front++ % queue.length] = null;
            size--;
            return output;

        } else {
            throw new QueueEmptyException("Queue is empty");
        }
    }

    /**
     * Returns object from the front of the array
     * 
     * @return E
     * @throws QueueEmptyException if queue is empty
     */
    @Override
    public E front() throws QueueEmptyException {
        if (size != 0) {
            return queue[front];

        } else {
            throw new QueueEmptyException("Queue is empty");
        }
    }

    /**
     * Returns the amount of objects in the queue
     * 
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns whether the queue is empty
     * 
     * @return true if the queue is empty (has size 0)
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void expandArray() {
        // New queue of double the size
        E[] queueN = (E[]) new Object[size * 2];

        // Copy into new queue
        for (int i = 0; i < size; i++) {
            // Mod operation wraps the front "pointer" to the front
            queueN[i] = queue[front++ % size];
        }

        // Overwrite old queue and reset front
        queue = queueN;
        front = 0;
    }
}
