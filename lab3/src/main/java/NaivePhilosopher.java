import java.util.concurrent.Semaphore;

public class NaivePhilosopher extends Philosopher {
    public NaivePhilosopher(int id, Semaphore leftFork, Semaphore rightFork) {
        super(id, leftFork, rightFork);
    }

    @Override
    public void run() {
        timestamps.add(System.nanoTime());

        while (true) {
            System.out.println("Philosopher" + id + " thinks");
            try {
                leftFork.acquire();
                rightFork.acquire();
                timestamps.add(System.nanoTime());
                System.out.println("Philosopher" + id + " eats");
                sleep(2000);
                leftFork.release();
                rightFork.release();
            } catch (InterruptedException e) {
                /*
                 * The ending timestamp is added to eliminate situations where
                 * a philosopher eaten only once, at the start.
                 */
                timestamps.add(System.nanoTime());
                break;
            }
        }
    }
}
