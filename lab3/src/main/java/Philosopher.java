import java.util.LinkedList;
import java.util.concurrent.Semaphore;


class Philosopher extends Thread {
    protected int id;
    protected Semaphore leftFork, rightFork;
    protected LinkedList<Long> timestamps = new LinkedList<>();

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public double getAverageWaitTime() {
        double averageWaitTime = 0.0;
        for (int i = 0; i < timestamps.size() - 1; i++) {
            long duration = (timestamps.get(i + 1) - timestamps.get(i));
            averageWaitTime += duration / 1000000000.0 - 2;
        }
        return averageWaitTime / timestamps.size() + 2;
    }
}
