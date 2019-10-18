import java.util.concurrent.Semaphore;

public class AsymmetricPhilosopher extends Philosopher {
    private Semaphore table;
    public AsymmetricPhilosopher(int id, Semaphore leftFork, Semaphore rightFork,
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
                if (id % 2 == 0) {
                    rightFork.acquire();
                    leftFork.acquire();
                } else {
                    leftFork.acquire();
                    rightFork.acquire();
                }
                table.release();
                timestamps.add(System.nanoTime());
                System.out.println("Philosopher" + id + " eats");
                sleep(2000);
                if (id % 2 == 0) {
                    rightFork.release();
                    leftFork.release();
                } else {
                    leftFork.release();
                    rightFork.release();
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
