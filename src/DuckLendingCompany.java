public class DuckLendingCompany {
    public static void main(String[] args) {
        DuckQueue queue = new DuckQueue();
        Distributor joe = new Distributor(queue);

        Order o1 = new Order(1, 20);
        Order o2 = new Order(5, 25);
        Order o3 = new Order(4, 10);
        Order o4 = new Order(1500, 5);
        Order o5 = new Order(2, 5);
        Order o6 = new Order(2000, 5);
        Order o7 = new Order(2001, 20);


        queue.addToQueue(o1);
        queue.addToQueue(o2);
        queue.addToQueue(o3);
        queue.addToQueue(o4);
        queue.addToQueue(o5);
        queue.addToQueue(o6);
        queue.addToQueue(o7);

        while(!queue.isEmpty()){
            joe.pickUpOrders();
            sleep_five_sec();

        }
    }

    private static void sleep_five_sec() {
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
