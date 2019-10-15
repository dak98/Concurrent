import java.security.InvalidParameterException;

import static java.lang.Thread.sleep;

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
                // Ignore this exception
            }
        }
        if (value == 0)
            System.out.println("An error occured! Value can not be 0 here");
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
