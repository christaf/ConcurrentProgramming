package zad2.elevator.active_pattern;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ElevatorActiveObject elevator = new ElevatorActiveObject(5);
        Thread elevatorThread = new Thread(elevator::run);
        elevatorThread.start();

        List<Thread> userThreads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int targetFloor = (int) (Math.random() * 5 + 1);
            User user = new User(elevator, targetFloor);
            user.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        elevatorThread.interrupt();
    }
}
