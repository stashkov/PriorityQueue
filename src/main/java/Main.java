import java.util.concurrent.PriorityBlockingQueue;


public class Main
{

    public static void main( String[] args )
    {
        PriorityBlockingQueue<Order> queue = new PriorityBlockingQueue<>();
        Buyer buyer1 = new Buyer( "Buyer 1", queue, 15 );
        Buyer buyer2 = new Buyer( "Buyer 2", queue, 10 );
        Buyer buyer3 = new Buyer( "Buyer 3", queue, 5 );
        Seller seller1 = new Seller( "Seller 1", queue );
        Seller seller2 = new Seller( "Seller 2", queue );

        new Thread( buyer1 ).start();
        new Thread( buyer2 ).start();
        new Thread( buyer3 ).start();
        new Thread( seller1 ).start();
        new Thread( seller2 ).start();


    }

}
