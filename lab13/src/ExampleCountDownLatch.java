import java.util.concurrent.CountDownLatch;

public class ExampleCountDownLatch {
    static class ParametrizedThread extends Thread {
        private final String name;
        private final long sleepTime;

        public ParametrizedThread(int id) {
            this.name = "Watek nr " + (id + 1);
            this.sleepTime = 1000L * id;
        }

        public void run() {
            System.out.println(this.name + " rozpoczyna działanie");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " kończy działanie");
            latch.countDown();
        }
    }

    static CountDownLatch latch;

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 10;
        latch = new CountDownLatch(numberOfThreads);

        ParametrizedThread[] parametrizedThreads = new ParametrizedThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            parametrizedThreads[i] = new ParametrizedThread(i);
        }

        for (ParametrizedThread parametrizedThread : parametrizedThreads) {
            parametrizedThread.start();
        }
        latch.await();
        System.out.println("Wszystkie watki zakończone");
    }
}
