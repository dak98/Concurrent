import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

enum Method {
    ASYMMETRIC,
    NAIVE,
    STARVE,
    WAITER
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        // Parse input arguments
        if (args.length != 4) {
            System.out.println("Expected 4 parameters but got " +
                               args.length + ". Should be " +
                               "[# of philosophers] " +
                               "[# of allowed at the table] " +
                               "[method] " +
                               "[duration in seconds]");
            System.exit(1);
        }

        int philosophersCount = Integer.parseInt(args[0]);
        int allowedAtTable = Integer.parseInt(args[1]);
        Method method = Method.valueOf(args[2]);
        long duration = Integer.parseInt(args[3]);

        // Create forks and other semaphores
        ArrayList<Semaphore> forks = new ArrayList<>();
        for (int i = 0; i < philosophersCount; i++)
            forks.add(new Semaphore(1));
        Semaphore table = new Semaphore(allowedAtTable);

        // Create philosophers
        ArrayList<Philosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < philosophersCount; i++) {
            var leftFork = forks.get(i);
            var rightFork = forks.get((i + 1) % philosophersCount);

            switch (method) {
                case ASYMMETRIC:
                    philosophers.add(new AsymmetricPhilosopher(i + 1, leftFork,
                                                               rightFork, table));
                    break;
                case NAIVE:
                    philosophers.add(new NaivePhilosopher(i + 1, leftFork,
                                                          rightFork));
                    break;
                case STARVE:
                    philosophers.add(new StarvingPhilosopher(i + 1, leftFork,
                                                             rightFork, table));
                    break;
                case WAITER:
                    philosophers.add(new WaiterPhilosopher(i + 1, leftFork,
                                                           rightFork, table));
            }
        }

        // Start the experiment
        for (Philosopher philosopher : philosophers)
            philosopher.start();

        try {
            sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // End the experiment
        for (Philosopher philosopher : philosophers)
            philosopher.interrupt();

        // Collect the results
        for (int i = 0; i < philosophersCount; i++) {
            var time = philosophers.get(i).getAverageWaitTime();
            System.out.println("Philosopher" + (i + 1) + ": " + time);
        }
    }
}
