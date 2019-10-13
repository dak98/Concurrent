class Actor extends Thread {
    private CountingSemaphore semaphore;
    private final int id;

    Actor(int id, CountingSemaphore semaphore) {
        this.id = id;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println("Actor " + id + " waits for the semaphore");
        synchronized (semaphore) {
            semaphore.P();
        }
        System.out.println("Actor " + id + " got the semaphore");
        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.V();
        System.out.println("Actor " + id + " released the semaphore");
    }
}

public class LimitedBuffer {
    public static void main(String[] args) {
        Actor[] actors = new Actor[100];
        CountingSemaphore semaphore = new CountingSemaphore(2);
        for (int i = 0; i < 100; i++) {
            actors[i] = new Actor(i, semaphore);
            actors[i].start();
        }
        for (int i = 0; i < 100; i++)
            try {
                actors[i].join();
                System.out.println("Actor " + i + " joined");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
