package zad1;

public class zad1b {
    static class TaskThread extends Thread {
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
                taskThread.interrupt();
            }
        }).start();

    }

}
