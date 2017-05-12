import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

import static org.testng.Assert.*;

public class DuckQueueTest {
    private DuckQueue q;

    @BeforeMethod
    public void setUp() throws Exception {
        q = new DuckQueue();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        q = null;
    }

    @Test()
    public void testDuckCorrectInstance() {
        try {
            new DuckQueue();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test()
    public void testEmptyQueue() {
        assertEquals(DuckQueue.getNumberOfItemsInRegularQueue(), 0);
    }

    @Test()
    public void testOneElement() {
        Order o = new Order(1, 6);
        q.addToQueue(o);
        assertEquals(DuckQueue.getNumberOfItemsInVIPQueue(), 6);
    }

    @Test()
    public void testTwoElements() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(2, 5);
        q.addToQueue(o1);
        q.addToQueue(o2);
        assertEquals(DuckQueue.getNumberOfItemsInVIPQueue(), 10);
    }

    @Test()
    public void testSameCustomerID() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(1, 6);
        q.addToQueue(o1);
        q.addToQueue(o2);
        assertEquals(DuckQueue.getNumberOfItemsInVIPQueue(), 5);
    }

    @Test()
    public void testUniqueCustomers() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(1, 6);
        q.addToQueue(o1);
        q.addToQueue(o2);
        assertEquals(q.getUniqueCustomers(), new HashSet<>(Arrays.asList(1)));
    }

    @Test()
    public void testAddAndRemoveOrderFromQueue() {
        Order o1 = new Order(1, 5);
        q.addToQueue(o1);
        q.removeFromQueue(o1);
        assertEquals(DuckQueue.getNumberOfItemsInVIPQueue(), 0);
    }

    @Test()
    public void testAddAndRemoveOrderFromQueue2Orders() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(2, 6);
        q.addToQueue(o1);
        q.addToQueue(o2);
        q.removeFromQueue(o1);
        assertEquals(DuckQueue.getNumberOfItemsInVIPQueue(), 6);
    }

    @Test()
    public void testUniqueCustomersRemoveFromQueue() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(2, 6);
        q.addToQueue(o1);
        q.addToQueue(o2);
        q.removeFromQueue(o1);
        assertEquals(q.getUniqueCustomers(), new HashSet<>(Arrays.asList(2)));
    }






    @Test()
    public void testTwoElementsInRegularQueue() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(10000, 10);
        q.addToQueue(o1);
        q.addToQueue(o2);
        assertEquals(DuckQueue.getNumberOfItemsInRegularQueue(), 10);
    }

    @Test()
    public void testAddAndRemoveOrderFromQueueRegularQueue() {
        Order o1 = new Order(10000, 5);
        q.addToQueue(o1);
        q.removeFromQueue(o1);
        assertEquals(DuckQueue.getNumberOfItemsInRegularQueue(), 0);
    }

    @Test()
    public void testAddAndRemoveOrderFromQueue2OrdersRegularQueue() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(10000, 6);
        q.addToQueue(o1);
        q.addToQueue(o2);
        q.removeFromQueue(o1);
        assertEquals(DuckQueue.getNumberOfItemsInRegularQueue(), 6);
    }

    @Test()
    public void testGetLengthOfVIPQueueInSeconds(){
        Instant head = Instant.now();
        Instant vipTail = Instant.now().plusMillis(3000);
        DuckQueue.setLastOrderFulfilledOn(head);
        DuckQueue.setLastVIPOrderCameOn(vipTail);
        assertEquals(DuckQueue.getSecondsBetweenFirstAndLastVIPOrder(), 3);
    }

    @Test()
    public void testGetLengthOfQueueInSeconds(){
        Instant head = Instant.now();
        Instant tail = Instant.now().plusMillis(3000);
        DuckQueue.setLastOrderFulfilledOn(head);
        DuckQueue.setLastOrderCameOn(tail);
        assertEquals(DuckQueue.getSecondsBetweenFirstAndLastOrder(), 3);
    }

    @Test()
    public void testGetPositionBetweenHeadAndTail(){
        Instant head = Instant.now().plusMillis(-10001);
        Instant tail = Instant.now().plusMillis(10001);
        Order o = new Order(20000, 20);
        DuckQueue.setLastOrderFulfilledOn(head);
        q.addToQueue(o);  // should be between 2 setters
        DuckQueue.setLastOrderCameOn(tail);
        float pointOnInterval = DuckQueue.getPointOnZeroOneIntervalBetweenHeadAndTail(o);
        assertEquals(pointOnInterval, 0.5, 0.1);
    }

    @Test()
    public void testGetPositionBetweenHeadAndTail2(){
        Instant head = Instant.now().plusMillis(-10001);
        Instant tail = Instant.now().plusMillis(5001);
        Order o = new Order(20000, 20);
        DuckQueue.setLastOrderFulfilledOn(head);
        q.addToQueue(o);  // should be between 2 setters
        DuckQueue.setLastOrderCameOn(tail);
        float pointOnInterval = DuckQueue.getPointOnZeroOneIntervalBetweenHeadAndTail(o);
        assertEquals(pointOnInterval, 0.3, 0.1);
    }

    @Test()
    public void testGetPositionBetweenHeadAndTail3(){
        Instant head = Instant.now().plusMillis(-10001);
        Instant tail = Instant.now().plusMillis(1000);
        Order o = new Order(20000, 20);
        DuckQueue.setLastOrderFulfilledOn(head);
        q.addToQueue(o);  // should be between 2 setters
        DuckQueue.setLastOrderCameOn(tail);
        float pointOnInterval = DuckQueue.getPointOnZeroOneIntervalBetweenHeadAndTail(o);
        assertEquals(pointOnInterval, 0.1, 0.1);
    }

}


