package CancellingTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExampleCancellableTaskUsingFuture {
    static class CancellableTaskUsingFuture extends Thread {
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println("Execution of " + i + "th iteration");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    interrupt();
                    System.out.println("Thread is actually " + e.getMessage());
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Creating an Executor Service
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Submitting the task and retrieving the future object
        Future<?> future = executor.submit(new CancellableTaskUsingFuture());
        Thread.sleep(5000);
        // Cancelling the task after 5 seconds
        future.cancel(true);
        executor.shutdown();

    }

}
