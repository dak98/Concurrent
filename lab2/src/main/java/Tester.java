/**
 * Represents a shared state to which the producer writes and consumer reads
 * from.
 */
class SharedBuffer {
    private int buffer;
    private BinarySemaphore semaphore;

    SharedBuffer(int initialValue) {
        buffer = initialValue;
        semaphore = new BinarySemaphore();
    }

    void put(int value) {
        semaphore.P();
        buffer = value;
        semaphore.V();
    }

    int get() {
        int value;
        semaphore.P();
        value = buffer;
        semaphore.V();
        return value;
    }
}

class Producer extends Thread {
    private SharedBuffer buffer;

    Producer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            buffer.put(i);
            System.out.println("Producer: put(" + i + ")");
        }
    }
}

class Consumer extends Thread {
    private SharedBuffer buffer;

    Consumer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Consumer: get() = " + buffer.get());
        }
    }
}

public class Tester {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(0);
        Producer producer0 = new Producer(buffer);
        Consumer consumer0 = new Consumer(buffer);

        producer0.start();
        consumer0.start();
    }
}
