package zad2.elevator.synchronized_method;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Elevator elevator = new Elevator(5);
        Thread elevatorThread = new Thread(elevator::run);
        elevatorThread.start();

        List<Thread> userThreads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int targetFloor = (int) (Math.random() * 5 + 1);
            User user = new User(elevator, targetFloor);
            Thread userThread = new Thread(user);
            userThreads.add(userThread);
            userThread.start();
            Thread.sleep(500);
        }

        for (Thread userThread : userThreads) {
            try {
                userThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
