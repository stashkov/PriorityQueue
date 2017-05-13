package com.example.jersey;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.testng.Assert.*;

public class DuckQueueTest {
    private DuckQueue q;

    @BeforeMethod
    public void setUp() {
        q = new DuckQueue();
    }

    @AfterMethod
    public void tearDown() {
        q = null;
    }


    @Test
    public void testEmptyQueue() {
        assertEquals(DuckQueue.itemsInQueue, 0);
    }

    @Test
    public void testOneElement() {
        Order o = new Order(1, 6);
        q.add(o);
        assertEquals(DuckQueue.itemsInQueue, 6);
    }

    @Test
    public void testTwoElements() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(2, 5);
        q.add(o1);
        q.add(o2);
        assertEquals(DuckQueue.itemsInQueue, 10);
    }

    @Test
    public void testSameCustomerID() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(1, 6);
        q.add(o1);
        q.add(o2);
        assertEquals(DuckQueue.itemsInQueue, 5);
    }

    @Test
    public void testUniqueCustomers() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(1, 6);
        q.add(o1);
        q.add(o2);
        assertEquals(DuckQueue.map.keySet(), new HashSet<>(Arrays.asList(1)));
    }

    @Test
    public void testAddAndRemoveOrderFromQueue() {
        Order o1 = new Order(1, 5);
        q.add(o1);
        q.remove();
        assertEquals(DuckQueue.itemsInQueue, 0);
    }

    @Test
    public void testAddAndRemoveOrderFromQueue2Orders() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(2, 6);
        q.add(o1);
        q.add(o2);
        q.remove(o1);
        assertEquals(DuckQueue.itemsInQueue, 6);
    }

    @Test
    public void testUniqueCustomersRemoveFromQueue() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(2, 6);
        q.add(o1);
        q.add(o2);
        q.remove(o1);
        assertEquals(DuckQueue.map.keySet(), new HashSet<>(Arrays.asList(2)));
    }



    @Test
    public void testTwoElementsInRegularQueue() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(10000, 10);
        q.add(o1);
        q.add(o2);
        assertEquals(DuckQueue.itemsInQueue, 15);
    }

    @Test
    public void testAddAndRemoveOrderFromQueueRegularQueue() {
        Order o1 = new Order(10000, 5);
        q.add(o1);
        q.remove(o1);
        assertEquals(DuckQueue.itemsInQueue, 0);
    }

    @Test
    public void testAddAndRemoveOrderFromQueue2OrdersRegularQueue() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(10000, 6);
        q.add(o1);
        q.add(o2);
        q.remove(o1);
        assertEquals(DuckQueue.itemsInQueue, 6);
    }

    @Test
    public void testItemsInVIPQueue() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(10000, 6);
        q.add(o1);
        q.add(o2);
        assertEquals(DuckQueue.itemsInVIPQueue, 5);
    }
}



