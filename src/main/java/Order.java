import java.util.concurrent.atomic.AtomicLong;
import java.time.Instant;

public class Order implements Comparable<Order>
{
    private final int _CUSTOMER_ID;
    private final int _AMOUNT;
    private final boolean _IS_VIP;
    private final static AtomicLong _TOTAL_ORDERS = new AtomicLong( 0L );
    private final long _ORDER_ID;
    private final Instant _CURRENT_TIMESTAMP;
    private static final int _ORDER_AMOUNT_LIMIT = 25;


    /**
     * Construct Order
     *
     * @param customerID - customerID who placed an order
     * @param amount     - amount that customer have ordered
     */
    public Order( int customerID, int amount )
    {
        final int customerLimit = 20000;

        if ( customerID > customerLimit )
            throw new IllegalArgumentException( "CustomerID > " + customerLimit );

        if ( amount > _ORDER_AMOUNT_LIMIT )
            throw new IllegalArgumentException( "Amount > " + _ORDER_AMOUNT_LIMIT );

        _CUSTOMER_ID = customerID;
        _AMOUNT = amount;
        _IS_VIP = customerID < 5;
        _ORDER_ID = _TOTAL_ORDERS.incrementAndGet();
        _CURRENT_TIMESTAMP = Instant.now();
    }

    @Override
    public String toString()
    {
        return String.format( "VIP: %5s CustomerID: %2d Amount: %2d OrderID : %3d SecondsInQueue: %2d",
                isVIP(), getCustomerID(), getAmount(), getOrderID(), getMillisecondsInQueue() );
    }

    @Override
    public int compareTo( Order order )
    {
        int result = compareVIPStatus( order );
        if ( result != 0 )
        {
            return result;
        }
        return compareOrderID( order );
    }

    private int compareOrderID( Order order )
    {
        return Long.compare( _ORDER_ID, order.getOrderID() );
    }

    private int compareVIPStatus( Order order )
    {
        return -Boolean.compare( _IS_VIP, order.isVIP() );
    }

    public int getCustomerID()
    {
        return _CUSTOMER_ID;
    }

    public int getAmount()
    {
        return _AMOUNT;
    }

    public boolean isVIP()
    {
        return _IS_VIP;
    }

    public long getOrderID()
    {
        return _ORDER_ID;
    }

    public long getMillisecondsInQueue()
    {
        return ( Instant.now().toEpochMilli() - _CURRENT_TIMESTAMP.toEpochMilli() ) / 1000;
    }
}