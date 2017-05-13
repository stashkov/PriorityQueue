package com.example.jersey;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

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

//    @Test
//    public void testWaitTimeVIPOnlyTwoIterationsTwoOrders() {
//        int customerID = 2;
//        d.placeOrder(1, 20);
//        d.placeOrder(customerID, 20);
//        Order o = DuckQueue.getOrderByCustomerID(customerID);
//        assertEquals(d.getApproximateWaitTime(o) * MINUTES , 5);
//    }

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

//    @Test
//    public void testWaitTimeRegularOnlyTwoIterationsTwoOrders() {
//        int customerID = 20000;
//        d.placeOrder(10000, 20);
//        d.placeOrder(customerID, 20);
//        Order o = DuckQueue.getOrderByCustomerID(customerID);
//        assertEquals(d.getApproximateWaitTime(o) * MINUTES , 5);
//    }

    @Test
    public void testCancelOrderByCustomerID(){
        int customerID = 1;
        d.placeOrder(customerID, 20);
        d.cancelOrderByCustomerID(customerID);
        String emptyJSON = "{}";
        assertEquals(d.managerView(), emptyJSON);
    }

    @Test
    public void testGetNextORder(){
        d.placeOrder(1, 20);
        d.getNextOrder();
        String emptyJSON = "{}";
        assertEquals(d.managerView(), emptyJSON);
    }

    @Test
    public void testPlaceOrderOnline(){
        d.placeOrderOnline(1, 20);
        d.getNextOrder();
        String emptyJSON = "{}";
        assertEquals(d.managerView(), emptyJSON);
    }

    @Test
    public void testGetUser(){
        d.placeOrderOnline(1, 20);
        String j = "{\"Links\":" +
                "[{\"href\":\"/orders\"," +
                "\"rel\":\"list\"," +
                "\"method\":\"GET\"}," +
                "{\"href\":\"/orders\"," +
                "\"rel\":\"delete\"," +
                "\"method\":\"DELETE\"}]}";
        assertEquals(d.getUser(), j);
    }

//    @Test
//    public void testManagerView(){
//        d.placeOrderOnline(1, 20);
//        // this is horrible
//        String j = "{\"orders\":" +
//                "[{\"customer_id\":1" +
//                ",\"amount\":20,\"info\"" +
//                ":[{\"vip\":true,\"positio" +
//                "n_in_queue\":1,\"approx_wait" +
//                "_time_minutes\":0.0,\"order_id\":" +
//                "0}],\"Links\":[{\"href\":\"/orde" +
//                "rs/1\",\"rel\":\"self\",\"method\"" +
//                ":\"GET\"},{\"href\":\"/orders/1\"," +
//                "\"rel\":\"delete\",\"method\":\"DEL" +
//                "ETE\"},{\"href\":\"/orders/1/amoun" +
//                "t/20\",\"rel\":\"create\",\"method\":\"GET\"}]}]}";
//        assertEquals(d.managerView(), j);
//    }
}
