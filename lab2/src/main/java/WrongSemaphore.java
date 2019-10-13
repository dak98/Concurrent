import java.security.InvalidParameterException;

public class WrongSemaphore {
    private int value;

    public WrongSemaphore(int intialValue) {
        if (intialValue < 0 || intialValue > 1)
            throw new InvalidParameterException("Initial values different " +
                    "from 0 or 1");
        value = intialValue;
    }

    /**
     * Decrements the value of the semaphore. Blocks if it is already 1.
     */
    public synchronized void P() {
        if (value == 0) {
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
     * Increments the value of the semaphore.
     */
    public synchronized void V() {
        value = 1;
        notifyAll();
    }
}
