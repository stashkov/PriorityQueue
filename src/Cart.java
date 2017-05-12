public class Cart {
    private final int capacity;
    private int quantity;
    private boolean isFull = false;

    /**
     * Class represent a cart which capacity is restricted
     *
     * @param capacity how many items fits into a cart
     */
    public Cart(int capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException("capacity cannot be < 1");

        this.capacity = capacity;
        this.quantity = 0;
    }

    public boolean isGoingToFitThenAdd(int amount) {
        add(amount);
        if (quantity > capacity) {
            remove(amount);
            this.isFull = true;
            return false;
        }
        return true;
    }

    private void add(int amount) {
        if (amount > this.capacity)
            throw new IllegalArgumentException(amount + " > " + this.capacity);
        quantity += amount;
    }

    private void remove(int amount) {
        quantity -= amount;
    }

    public boolean isFull() {
        return isFull;
    }

    public int getQuantity() {
        return quantity;
    }

    private void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void emptyCart() {
        setQuantity(0);
        this.isFull = false;
    }
}

