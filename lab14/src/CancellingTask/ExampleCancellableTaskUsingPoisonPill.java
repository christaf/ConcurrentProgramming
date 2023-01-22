package CancellingTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ExampleCancellableTaskUsingPoisonPill {
    static class PoisonPill {
        // I IS A PILL
    }
    static class CancellableTaskUsingPoisonPill extends Thread {
        private final BlockingQueue<Object> queue;

        public CancellableTaskUsingPoisonPill(BlockingQueue<Object> queue) {
            this.queue = queue;
        }

        public void run() {
            // Loop to execute the task
            while (true) {
                try {
                    System.out.println("Performing step of the task");
                    if (!queue.isEmpty()) {
                        Object obj = queue.take();
                        if (obj instanceof PoisonPill) {
                            System.out.println("FOUND PILL");
                            break;
                        }
                    }

                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        final BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
        CancellableTaskUsingPoisonPill cancellableTaskUsingPoisonPill = new CancellableTaskUsingPoisonPill(queue);
        cancellableTaskUsingPoisonPill.start();
//         Inserting the poison pill after 5 seconds
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    queue.offer(new PoisonPill());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
