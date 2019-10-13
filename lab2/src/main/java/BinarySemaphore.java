import java.security.InvalidParameterException;

public class BinarySemaphore {
    /**
     * This variable can have one of two values: 0 or 1 (the default). Value 0
     * means that the semaphore is taken and any thread which wants to use it
     * must wait.
     */
    private int value;

    public BinarySemaphore(int intialValue) {
        if (intialValue < 0 || intialValue > 1)
            throw new InvalidParameterException("Initial values different " +
                                                "from 0 or 1");
        value = intialValue;
    }

    /**
     * Decrements the value of the semaphore. Blocks if it is already 1.
     */
    public synchronized void P() {
        while (value == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value = 0;
    }

    /**
     * Increments the value of the semaphore.
     */
    public synchronized void V() {
        value = 1;
        notify();
    }
}
