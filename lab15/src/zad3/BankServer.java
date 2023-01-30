package zad3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BankServer {
    private static final int PORT = 1234;
    private static Account[] accounts = new Account[100];

    public static void main(String[] args) {
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(i, 100);
        }

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Bank server started on port " + PORT);

            while (true) {
                try (Socket socket = server.accept()) {
                    System.out.println("Client connected");

                    Scanner in = new Scanner(socket.getInputStream());
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    int accountId = in.nextInt();
                    Account account = accounts[accountId];
                    System.out.println("Welcome to account nr " + accountId);

                    while (true) {
                        synchronized (account) {
                            String operation = in.next();
                            System.out.println(operation);
                            if (operation.equals("logout")) {
                                System.out.println("Client logged out");
                              //  System.out.println(in.next());
                                break;
                            } else if (operation.equals("balance")) {
                                out.println(account.getBalance());
                            } else {
                                int amount = in.nextInt();
                                if (operation.equals("deposit")) {
                                    account.deposit(amount);
                                } else if (operation.equals("withdraw")) {
                                    account.withdraw(amount);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }
}

