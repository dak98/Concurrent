public class BinarySemaphore {
    private int value;

    public BinarySemaphore() {
        value = 0;
    }

    /**
     * Increments the value of the semaphore. Blocks if it is already 1.
     */
    public synchronized void P() {
        while (value == 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value = 0;
        notifyAll();
    }

    /**
     * Decrements the value of the semaphore. Blocks if it is already 0.
     */
    public synchronized void V() {
        while (value == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value = 1;
        notifyAll();
    }
}
