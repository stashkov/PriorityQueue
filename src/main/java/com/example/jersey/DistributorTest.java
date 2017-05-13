package com.example.jersey;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class DistributorTest {
    private static Distributor d;
    private final int MINUTES = Distributor.MINUTES;

    @BeforeMethod
    public void setUp() {
        d = new Distributor();
    }

    @AfterMethod
    public void tearDown() {
        d = null;
    }

    @Test
    public void testDistributorConstructor() {
        try {
            new Distributor();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testWaitTimeVIPOnlyOneIteration() {
        d.placeOrder(1, 10);
        Order o = DuckQueue.getOrderByCustomerID(1);
        assertEquals(d.getApproximateWaitTime(o) , 0);
    }

    @Test
    public void testWaitTimeVIPOnlyOneIterationsTwoOrders() {
        d.placeOrder(1, 10);
        d.placeOrder(2, 10);
        Order o = DuckQueue.getOrderByCustomerID(2);
        int minutes = d.getApproximateWaitTime(o);
        assertEquals(minutes , 0);
    }

    @Test
    public void testWaitTimeVIPOnlyTwoIterationsTwoOrders() {
        int customerID = 2;
        d.placeOrder(1, 20);
        d.placeOrder(customerID, 20);
        Order o = DuckQueue.getOrderByCustomerID(customerID);
        assertEquals(d.getApproximateWaitTime(o) * MINUTES , 5);
    }

    @Test
    public void testWaitTimeRegularOnlyOneIteration() {
        int customerID = 10000;
        d.placeOrder(customerID, 10);
        Order o = DuckQueue.getOrderByCustomerID(customerID);
        assertEquals(d.getApproximateWaitTime(o) , 0);
    }

    @Test
    public void testWaitTimeRegularOnlyOneIterationsTwoOrders() {
        int customerID = 20000;
        d.placeOrder(10000, 10);
        d.placeOrder(customerID, 10);
        Order o = DuckQueue.getOrderByCustomerID(customerID);
        assertEquals(d.getApproximateWaitTime(o) , 0);
    }

    @Test
    public void testWaitTimeRegularOnlyTwoIterationsTwoOrders() {
        int customerID = 20000;
        d.placeOrder(10000, 20);
        d.placeOrder(customerID, 20);
        Order o = DuckQueue.getOrderByCustomerID(customerID);
        assertEquals(d.getApproximateWaitTime(o) * MINUTES , 5);
    }

    @Test


}



