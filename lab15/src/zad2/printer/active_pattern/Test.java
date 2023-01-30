package zad2.printer.active_pattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class PrintRequest {
    private final String document;

    PrintRequest(String document) {
        this.document = document;
    }

    String getDocument() {
        return document;
    }
}

class ServiceActivator {
    private final BlockingQueue<PrintRequest> requests = new LinkedBlockingQueue<>();

    void addPrintRequest(PrintRequest request) {
        requests.add(request);
    }

    void startService() {
        new Thread(() -> {
            while (true) {
                try {
                    PrintRequest request = requests.take();
                    System.out.println("Printing document: " + request.getDocument());
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        }).start();
    }
}

class Printer {
    private final ServiceActivator activator = new ServiceActivator();

    Printer() {
        activator.startService();
    }

    void print(String document) {
        activator.addPrintRequest(new PrintRequest(document));
    }
}

public class Test {
    public static void main(String[] args) {
        Printer printer = new Printer();
        String document1 = "Document 1";
        String document2 = "Document 2";
        String document3 = "Document 3";

        printer.print(document1);
        printer.print(document2);
        printer.print(document3);

    }
}
