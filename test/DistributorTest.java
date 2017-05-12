import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;

public class DistributorTest {
    private DuckQueue1 q;

    @BeforeMethod
    public void setUp() throws Exception {
        q = new DuckQueue1();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        q = null;
    }
    @Test()
    public void testGetApproximateWaitTimeForOrder(){
        Instant head = Instant.now().plusMillis(-10001);
        Instant tail = Instant.now().plusMillis(1000);
        Order o = new Order(20000, 20);
        DuckQueue1.setLastOrderFulfilledOn(head);
        q.addToQueue(o);  // should be between 2 setters
        DuckQueue1.setLastOrderCameOn(tail);
        assertEquals(Distributor.getApproximateWaitTimeForOrder(o), 0);
    }

    @Test()
    public void testGetApproximateWaitTimeForOrder2(){
        Instant head = Instant.now().plusMillis(-1001);
        Instant tail = Instant.now().plusMillis(1000);
        Order o = new Order(20000, 20);
        Order o1 = new Order(5000, 20);
        Order o2 = new Order(5001, 20);
        Order o3 = new Order(5002, 20);
        DuckQueue1.setLastOrderFulfilledOn(head);
        q.addToQueue(o);  // should be between 2 setters
        q.addToQueue(o1);
        q.addToQueue(o2);
        q.addToQueue(o3);
        DuckQueue1.setLastOrderCameOn(tail);
        assertEquals(Distributor.getApproximateWaitTimeForOrder(o3), 300);
    }
}
