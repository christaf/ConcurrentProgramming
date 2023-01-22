package zad1;

public class zad1b {
    static class CancellableTaskUsingInterruption extends Thread {
        public void run() {
            for (int i = 0; i < 100; i++) {
                if (isInterrupted()) {
                    break;
                }
                System.out.println("Execution of " + i + "th iteration");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //przerwanie
                    interrupt();
                    System.out.println("Thread is actually " + e.getMessage());
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create and run our task
        final CancellableTaskUsingInterruption taskThread = new CancellableTaskUsingInterruption();
        taskThread.start();

        // Creating 2nd thread, which will cancel our task after 5 seconds
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                taskThread.interrupt();
            }
        }).start();

    }

}
