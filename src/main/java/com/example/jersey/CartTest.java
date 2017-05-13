package com.example.jersey;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CartTest {

    @Test
    public void testCartCorrectInstance() {
        try {
            new Cart(20);
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
    public void testCartAmountGreaterThanCapacity() {
        try {
            Cart cart = new Cart(20);
            cart.isGoingToFitThenAdd(21);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testCartAmountLessThanCapacity() {
        try {
            Cart cart = new Cart(20);
            cart.isGoingToFitThenAdd(19);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCartAmountExactlyEqualCapacity() {
        try {
            Cart cart = new Cart(20);
            cart.isGoingToFitThenAdd(20);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCartIsFull() {
        Cart cart = new Cart(20);
        cart.isGoingToFitThenAdd(20);
        assertEquals(cart.isFull(), false);

    }

    @Test
    public void testCartQuantityInside() {
        Cart cart = new Cart(20);
        cart.isGoingToFitThenAdd(15);
        assertEquals(cart.getQuantity(), 15);

    }
    @Test
    public void testEmptyCart() {
        Cart cart = new Cart(20);
        cart.isGoingToFitThenAdd(15);
        cart.emptyCart();
        assertEquals(cart.getQuantity(), 0);
    }
}
