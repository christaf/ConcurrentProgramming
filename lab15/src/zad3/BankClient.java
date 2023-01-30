package zad3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BankClient {
    private static final String HOST = "localhost";
    private static final int PORT = 1234;
    private int accountId;

    public BankClient(int accountId) {
        this.accountId = accountId;
    }

    public void start() {
        Scanner consoleIn = new Scanner(System.in);
        while (true) {
            try (Socket socket = new Socket(HOST, PORT)) {
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Connected to server");
                out.println(accountId);

                while (true) {
                    System.out.println("Enter the amount to deposit/withdraw or type 'logout' to switch to another account:");
                    String line = consoleIn.nextLine();
                    int amount;
                    int balance;
                    switch (line) {
                        case "logout":
                            out.println(line);
                            System.out.println("Successfully logged out, now you can log in to another account.");
                            System.out.println("Do you want to log into another account? (yes/no)");
                            String answer = consoleIn.nextLine();
                            if (!answer.equals("yes")) {
                                return;
                            }
                            break;
                        case "deposit":
                            out.println(line);
                            amount = consoleIn.nextInt();
                            out.println(amount);
                            break;
                        case "balance":
                            out.println(line);
                            balance = in.nextInt();
                            System.out.println("Balance: " + balance);
                            break;
                        default:
                            System.out.println("Wrong command");
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error connecting to server: " + e.getMessage());
            }


        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter account id:");
            Scanner consoleIn = new Scanner(System.in);
            int accountId = consoleIn.nextInt();
            BankClient client = new BankClient(accountId);
            client.start();

        }
    }
}