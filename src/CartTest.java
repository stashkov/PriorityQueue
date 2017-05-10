import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CartTest {

    @Test
    public void testCartCorrectInstance() {
        try {
            new Cart(55);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCartIllegalValueCapacity() {
        try {
            new Cart(-5);
            fail("CAPACITY cannot be < 1");
        } catch (IllegalArgumentException e) {
        }
    }



    @Test
    public void testCartIsGonnaFit() {
        Cart cart = new Cart(10);
        // TODO amount > capacity catch error
        // TODO is going to fit
        // TODO is not going to fit
//        assertEquals(1, o.getCustomerID());
    }




}
