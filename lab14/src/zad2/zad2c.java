package zad2;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class zad2c {

    static class SharedData {
        private int data;
        private final ReentrantReadWriteLock lock;

        SharedData(ReentrantReadWriteLock lock) {
            this.lock = lock;
        }


        public int getData() {
            //System.out.println("GET: before reading value");
            lock.readLock().lock();
           // System.out.println("GET: reading value " + data);
            try {
                sleep(1000);
                return data;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return -1;
            } finally {
                lock.readLock().unlock();
               // System.out.println("GET: after reading value");
            }

        }

        public void setData(int value) {
           // System.out.println("SET: before writing value");
            lock.writeLock().lock();
            //System.out.println("SET: writing value " + data);
            try {
                sleep(1000);
                this.data = value;
            } catch (InterruptedException e) {
                e.printStackTrace();
                this.data = -1;
            } finally {
                lock.writeLock().unlock();
               // System.out.println("SET: after writing value");
            }

        }
    }

    static class ReadingThread extends Thread {
        private final SharedData sharedData;

        public ReadingThread(SharedData sharedData) {
            this.sharedData = sharedData;
        }

        public void run() {
            while (true) {
                int data = sharedData.getData();
                System.out.println("ReadingThread: reading value " + data);
            }
        }
    }


    static class ModifyingThread extends Thread {
        private final SharedData sharedData;

        public ModifyingThread(SharedData sharedData) {
            this.sharedData = sharedData;
        }

        public void run() {
            while (true) {
                int value = (int) (Math.random() * 100 + 1);
                sharedData.setData(value);
                System.out.println("ModifyingThread: set value to " + value);
            }
        }
    }


    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        SharedData sharedData = new SharedData(lock);

        Thread modifyingThread = new Thread(new ModifyingThread(sharedData));
        Thread readingThread1 = new Thread(new ReadingThread(sharedData));
        Thread readingThread2 = new Thread(new ReadingThread(sharedData));

        modifyingThread.start();
        readingThread1.start();
        readingThread2.start();
    }
}
