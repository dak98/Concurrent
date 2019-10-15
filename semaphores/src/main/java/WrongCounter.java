class WrongCounterBuffer {
    private int buffer;

    WrongCounterBuffer(int initialValue) {
        buffer = initialValue;
    }

    public void increment() {
        buffer++;
    }

    public void decrement() {
        buffer--;
    }

    public int getValue() {
        return buffer;
    }
}

class WrongIncrementer extends Thread {
    private WrongCounterBuffer counterBuffer;
    private WrongSemaphore semaphore;

    WrongIncrementer(WrongCounterBuffer counterBuffer,
                     WrongSemaphore semaphore) {
        this.counterBuffer = counterBuffer;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            semaphore.P();
            counterBuffer.increment();
            semaphore.V();
        }
    }
}

public class WrongCounter {
    public static void main(String[] args) {
        WrongCounterBuffer counterBuffer = new WrongCounterBuffer(0);
        WrongSemaphore semaphore = new WrongSemaphore(1);

        WrongIncrementer incrementer = new WrongIncrementer(counterBuffer,
                                                            semaphore);

        incrementer.start();
        for (int i = 0; i < 100; i++) {
            Thread tid = new Thread(() -> {
                semaphore.P();
                counterBuffer.decrement();
                semaphore.V();
            });
            tid.start();
            tid.interrupt();
        }

        try {
            incrementer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final value: " + counterBuffer.getValue());
    }
}
