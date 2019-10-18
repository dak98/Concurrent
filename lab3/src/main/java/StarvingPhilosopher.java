import java.util.concurrent.Semaphore;

public class StarvingPhilosopher extends Philosopher {
    private Semaphore table;

    public StarvingPhilosopher(int id, Semaphore leftFork, Semaphore rightFork,
                               Semaphore table) {
        super(id, leftFork, rightFork);
        this.table = table;
    }

    @Override
    public void run() {
        timestamps.add(System.nanoTime());

        while (true) {
            System.out.println("Philosopher" + id + " thinks");
            try {
                table.acquire();
                if (leftFork.tryAcquire() && rightFork.tryAcquire()) {
                    table.release();
                    System.out.println("Philosopher" + id + " eats");
                    timestamps.add(System.nanoTime());
                    sleep(2000);
                    leftFork.release();
                    rightFork.release();
                } else {
                    leftFork.release();
                    table.release();
                }
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
