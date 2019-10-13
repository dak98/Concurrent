class CounterBuffer {
    private int buffer;

    CounterBuffer(int initialValue) {
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

class Incrementer extends Thread {
    private CounterBuffer counterBuffer;
    private BinarySemaphore semaphore;

    Incrementer(CounterBuffer counterBuffer, BinarySemaphore semaphore) {
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

class Decrementer extends Thread {
    private CounterBuffer counterBuffer;
    private BinarySemaphore semaphore;

    Decrementer(CounterBuffer counterBuffer, BinarySemaphore semaphore) {
        this.counterBuffer = counterBuffer;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            semaphore.P();
            counterBuffer.decrement();
            semaphore.V();
        }
    }
}

public class Counter {
    public static void main(String[] args) {
        CounterBuffer counterBuffer = new CounterBuffer(0);
        BinarySemaphore semaphore = new BinarySemaphore(1);

        Incrementer incrementer = new Incrementer(counterBuffer, semaphore);
        Decrementer decrementer = new Decrementer(counterBuffer, semaphore);

        incrementer.start();
        decrementer.start();

        try {
            incrementer.join();
            decrementer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final value: " + counterBuffer.getValue());
    }
}
