package zad2.printer.synchronized_method;

import java.util.LinkedList;
import java.util.Queue;

public class Printer {
    private Queue<String> printQueue = new LinkedList<>();

    public synchronized void print(String document) {
        printQueue.add(document);
        System.out.println("Document added to print queue: " + document);
        notifyAll();
    }

    public synchronized void printDocuments() {
        while (true) {
            while (printQueue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String document = printQueue.poll();
            System.out.println("Printing document: " + document);
        }
    }
}
