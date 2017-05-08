import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class OrderTest {
    @Test
    void compareTo() {
    }

    @Test
    public void testOrderCorrectInstance() {
        try {
            new Order(1, 5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testOrderIllegalValueCustomerID() {
        try {
            new Order(1, 50);
            fail("Exception was expected for amount of > 25");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testOrderIllegalValueAmount() {
        try {
            new Order(20001, 1);
            fail("Exception was expected for customerID > 20000");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testOrderGetCustomerID() {
        Order o = new Order(1, 5);
        assertEquals(1, o.getCustomerID());
    }

    @Test
    public void testOrderGetAmount() {
        Order o = new Order(1, 5);
        assertEquals(5, o.getAmount());
    }

    @Test
    public void testOrderIsVIP() {
        Order o = new Order(1, 5);
        assertEquals(true, o.isVIP());
    }

    @Test
    public void testOrderIsNotVIP() {
        Order o = new Order(2222, 5);
        assertEquals(false, o.isVIP());
    }

    @Test
    public void testOrderVIPAndNotVIP() {
        Order vip = new Order(5, 1);
        Order notVIP = new Order(2222, 5);
        assertEquals(-1, vip.compareTo(notVIP));
    }

    @Test
    public void testOrderVIPAndVIP() {
        Order vip = new Order(5, 1);
        Order notVIP = new Order(4, 5);
        assertEquals(-1, vip.compareTo(notVIP));
    }


}
