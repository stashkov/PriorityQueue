import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;


public class MyPriorityQueue
{
    private static Set<Integer> set = new HashSet<>();
    private static PriorityBlockingQueue<Order> queue = new PriorityBlockingQueue<>();
    private static Set<Integer> synset = Collections.synchronizedSet( set );
    private static Cart joeCart = new Cart();

    public static void main( String[] args )
    {

        Order o1 = new Order( 1, 20 );
        Order o2 = new Order( 5, 25 );
        Order o3 = new Order( 4, 10 );
        Order o4 = new Order( 1500, 5 );
        Order o5 = new Order( 2, 5 );
        Order o6 = new Order( 2000, 5 );
        Order o7 = new Order( 2001, 20 );

        add_to_queue( o1 );
        add_to_queue( o2 );
        add_to_queue( o3 );
        add_to_queue( o4 );
        add_to_queue( o5 );
        add_to_queue( o6 );
        add_to_queue( o7 );

        System.out.println( synset );

        while ( !queue.isEmpty() )
        {
            poll_from_queue();
        }
    }


    private static void add_to_queue( Order o1 )
    {
        if ( !synset.contains( o1.getCustomerID() ) )
        {
            synset.add( o1.getCustomerID() ); // might be a race condition
            queue.add( o1 );
        }
    }

    private static void poll_from_queue()
    {
        Order s;
        while ( !joeCart.isFull() && !queue.isEmpty() )
        {
            s = queue.peek();
            if ( joeCart.isGonnaFit( s.getAmount() ) )
            {
                s = queue.remove();
                synset.remove( s.getCustomerID() );
                System.out.println( "Removing from joeCart..." );
            }
        }
        System.out.printf( "Was able to load %s%n", joeCart.getQuantity() );
        System.out.println( "Waiting 5 min to unload\n" );
        joeCart.setQuantity( 0 );  // can destroy joeCart if don't need it anymore? Do I need to?
    }

}
