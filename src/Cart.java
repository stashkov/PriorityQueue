public class Cart {
    private final int CAPACITY;
    private int quantity;
    private boolean isFull = false;

    /**
     * Class represent a cart which capacity is restricted
     *
     * @param CAPACITY how many items fits into a cart
     */
    public Cart(int CAPACITY) {
        if (CAPACITY < 1)
            throw new IllegalArgumentException("CAPACITY cannot be < 1");

        this.CAPACITY = CAPACITY;
        this.quantity = 0;
    }

    public boolean isGonnaFit(int amount) {
        add(amount);
        if (quantity > CAPACITY) {
            remove(amount);
            this.isFull = true;
            return false;
        }
        return true;
    }

    private void add(int amount) {
        if (amount > this.CAPACITY)
            throw new IllegalArgumentException(amount + " > " + this.CAPACITY);
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

