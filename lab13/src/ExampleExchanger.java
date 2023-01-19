import java.util.concurrent.Exchanger;

public class ExampleExchanger {

    static class MainThread extends Thread {

        public int numberOfPhases;
        public Exchanger<Integer> exchanger;

        public MainThread(int numberOfPhases, Exchanger<Integer> exchanger) {
            this.numberOfPhases = numberOfPhases;
            this.exchanger = exchanger;
        }

        public void run() {
            for (int i = 0; i < numberOfPhases; i++) {
                System.out.println("Main thread at phase: " + i);
                try {
                    exchanger.exchange(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ParametrizedThread extends Thread {
        private final String name;
        private final long sleepTime;
        public Exchanger<Integer> exchanger;

        public ParametrizedThread(int id, Exchanger<Integer> exchanger) {
            this.name = "Thread nr " + (id + 1);
            this.sleepTime = 500L * id;
            this.exchanger = exchanger;
        }

        public void run() {
            System.out.println(this.name + " starts execution");
            try {
                Thread.sleep(sleepTime);
                int phase = exchanger.exchange(0);
                System.out.println(this.name + " at phase: " + phase);
                Thread.sleep(500);
                phase = exchanger.exchange(0);
                System.out.println(this.name + " at phase: " + phase);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " finishes execution");
        }
    }

    public static void main(String[] args) {
        int numberOfThreads = 10;
        int numberOfPhases = 2;
        Exchanger<Integer> exchanger = new Exchanger<>();
        MainThread mainThread = new MainThread(numberOfPhases, exchanger);
        ParametrizedThread[] parametrizedThreads = new ParametrizedThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            parametrizedThreads[i] = new ParametrizedThread(i, exchanger);
        }

        mainThread.start();
        for (ParametrizedThread parametrizedThread : parametrizedThreads) {
            parametrizedThread.start();
        }

    }
}