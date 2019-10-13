import static java.lang.Thread.sleep;

/**
 * Represents a shared state to which the producer writes and consumer reads
 * from.
 */
class SharedBuffer {
    private int buffer;
    private WrongSemaphore semaphore;

    SharedBuffer(int initialValue) {
        buffer = initialValue;
        semaphore = new WrongSemaphore(1);
    }

    void put(int value) {
        semaphore.P();
        buffer = value;

        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
    private final int id;
    private SharedBuffer buffer;

    Consumer(int id, SharedBuffer buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Consumer" + id + ": get() = " + buffer.get());
        }
    }
}

public class Tester {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(0);
        Producer producer0 = new Producer(buffer);
        Producer producer1 = new Producer(buffer);
        Consumer consumer0 = new Consumer(0, buffer);
        Consumer consumer1 = new Consumer(1, buffer);
        Consumer consumer2 = new Consumer(2, buffer);
        Consumer consumer3 = new Consumer(3, buffer);

        producer0.start();
        producer1.start();
        consumer0.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
}
