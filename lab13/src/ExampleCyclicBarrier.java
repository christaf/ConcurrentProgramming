import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ExampleCyclicBarrier {
    static class ParametrizedThread extends Thread {
        private final String name;
        private final long sleepTime;

        public ParametrizedThread(int id) {
            this.name = "Thread nr " + (id + 1);
            this.sleepTime = 1000L * id;
        }

        public void run() {
            System.out.println(this.name + " starts execution");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " finishes execution");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static CyclicBarrier barrier;

    public static void main(String[] args) {
        int barrierParameter = 3;
        int numberOfThreads = 5 * barrierParameter;
        barrier = new CyclicBarrier(barrierParameter, () -> System.out.println("THE END"));

        ParametrizedThread[] parametrizedThreads = new ParametrizedThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            parametrizedThreads[i] = new ParametrizedThread(i);
        }

        for (ParametrizedThread parametrizedThread : parametrizedThreads) {
            parametrizedThread.start();
        }
    }
}
