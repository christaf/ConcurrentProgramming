package zad2;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class zad2c {

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
        private final ReentrantReadWriteLock.ReadLock lock;

        public ReadingThread(SharedData sharedData, ReentrantReadWriteLock lock) {
            this.sharedData = sharedData;
            this.lock = lock.readLock();
        }

        public void run() {
            while (true) {
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
        private final ReentrantReadWriteLock.WriteLock lock;

        public ModifyingThread(SharedData sharedData, ReentrantReadWriteLock lock) {
            this.sharedData = sharedData;
            this.lock = lock.writeLock();
        }

        public void run() {
            while (true) {
                lock.lock();
                try {
                    int value = (int) (Math.random() * 100 + 1);
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
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        Thread readingThread = new Thread(new ReadingThread(sharedData, lock));
        Thread modifyingThread = new Thread(new ModifyingThread(sharedData, lock));

        readingThread.start();
        modifyingThread.start();
    }
}
