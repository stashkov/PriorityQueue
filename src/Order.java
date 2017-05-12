import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import java.time.Instant;

public class Order implements Comparable<Order> {
    private final int customerID;
    private final int amount;
    private final boolean isVIP;
    private final static AtomicLong serialNumGenerator = new AtomicLong(0L);
    private final long serialNum;
    private final Instant current_timestamp;


    /**
     * Construct Order
     *
     * @param customerID - customerID who placed an order
     * @param amount     - amount that customer have ordered
     */
    public Order(int customerID, int amount) {
        final int customerLimit = 20000;
        final int orderAmount = 25;

        if (customerID > customerLimit)
            throw new IllegalArgumentException("customerID > " + customerLimit);

        if (amount > orderAmount)
            throw new IllegalArgumentException("Amount > " + orderAmount);

        this.customerID = customerID;
        this.amount = amount;
        this.isVIP = customerID < 1000 ? true : false;
        this.serialNum = serialNumGenerator.getAndIncrement();
        this.current_timestamp = Instant.now();
    }

    @Override
    public String toString() {
        return String.format("VIP: %s CustomerID: %s Amount: %s SerialNumber : %s SecondsInQueue: %s%n",
                isVIP(), getCustomerID(), getAmount(), getSerialNum(), getMillisecondsInQueue());
    }

    @Override
    public int compareTo(Order o) {
        int d = compareVIPStatus(o);
        if (d != 0)
            return d;
        d = compareSerialNumber(o);
        return d;
    }

    public int compareSerialNumber(Order o) {
        return (serialNum < o.serialNum)
                ? -1
                : ((serialNum > o.serialNum) ? 1 : 0);
    }

    public int compareVIPStatus(Order o) {
        return -Boolean.compare(isVIP, o.isVIP);
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public long getSerialNum() {
        return serialNum;
    }

    public long getMillisecondsInQueue() {
        return Duration.between(Instant.now(), this.current_timestamp).toMillis() / 1000;
    }
}