package zad2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static java.lang.Thread.sleep;

public class zad2b {


    static class SharedData {
        private int data;

        public int getData() {
            return data;
        }

        public void setData(int value) {
            data = value;
        }
    }

    static class ReadingThread implements Runnable {
        private final SharedData sharedData;
        private final Lock lock;

        public ReadingThread(SharedData sharedData, Lock lock) {
            this.sharedData = sharedData;
            this.lock = lock;
        }
        public void run() {
            for(int i = 0; i < 10; i++){
                lock.lock();
                try {
                    int data = sharedData.getData();
                    System.out.println("ReadingThread: reading value " + data);
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class ModifyingThread implements Runnable {
        private final SharedData sharedData;
        private final Lock lock;

        public ModifyingThread(SharedData sharedData, Lock lock) {
            this.sharedData = sharedData;
            this.lock = lock;
        }
        public void run() {
            for(int i = 0; i < 10; i++){
                lock.lock();
                try {
                    int value = (int)(Math.random() * 100 + 1);
                    sharedData.setData(value);
                    System.out.println("ModifyingThread: set value to " + value);
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

        public static void main(String[] args) {
            SharedData sharedData = new SharedData();
            Lock lockReader = new ReentrantLock();
            Lock lockWriter = new ReentrantLock();
            Thread readingThread1 = new Thread(new ReadingThread(sharedData, lockReader));
            Thread readingThread2 = new Thread(new ReadingThread(sharedData, lockReader));
            Thread modifyingThread = new Thread(new ModifyingThread(sharedData, lockWriter));
            readingThread1.start();
            readingThread2.start();
            modifyingThread.start();
        }
}
