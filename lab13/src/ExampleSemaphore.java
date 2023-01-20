import java.util.concurrent.Semaphore;

public class ExampleSemaphore {

    static class ParametrizedThread extends Thread {
        private final String name;
        private final long sleepTime;
        public Semaphore semaphore;

        public ParametrizedThread(int id, Semaphore semaphore) {
            this.name = "Thread nr " + (id + 1);
            this.sleepTime = 2000L * id;
            this.semaphore = semaphore;
        }

        public void run() {
            System.out.println(this.name + " starts execution");
            try {
                Thread.sleep(sleepTime);
                semaphore.acquire();
                System.out.println(this.name + " is working");
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
            System.out.println(this.name + " finished execution");
        }
    }
    public static void main(String[] args) {
        int numberOfThreads = 3;
        Semaphore semaphore = new Semaphore(3);
        ParametrizedThread[] parametrizedThreads = new ParametrizedThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            parametrizedThreads[i] = new ParametrizedThread(i, semaphore);
        }
        for (ParametrizedThread parametrizedThread : parametrizedThreads) {
            parametrizedThread.start();
        }
    }
}