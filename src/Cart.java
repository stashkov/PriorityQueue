public class Cart {
    private final int CAPACITY = 25;
    private int quantity = 0;
    private boolean isFull = false;

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        isFull = false;
    }
}

