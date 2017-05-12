public class Distributor {
    // TODO fix the problem with multiple inheritance
    private DuckQueue queue;
    private Cart cart = new Cart(25);

    public Distributor(DuckQueue queue) {
        this.queue = queue;
    }

    public void pickUpOrders() {
        // your code is incredibly racy!
        while (!cart.isFull() && !queue.isEmpty()) {
                Order o = queue.peek();
                System.out.printf("Processing %s", o.toString());
                if (cart.isGoingToFitThenAdd(o.getAmount()))
                    queue.removeFromQueue(o);

        }
        System.out.printf("Was able to load %s%n", cart.getQuantity());
        System.out.println("Waiting 5 min to unload\n");
        cart.emptyCart();
    }

//TODO private long numberOfDucks += amount
// class variable. to get wait time numberOfDucks / 25 * 5 (this part should be in distributor class)
// as I remove from the queue -=
// also can be 2 separate counters for VIP and not VIP
// I can also memorize satrt and end time
}
