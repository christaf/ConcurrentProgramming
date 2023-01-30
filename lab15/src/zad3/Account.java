package zad3;

class Account {
    private final int id;
    private volatile int balance;

    Account(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    void deposit(int amount) {
        balance += amount;
    }

    void withdraw(int amount) {
        balance -= amount;
    }

    int getBalance() {
        return balance;
    }
}
