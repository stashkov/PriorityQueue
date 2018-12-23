import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Buyer implements Runnable
{
    private static PriorityBlockingQueue<Order> _QUEUE;
    private final String _NAME;
    private final long _WAIT_PERIOD_BETWEEN_ACTIONS;


    public Buyer( String name, PriorityBlockingQueue<Order> queue, long waitPeriodInSeconds )
    {
        _NAME = name;
        _QUEUE = queue;
        _WAIT_PERIOD_BETWEEN_ACTIONS = TimeUnit.SECONDS.toMillis( waitPeriodInSeconds );
    }

    public String getName()
    {
        return _NAME;
    }

    public Order placeOrder()
    {
        int quantity = new Random().nextInt( 25 ) + 1;
        int customerID = new Random().nextInt( 15 ) + 1;
        Order order = new Order( customerID, quantity );
        System.out.printf( "+++Placed order+++++++ %s by %s%n", order, getName() );
        return order;
    }

    @Override
    public void run()
    {
        while ( true )
        {
            try
            {
                _QUEUE.add( placeOrder() );
                Thread.sleep( _WAIT_PERIOD_BETWEEN_ACTIONS );
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
        }
    }
}
