package com.example.jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

@Path("/")
public class Distributor {
    private static final int CAPACITY = 25;
    public static final int MINUTES = 5;
    private static DuckQueue queue = new DuckQueue();
    private static Cart cart = new Cart(CAPACITY);
//
//    public Distributor() {
//        queue = new DuckQueue();
//        cart = new Cart(CAPACITY);
    // test doesn't work if I define constructor here
    // but if I do then REST redifines it every time
    // TODO looks like I have to move REST part out of here
//    }

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
        JsonObject orders = getJsonOrders();
//        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return new Gson().toJson(orders);
    }

    private JsonObject getJsonOrders() {
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
        return orders;
    }

    @GET  // can be @POST @Consumes("application/json")
    @Path("/order/{customerID}/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public String placeOrderOnline(@PathParam("customerID") int customerID, @PathParam("amount") int amount) {
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
        JsonObject order = new JsonObject();
        if (o != null) {
            JsonArray datasets = getJsonSpecificOrderForCustomer(o);
            order.add("order", datasets);
        }
        return new Gson().toJson(order);

    }

    private JsonArray getJsonSpecificOrderForCustomer(Order o) {
        JsonArray datasets = new JsonArray();
        JsonObject dataset = new JsonObject();

        double waitTime = getApproximateWaitTime(o) * MINUTES;
//      dataset.addProperty("order_id", o.getSerialNum());
//      dataset.addProperty("customer_id", o.getCustomerID());
//      dataset.addProperty("vip", o.isVIP());
//      dataset.addProperty("amount", o.getAmount());
        dataset.addProperty("position_in_queue", getPositionInQueue(o));
        dataset.addProperty("approx_wait_time_minutes", waitTime);

        datasets.add(dataset);
        return datasets;
    }

    private int getPositionInQueue(Order o) {
        return queue.indexOf(o) + 1;
    }

    @GET
    @Path("/order")
    @Produces(MediaType.APPLICATION_JSON)
    public String managerView() {
        JsonObject orders = new JsonObject();
        JsonArray datasets = new JsonArray();
        for (Order o : queue) {
            JsonObject dataset = getOrderInfo(o);
            datasets.add(dataset);
            orders.add("order", datasets);
        }
        return new Gson().toJson(orders);

    }

    private JsonObject getOrderInfo(Order o) {
        JsonObject dataset = new JsonObject();
        double waitTime = getApproximateWaitTime(o) * MINUTES;
//            dataset.addProperty("order_id", o.getSerialNum());
        dataset.addProperty("customer_id", o.getCustomerID());
        dataset.addProperty("vip", o.isVIP());
        dataset.addProperty("amount", o.getAmount());
        dataset.addProperty("position_in_queue", getPositionInQueue(o));
        dataset.addProperty("approx_wait_time_minutes", waitTime);
        return dataset;
    }

    @DELETE
    @Path("/order/{customerID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String cancelOrderByCustomerID(@PathParam("customerID") int customerID) {
        Order o = DuckQueue.getOrderByCustomerID(customerID);
        if (o != null)
            queue.remove(o);
        return new Gson().toJson("order was removed successfully");
    }

    @DELETE
    @Path("/order")
    @Produces(MediaType.TEXT_PLAIN)
    public String getNextOrder() {
        Order o = queue.remove();
        return new Gson().toJson(o.toString());
    }


    //    public void getNextOrder() {
//        while (!queue.isEmpty()) {
//            pickUpOrders();
//        }
//    }


    public int getTotalWaitTimeVIP() {
        // if itemsInVIPQueue < CAPACITY we get 1
        // but for large numbers it works OK
        double waitTime = (double) DuckQueue.itemsInVIPQueue / CAPACITY;
        return (int) Math.rint(waitTime);
    }

    public int getMinutesInQueue(Order o) {
        double pointOnZeroOneInterval = getPointOnZeroOneInterval(o);
        int items = DuckQueue.getNumberOfItemsInQueue(o);
        double approxNumberOfItemsInFrontOfCustomerID = pointOnZeroOneInterval * items;
        return (int) approxNumberOfItemsInFrontOfCustomerID / CAPACITY;
    }

    public int getApproximateWaitTime(Order o) {
        if (o.isVIP())
            return getMinutesInQueue(o);
        else
            return getTotalWaitTimeVIP() + getMinutesInQueue(o);
    }

    private double getPointOnZeroOneInterval(Order o) {
        int position = o.isVIP() ? getPositionInQueue(o) : getPositionInQueue(o) - DuckQueue.getCustomersInVIPQueue();

        int size = DuckQueue.getNumberOfCustomersInQueue(o);
        return (double) position / size;
    }


}

