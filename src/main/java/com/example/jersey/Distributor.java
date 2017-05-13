package com.example.jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

@Path("/")
public class Distributor {
    private static final int CAPACITY = 25;
    public static final int MINUTES = 5;
    private static DuckQueue queue;
    private static Cart cart;

    public Distributor(){
        queue = new DuckQueue();
        cart = new Cart(CAPACITY);
    }

    public void pickUpOrders() {
        while (!cart.isFull() && !queue.isEmpty()) {
            Order o = queue.peek();
            if (cart.isGoingToFitThenAdd(o.getAmount()))
                queue.remove(o);
        }
        cart.emptyCart();
    }

    private static void sleep_five_sec() {
        try {
            Thread.sleep(10000);  // 6000 * 10 * 5
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    @GET
    @Path("/api")
    @Produces(MediaType.APPLICATION_JSON)
    public String showDirectory() {
        JsonObject orders = new JsonObject();
        JsonArray datasets = new JsonArray();

        for (Order o : queue) {
            JsonObject dataset = new JsonObject();

//            dataset.addProperty("order_id", o.getSerialNum());
            dataset.addProperty("customer_id", o.getCustomerID());
            dataset.addProperty("vip", o.isVIP());
            dataset.addProperty("amount", o.getAmount());
            dataset.addProperty("position_in_queue", getPositionInQueue(o));

            datasets.add(dataset);
            orders.add("order", datasets);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        System.out.println(gson.toJson(orders));
        return new Gson().toJson(orders);
    }

    @GET  // can be @POST @Consumes("application/json")
    @Path("/order/{customerID}/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public String placeOrderOnline(@PathParam("customerID") int customerID, @PathParam("amount") int amount) {
        // TODO check if placed correctly
        placeOrder(customerID, amount);
        return new Gson().toJson("order was placed successfully");
    }

    public void placeOrder(int customerID, int amount) {
        Order o = new Order(customerID, amount);
        queue.add(o);
    }

    @GET
    @Path("/order/{customerID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPositionInQueueByCustomerID(@PathParam("customerID") int customerID) {
        Order o = DuckQueue.getOrderByCustomerID(customerID);
        // TODO check for existence of o
        double waitTime = getApproximateWaitTime(o) * MINUTES;
        String s = getPositionInQueue(o) + " " + waitTime;
        return new Gson().toJson(s);

    }

    // TODO approximate wait time in a queue

    private int getPositionInQueue(Order o) {
        return queue.indexOf(o) + 1;
    }

    @GET
    @Path("/order")
    @Produces(MediaType.APPLICATION_JSON)
    public String managerView() {
        placeSomeOrders();
        return new Gson().toJson(queue.toString());

    }

    @DELETE
    @Path("/order/{customerID}")
    @Produces(MediaType.TEXT_PLAIN)
    public void cancelOrderByCustomerID(@PathParam("customerID") int customerID) {
        Order o = DuckQueue.getOrderByCustomerID(customerID);
        queue.remove(o);
    }

    @DELETE
    @Path("/order")
    @Produces(MediaType.TEXT_PLAIN)
    public String getNextOrder() {
        Order o = queue.remove();
        return o.toString();

    }


    private void placeSomeOrders() {
        placeOrder(1, 20);
        placeOrder(5, 25);
        placeOrder(4, 10);
        placeOrder(1500, 5);
        placeOrder(2, 5);
        placeOrder(2000, 5);
        placeOrder(2001, 20);
    }

    //    public void getNextOrder() {
//        while (!queue.isEmpty()) {
//            pickUpOrders();
//        }
//    }

    public int getApproximateWaitTime(Order o) {
        int size = o.isVIP() ? DuckQueue.customersInVIPQueue : queue.size();
        double pointOnZeroOneInterval = (double) getPositionInQueue(o) / size;

//        System.out.println("size of the queue");
//        System.out.println(size);
//        System.out.println(pointOnZeroOneInterval);

        int items = o.isVIP() ? DuckQueue.itemsInVIPQueue : DuckQueue.itemsInQueue;
        double approxNumberOfItemsInFrontOfCustomerID = pointOnZeroOneInterval * items;

//        System.out.println("number of items in queue");
//        System.out.println(items);
//
//        System.out.println(approxNumberOfItemsInFrontOfCustomerID);

        return (int) approxNumberOfItemsInFrontOfCustomerID / CAPACITY;
    }


}

