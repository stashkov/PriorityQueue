
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class DuckQueue1 extends PriorityBlockingQueue<Order> {
    private Set<Integer> set = new HashSet<>();
    private Set<Integer> uniqueCustomers = Collections.synchronizedSet(set);
    private static AtomicInteger numberOfItemsInRegularQueue;
    private static AtomicInteger numberOfItemsInVIPQueue;
    private static Instant lastOrderFulfilledOn;
    private static Instant lastOrderCameOn;
    private static Instant lastVIPOrderCameOn;

    public DuckQueue1() {
        numberOfItemsInRegularQueue = new AtomicInteger(0);
        numberOfItemsInVIPQueue = new AtomicInteger(0);
        lastOrderFulfilledOn = Instant.now();
        lastVIPOrderCameOn = Instant.now();
        lastOrderCameOn = Instant.now();
    }

    public void addToQueue(Order o) {
        if (!isCustomerIDInQueue(o)) {
            try {
                uniqueCustomers.add(o.getCustomerID());
                recordInformationAboutIncomingOrdersInQueue(o);
            } finally {
                super.add(o);
            }
        }
    }

    private boolean isCustomerIDInQueue(Order o) {
        return uniqueCustomers.contains(o.getCustomerID());
    }

    private void recordInformationAboutIncomingOrdersInQueue(Order o) {
        if (o.isVIP())
            updateDataAboutVIPPartOfQueue(o);
        else
            updateDataAboutRegularPartOfQueue(o);

    }

    private void updateDataAboutRegularPartOfQueue(Order o) {
        numberOfItemsInRegularQueue.getAndAdd(o.getAmount());
        lastOrderCameOn = Instant.now();
    }

    private void updateDataAboutVIPPartOfQueue(Order o) {
        numberOfItemsInVIPQueue.getAndAdd(o.getAmount());
        lastVIPOrderCameOn = Instant.now();
    }

    public void removeFromQueue(Order o) {
        try {
            uniqueCustomers.remove(o.getCustomerID());
            updateDataAboutOrderFulfillment(o);
        } finally {
            super.remove();
        }
    }

    private void updateDataAboutOrderFulfillment(Order o) {
        if (o.isVIP())
            numberOfItemsInVIPQueue.getAndAdd(-o.getAmount());
        else
            numberOfItemsInRegularQueue.getAndAdd(-o.getAmount());
        lastOrderFulfilledOn = Instant.now();
    }


    public Set<Integer> getUniqueCustomers() {
        return uniqueCustomers;
    }

    public static int getNumberOfItemsInRegularQueue() {
        return numberOfItemsInRegularQueue.get();
    }

    public static int getNumberOfItemsInVIPQueue() {
        return numberOfItemsInVIPQueue.get();
    }

    public static Instant getLastOrderFulfilledOn() {
        return lastOrderFulfilledOn;
    }

    public static Instant getLastOrderCameOn() {
        return lastOrderCameOn;
    }

    public static Instant getLastVIPOrderCameOn() {
        return lastVIPOrderCameOn;
    }

    public static void setLastOrderFulfilledOn(Instant lastOrderFulfilledOn) {
        DuckQueue1.lastOrderFulfilledOn = lastOrderFulfilledOn;
    }

    public static void setLastOrderCameOn(Instant lastOrderCameOn) {
        DuckQueue1.lastOrderCameOn = lastOrderCameOn;
    }

    public static void setLastVIPOrderCameOn(Instant lastVIPOrderCameOn) {
        DuckQueue1.lastVIPOrderCameOn = lastVIPOrderCameOn;
    }

    public static long getSecondsBetweenFirstAndLastVIPOrder() {
        return Duration.between(getLastOrderFulfilledOn(), getLastVIPOrderCameOn()).toMillis() / 1000;
    }

    public static long getSecondsBetweenFirstAndLastOrder() {
        return Duration.between(getLastOrderFulfilledOn(), getLastOrderCameOn()).toMillis() / 1000;
    }

    public static float getPointOnZeroOneIntervalBetweenHeadAndTail(Order o) {
        long secondsBetweenHeadToTail = o.isVIP() ? getSecondsBetweenFirstAndLastVIPOrder() : getSecondsBetweenFirstAndLastOrder();
        long secondsBetweenHeadToOrder = Duration.between(getLastOrderFulfilledOn(), o.getOrderDate()).toMillis() / 1000;
//        System.out.println(secondsBetweenHeadToTail);
//        System.out.println(secondsBetweenHeadToOrder);
        float point = (float) (secondsBetweenHeadToTail - secondsBetweenHeadToOrder) / secondsBetweenHeadToTail;
        if (point != point)
            return 1;
        else
            return point;
    }

    public static int getNumberOfItemsInEntireQueue() {
        return getNumberOfItemsInRegularQueue() + getNumberOfItemsInVIPQueue();
    }

}
