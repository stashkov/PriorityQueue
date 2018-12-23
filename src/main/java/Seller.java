import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Seller implements Runnable
{
    private final String _NAME;
    private final PriorityBlockingQueue<Order> _QUEUE;
    private final List<Order> _PACKAGE_OF_ORDERS = new ArrayList<>();
    private int _TOTAL_QUANTITY;
    private int _PACKAGE_SIZE = 25;

    public Seller( String name, PriorityBlockingQueue<Order> queue )
    {
        _NAME = name;
        _QUEUE = queue;
    }

    public String getName()
    {
        return _NAME;
    }

    public void serveOrders() throws InterruptedException
    {
        if ( !_PACKAGE_OF_ORDERS.isEmpty() )
        {
            simulateLoad();
            int totalQuantity = _PACKAGE_OF_ORDERS.stream().map( Order::getAmount ).reduce( 0, Integer::sum );
            System.out.printf(
                    "Full capacity reached. Serving orders: %s by %s In total %d items%n",
                    Arrays.toString( _PACKAGE_OF_ORDERS.toArray() ), getName(), totalQuantity
            );
            _TOTAL_QUANTITY = 0;
            _PACKAGE_OF_ORDERS.clear();
        }

    }

    private void simulateLoad() throws InterruptedException
    {
        Thread.sleep( TimeUnit.SECONDS.toMillis( 5 ) );
    }

    public void addToPackage( Order order )
    {
        _PACKAGE_OF_ORDERS.add( order );
        System.out.printf( "---Collecting order--- %s by %s. Total package %2d items.%n", order, getName(), _TOTAL_QUANTITY );
    }

    @Override
    public void run()
    {
        while ( true )
        {
            try
            {
                Order order = _QUEUE.peek();  // happy race condition!
                if ( order != null )
                {
                    processOrders( order );
                }
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
        }
    }

    private void processOrders( Order order ) throws InterruptedException
    {
        if ( fitsIntoPackage( order.getAmount() ) )
        {
            addToPackage( _QUEUE.take() );
        }
        else
        {
            serveOrders();
        }
    }

    private boolean fitsIntoPackage( int quantity )
    {
        if ( _TOTAL_QUANTITY + quantity <= _PACKAGE_SIZE )
        {
            _TOTAL_QUANTITY += quantity;
            return true;
        }
        return false;
    }
}
