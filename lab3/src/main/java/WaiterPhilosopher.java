import java.util.concurrent.Semaphore;

public class WaiterPhilosopher extends Philosopher {
    Semaphore table;
    public WaiterPhilosopher(int id, Semaphore leftFork, Semaphore rightFork,
                             Semaphore table) {
        super(id, leftFork, rightFork);
        this.table = table;
    }

    @Override
    public void run() {
        timestamps.add(System.nanoTime());

        while (true) {
            System.out.println("Philosopher" + id + " outside of the table");
            try {
                table.acquire();
                System.out.println("Philosopher" + id + " thinks");

                leftFork.acquire();
                rightFork.acquire();
                System.out.println("Philosopher" + id + " eats");
                timestamps.add(System.nanoTime());
                sleep(2000);
                leftFork.release();
                rightFork.release();

                table.release();
            } catch (InterruptedException e) {
                /*
                 * The ending timestamp is added to eliminate situations where
                 * a philosopher eaten only once, at the start.
                 */
                timestamps.add(System.nanoTime());
                break;
            }

            System.out.println("Philosopher" + id + " leaves the table");
        }
    }
}