package zad2.printer.synchronized_method;

public class User extends Thread {
    private Printer printer;
    private String document;

    public User(Printer printer, String document) {
        this.printer = printer;
        this.document = document;
    }

    @Override
    public void run() {
        printer.print(document);
    }
}
