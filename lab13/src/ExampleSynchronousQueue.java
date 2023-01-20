import java.util.concurrent.SynchronousQueue;

public class ExampleSynchronousQueue {
    static class ParametrizedThread extends Thread {
        private final SynchronousQueue<String> queue;
        private final String message;
        public ParametrizedThread(SynchronousQueue<String> queue, String message) {
            this.queue = queue;
            this.message = message;
        }

        public void run() {
            try {
                if (this.message != null) {
                    queue.put(this.message);
                    System.out.println(Thread.currentThread().getName() + ": Message added to queue: " + this.message);
                } else {
                    String message = queue.take();
                    System.out.println(Thread.currentThread().getName() + ": Message received from queue: " + message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Create a new SynchronousQueue
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        // Thread 1 will put a message in the queue
        new ParametrizedThread(queue, "Hello World!").start();

        // Thread 2 will take the message from the queue
        new ParametrizedThread(queue, null).start();

    }
}
