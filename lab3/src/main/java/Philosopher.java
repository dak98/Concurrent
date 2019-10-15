import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    protected final int id;
    protected Semaphore leftFork, rightFork, table;

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork,
                       Semaphore table) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.table = table;
    }
}
