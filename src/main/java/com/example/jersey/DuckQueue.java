package com.example.jersey;

import java.util.*;


public class DuckQueue extends LinkedList<Order> {
    public static Map<Integer, Order> map;
    public static int itemsInQueue;
    public static int itemsInVIPQueue;
    public static int customersInVIPQueue;


    public DuckQueue() {
        map = new HashMap<>();
        itemsInQueue = 0;
        itemsInVIPQueue = 0;
        customersInVIPQueue = 0;
    }


    @Override
    public boolean add(Order order) {
        if (!map.containsKey(order.getCustomerID())) {
            map.put(order.getCustomerID(), order);
            incrementStatisticsAboutOrders(order);
            return sortedInsert(order);
        } else
            return false;
    }

    private boolean sortedInsert(Order order) {
        ListIterator<Order> iter = listIterator();
        while (true) {
            if (emptyThenInsert(order, iter)) return true;
            if (notEmptyThenCompareAndInsert(order, iter)) return true;
        }
    }

    private boolean notEmptyThenCompareAndInsert(Order order, ListIterator<Order> iter) {
        Order elementInList = iter.next();
        if (elementInList.compareTo(order) > 0) {
            iter.previous();
            iter.add(order);
            return true;
        }
        return false;
    }

    private boolean emptyThenInsert(Order order, ListIterator<Order> iter) {
        if (!iter.hasNext()) {
            iter.add(order);
            return true;
        }
        return false;
    }

    private void incrementStatisticsAboutOrders(Order order) {
        itemsInQueue += order.getAmount();
        if (order.isVIP()) {
            itemsInVIPQueue += order.getAmount();
            customersInVIPQueue++;
        }
    }

    private void decrementStatisticsAboutOrders(Order order) {
        itemsInQueue -= order.getAmount();
        if (order.isVIP()) {
            itemsInVIPQueue -= order.getAmount();
            customersInVIPQueue--;
        }
    }

    @Override
    public Order remove() {
        Order order = super.remove();
        map.remove(order.getCustomerID());
        decrementStatisticsAboutOrders(order);
        return order;
    }


    public boolean remove(Order order) {
        map.remove(order.getCustomerID());
        decrementStatisticsAboutOrders(order);
        return super.remove(order);
    }

    public static Order getOrderByCustomerID(int customerID) {
        Order o = map.get(customerID);
        if (o == null)
            throw new IllegalArgumentException("Requested CustomerID does not exist");
        return o;
    }

}
