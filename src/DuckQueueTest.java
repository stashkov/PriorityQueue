import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
        assertEquals(DuckQueue.getNumberOfItemsInQueue(), 0);
    }

    @Test()
    public void testOneElement() {
        Order o = new Order(1, 6);
        q.addToQueue(o);
        assertEquals(DuckQueue.getNumberOfItemsInQueue(), 6);
    }

    @Test()
    public void testTwoElements() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(2, 5);
        q.addToQueue(o1);
        q.addToQueue(o2);
        assertEquals(DuckQueue.getNumberOfItemsInQueue(), 10);
    }

    @Test()
    public void testSameCustomerID() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(1, 6);
        q.addToQueue(o1);
        q.addToQueue(o2);
        assertEquals(DuckQueue.getNumberOfItemsInQueue(), 5);
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
        assertEquals(DuckQueue.getNumberOfItemsInQueue(), 0);
    }

    @Test()
    public void testAddAndRemoveOrderFromQueue2Orders() {
        Order o1 = new Order(1, 5);
        Order o2 = new Order(2, 6);
        q.addToQueue(o1);
        q.addToQueue(o2);
        q.removeFromQueue(o1);
        assertEquals(DuckQueue.getNumberOfItemsInQueue(), 6);
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
}


