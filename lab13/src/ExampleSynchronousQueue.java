//import java.util.concurrent.SynchronousQueue;
//
//public class ExampleSynchronousQueue {
//    static class ParametrizedThread extends Thread {
//        private final SynchronousQueue<String> queue;
//        private final String message;
//        public ParametrizedThread(SynchronousQueue<String> queue, String message) {
//            this.queue = queue;
//            this.message = message;
//        }
//
//        public void run() {
//            try {
//                if (this.message != null) {
//                    queue.put(this.message);
//                    System.out.println(Thread.currentThread().getName() + ": Message added to queue: " + this.message);
//                } else {
//                    String message = queue.take();
//                    System.out.println(Thread.currentThread().getName() + ": Message received from queue: " + message);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        // Create a new SynchronousQueue
//        SynchronousQueue<String> queue = new SynchronousQueue<>();
//
//        // Thread 1 will put a message in the queue
//        new ParametrizedThread(queue, "Hello World!").start();
//
//        // Thread 2 will take the message from the queue
//        new ParametrizedThread(queue, null).start();
//
//    }
//}

import java.util.concurrent.SynchronousQueue;

public class ExampleSynchronousQueue {

    public static void main(String[] args) {
        // Create a SynchronousQueue with a capacity of 1
        final SynchronousQueue<String> queue = new SynchronousQueue<>();

        // Start a new thread that will take objects from the queue
        ConsumerThread consumerThread = new ConsumerThread(queue);
        consumerThread.start();

        // Start a new thread that will put objects in the queue
        ProducerThread producerThread = new ProducerThread(queue);
        producerThread.start();
    }
}

class ConsumerThread extends Thread {

    private final SynchronousQueue<String> queue;

    public ConsumerThread(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Take an object from the queue
                String obj = queue.take();
                // Print the object
                System.out.println("Consumer: " + obj);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ProducerThread extends Thread {

    private final SynchronousQueue<String> queue;

    public ProducerThread(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                // Put an object in the queue
                queue.put("Object " + i);
                // Print a message
                System.out.println("Producer: Object " + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
