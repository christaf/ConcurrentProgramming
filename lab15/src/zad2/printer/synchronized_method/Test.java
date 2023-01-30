package zad2.printer.synchronized_method;

public class Test {
    public static void main(String[] args) {
        Printer printer = new Printer();
        new Thread(printer::printDocuments).start();

        User user1 = new User(printer, "Document 1");
        User user2 = new User(printer, "Document 2");
        User user3 = new User(printer, "Document 3");

        user1.start();
        user2.start();
        user3.start();
    }
}