public class CountingSemaphore {
    private BinarySemaphore countSemaphore, valueSemaphore;
    private int currentThreadCount;

    /**
     *
     * @param maximumThreadCount The number of threads allows simultaneously
     *                           in the critical section. Should be greater
     *                           than 0.
     */
    public CountingSemaphore(int maximumThreadCount) {
        countSemaphore = new BinarySemaphore(1);
        valueSemaphore = new BinarySemaphore(0);

        this.currentThreadCount = maximumThreadCount;
    }

    public synchronized void P() {
        countSemaphore.P();
        currentThreadCount--;
        if (currentThreadCount < 0) {
            countSemaphore.V();
            valueSemaphore.P();
        }
        countSemaphore.V();
    }

    public synchronized void V() {
        countSemaphore.P();
        currentThreadCount++;
        if (currentThreadCount <= 0)
            valueSemaphore.V();
        countSemaphore.V();
    }
}
