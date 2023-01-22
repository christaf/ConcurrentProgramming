package zad1;


public class zad1a {
    static class CancellableTaskUsingSharedVariable extends Thread {
        private boolean isCancelled = false;

        public void cancel() {
            isCancelled = true;
            System.out.println("Executed function cancel()");
        }

        public void run() {
            for (int i = 0; i < 100; i++) {
                if (isCancelled) {
                    System.out.println("variable isCancelled was set true");
                    break;
                }
                System.out.println("Execution of " + i + "th iteration");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create and run our task
        final CancellableTaskUsingSharedVariable cancellableTaskUsingSharedVariable = new CancellableTaskUsingSharedVariable();
        cancellableTaskUsingSharedVariable.start();

        // Creating 2nd thread, which will cancel our task after 5 seconds
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cancellableTaskUsingSharedVariable.cancel();
            }
        }).start();
    }

}
