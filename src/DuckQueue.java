import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class DuckQueue extends PriorityBlockingQueue<Order> {
    private Set<Integer> set = new HashSet<>();
    private Set<Integer> uniqueCustomers = Collections.synchronizedSet(set);
    private static AtomicInteger NumberOfItemsInQueue;

    public DuckQueue() {
        NumberOfItemsInQueue = new AtomicInteger(0);
    }

    public void addToQueue(Order o) {
        if (!uniqueCustomers.contains(o.getCustomerID())) {
            try {
                uniqueCustomers.add(o.getCustomerID());
                NumberOfItemsInQueue.getAndAdd(o.getAmount());
            } finally {
                super.add(o);
            }
        }
    }

    public void removeFromQueue(Order o) {
        try {
            uniqueCustomers.remove(o.getCustomerID());
            NumberOfItemsInQueue.getAndAdd(-o.getAmount());
        } finally {
            super.remove();
        }
    }

    public Set<Integer> getUniqueCustomers() {
        return uniqueCustomers;
    }

    public static int getNumberOfItemsInQueue() {
        return NumberOfItemsInQueue.get();
    }
}
