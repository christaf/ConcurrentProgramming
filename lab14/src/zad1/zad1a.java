package zad1;


public class zad1a {
    static class TaskThread extends Thread {
        private boolean isCancelled = false;

        public void cancel() {
            isCancelled = true;
            System.out.println("Executed function cancel()");
        }

        public void run() {
            // Pętla wykonująca zadanie
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
        // Utworzenie i uruchomienie wątku wykonującego zadanie
        final TaskThread taskThread = new TaskThread();
        taskThread.start();

        // Uruchomienie drugiego wątku, który anuluje zadanie po 5 sekundach
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                taskThread.cancel();
            }
        }).start();
    }

}
