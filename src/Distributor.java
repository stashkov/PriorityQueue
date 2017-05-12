public class Distributor {
    private DuckQueue queue;
    private Cart cart = new Cart(25);

    public Distributor(DuckQueue queue) {
        this.queue = queue;
    }

    public void pickUpOrders() {
        while (!cart.isFull() && !queue.isEmpty()) {
            Order o = queue.peek();  // your code is incredibly racy!
            System.out.printf("Processing %s", o.toString());
            if (cart.isGoingToFitThenAdd(o.getAmount()))
                queue.removeFromQueue(o);
        }

        System.out.printf("Was able to load %s%n", cart.getQuantity());
        System.out.println("Waiting 5 min\n");
        cart.emptyCart();
    }

    public static int getApproximateWaitTimeForOrder(Order o) {
        float pointOnInterval = DuckQueue.getPointOnZeroOneIntervalBetweenHeadAndTail(o);
        int numberOfItemsInQueue = o.isVIP() ? DuckQueue.getNumberOfItemsInVIPQueue() : DuckQueue.getNumberOfItemsInEntireQueue();
        int secondsToWaitBetweenEachServing = 10; // 5 * 60
        int maximumNumberOfItemPerServing = 25;
//        System.out.println(numberOfItemsInQueue);
//        System.out.println(pointOnInterval);
//        System.out.println(pointOnInterval * numberOfItemsInQueue);
//        System.out.println((int) (pointOnInterval * numberOfItemsInQueue / maximumNumberOfItemPerServing));
//        System.out.println((int) (pointOnInterval * numberOfItemsInQueue / maximumNumberOfItemPerServing) * secondsToWaitBetweenEachServing);
        return (int) (pointOnInterval * numberOfItemsInQueue / maximumNumberOfItemPerServing) * secondsToWaitBetweenEachServing;
    }

}
