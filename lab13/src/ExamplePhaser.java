import java.util.concurrent.Phaser;

public class ExamplePhaser {
    static class MainThread extends Thread {

        public int numberOfPhases;

        public MainThread(int numberOfPhases) {
            this.numberOfPhases = numberOfPhases;
        }

        public void run() {
            for (int i = 0; i < numberOfPhases; i++) {
                System.out.println("Main thread at phase: " + i);
                phaser.arriveAndAwaitAdvance();
            }
            phaser.arriveAndDeregister();
        }

    }

    static class ParametrizedThread extends Thread {
        private final String name;
        private final long sleepTime;

        public ParametrizedThread(int id) {
            this.name = "Thread nr " + (id + 1);
            this.sleepTime = 500L * id;
        }

        public void run() {
            System.out.println(this.name + " starts execution");
            try {
                Thread.sleep(sleepTime);
                phaser.arriveAndAwaitAdvance();
                System.out.println(this.name + " at phase: " + phaser.getPhase());
                Thread.sleep(3000);
                phaser.arriveAndAwaitAdvance();
                System.out.println(this.name + " at phase: " + phaser.getPhase());
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " finishes execution");
        }
    }

    static Phaser phaser;

    public static void main(String[] args) {
        int numberOfThreads = 10;
        int numberOfPhases = 2;
        phaser = new Phaser(1);
        phaser.bulkRegister(numberOfThreads);
        MainThread mainThread = new MainThread(numberOfPhases);
        ParametrizedThread[] parametrizedThreads = new ParametrizedThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            parametrizedThreads[i] = new ParametrizedThread(i);
        }

        mainThread.start();
        for (ParametrizedThread parametrizedThread : parametrizedThreads) {
            parametrizedThread.start();
        }


        try {
            mainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (ParametrizedThread parametrizedThread : parametrizedThreads) {
            try {
                parametrizedThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
