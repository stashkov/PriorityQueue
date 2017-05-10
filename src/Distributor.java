import java.util.concurrent.PriorityBlockingQueue;

public class Distributor {
    private MyPriorityQueue queue = new MyPriorityQueue();
    private Cart joeCart = new Cart(25);

    public static void main(String[] args) {


        Order o1 = new Order(1, 20);
        Order o2 = new Order(5, 25);
        Order o3 = new Order(4, 10);
        Order o4 = new Order(1500, 5);
        Order o5 = new Order(2, 5);
        Order o6 = new Order(2000, 5);
        Order o7 = new Order(2001, 20);

        add_to_queue(o1);
        add_to_queue(o2);
        add_to_queue(o3);
        add_to_queue(o4);
        add_to_queue(o5);
        add_to_queue(o6);
        add_to_queue(o7);

        System.out.println(synset);

        while (!queue.isEmpty()) {
            poll_from_queue();
        }
    }

    //TODO private long numberOfDucks += amount
    // class variable. to get wait time numberOfDucks / 25 * 5 (this part should be in distributor class)
    // as I remove from the queue -=
    // also can be 2 separate counters for VIP and not VIP
    // I can also memorize satrt and end time
}
