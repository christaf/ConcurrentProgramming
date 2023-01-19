import java.util.concurrent.Exchanger;

public class ExampleExchanger {
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

        }
    }


    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 10;

        ParametrizedThread[] parametrizedThreads = new ParametrizedThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            parametrizedThreads[i] = new ParametrizedThread(i);
        }

        for (ParametrizedThread parametrizedThread : parametrizedThreads) {
            parametrizedThread.start();
        }

        System.out.println("Wszystkie watki zakończone");
    }
}
