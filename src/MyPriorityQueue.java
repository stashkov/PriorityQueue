import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue; // thread safe


public class MyPriorityQueue extends PriorityBlockingQueue<Order>{
    private Set<Integer> set = new HashSet<>();
    private Set<Integer> synset = Collections.synchronizedSet(set);
    private PriorityBlockingQueue<Order> queue = new PriorityBlockingQueue<>();



    // TODO override add method (this can definitely stay here)
    private void add_to_queue(Order o1) {
        if (!synset.contains(o1.getCustomerID())) {
            synset.add(o1.getCustomerID()); // your code is racy!
            queue.add(o1);
        }
    }


    // TODO override remove
    // TODO shouldn't this be in Cart.java ? or Distributor?
    private void poll_from_queue() {
        Order s;
        while(!joeCart.isFull() && !queue.isEmpty()) {
            s = queue.peek();
            if (joeCart.isGonnaFit(s.getAmount())) {  // your code is incredibly racy!
                s = queue.remove();
                synset.remove(s.getCustomerID());
            }
        }
        System.out.printf("Was able to load %s%n", joeCart.getQuantity());
        System.out.println("Waiting 5 min to unload\n");
        joeCart.emptyCart();  // can destroy joeCart if don't need it anymore? Do I need to?
    }



}
