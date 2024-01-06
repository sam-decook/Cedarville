package com.mycompany.project4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the ArrayQueue class
 * 
 * @author samdecook
 * @version 1.0
 * 
 *          Description: The first test tests general functionality, the second
 *          one
 *          wrapping functionality when expanding the array the queue is built
 *          on, and
 *          the third that exceptions are thrown properly
 */
public class ArrayQueueTest {

    /**
     * Test of all methods of class ArrayQueue.
     */
    @Test
    public void testQueue() {
        System.out.println("Test queue");
        ArrayQueue queue = new ArrayQueue(); // Also tests default constructor

        assertTrue(queue.isEmpty());

        for (int i = 0; i < 10; i++) {
            Object element = i;
            queue.enqueue(element);
        }

        assertEquals(10, queue.size());
        assertEquals(0, queue.front());

        for (int i = 10; i < 20; i++) {
            Object element = i;
            queue.enqueue(element);
        }

        assertEquals(20, queue.size());
        assertEquals(0, queue.dequeue());

        for (int i = 1; i < 14; i++) {
            queue.dequeue();
        }

        assertEquals(14, queue.dequeue());
    }

    /**
     * Test of unwrapping functionality in expand method of class ArrayQueue
     */
    @Test
    public void testUnwrapping() {
        System.out.println("Test unwrapping");
        ArrayQueue queue = new ArrayQueue(10);

        for (int i = 0; i < 10; i++) {
            Object element = i;
            queue.enqueue(element);
        }

        for (int i = 0; i < 5; i++) {
            queue.dequeue();
        }

        // Should begin wrapping data, expand array and unwrap it
        for (int i = 10; i < 20; i++) {
            Object element = i;
            queue.enqueue(element);
        }

        for (int i = 0; i < 8; i++) {
            queue.dequeue();
        }

        assertEquals(13, queue.dequeue());
    }

    /**
     * Test of the exceptions of class ArrayQueue
     */
    @Test
    public void testExceptions() {
        System.out.println("Test exceptions");
        ArrayQueue queue = new ArrayQueue();
        Object element = null;

        // Attempt to enqueue a null element
        Exception e1 = assertThrows(InvalidDataException.class, () -> {
            queue.enqueue(element);
        });
        String msg1 = e1.getMessage();
        assertTrue(msg1.equals("Input cannot be null"));

        // Attempt to call front() on empty queue
        Exception e2 = assertThrows(QueueEmptyException.class, () -> {
            queue.front();
        });
        String msg2 = e2.getMessage();
        assertTrue(msg2.equals("Queue is empty"));

        // Attempt to call dequeue() on empty queue
        Exception e3 = assertThrows(QueueEmptyException.class, () -> {
            queue.dequeue();
        });
        String msg3 = e3.getMessage();
        assertTrue(msg3.equals("Queue is empty"));
    }
}
