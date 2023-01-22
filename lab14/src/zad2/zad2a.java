package zad2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class zad2a {

    static class SharedData {
        private final AtomicInteger data = new AtomicInteger();

        public int getData() {
            return data.get();
        }

        public void setData(int value) {
            data.set(value);
        }
    }

    static class ReadingThread implements Runnable {
        private final SharedData sharedData;

        public ReadingThread(SharedData sharedData) {
            this.sharedData = sharedData;
        }

        public void run() {
            while (true) {
                int data = sharedData.getData();
                System.out.println("ReadingThread: reading value " + data);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ModifyingThread implements Runnable {
        private final SharedData sharedData;

        public ModifyingThread(SharedData sharedData) {
            this.sharedData = sharedData;
        }

        public void run() {
            Random random = new Random();
            while (true) {
                int value = random.nextInt(100);
                sharedData.setData(value);
                System.out.println("ModifyingThread: set value to " + value);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SharedData sharedData = new SharedData();
        Thread readingThread1 = new Thread(new ReadingThread(sharedData));
        Thread readingThread2 = new Thread(new ReadingThread(sharedData));
        Thread modifyingThread = new Thread(new ModifyingThread(sharedData));
        readingThread1.start();
        readingThread2.start();
        modifyingThread.start();
    }
}
