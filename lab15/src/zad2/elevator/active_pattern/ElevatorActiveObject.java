package zad2.elevator.active_pattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ElevatorActiveObject {
    private int currentFloor = 1;
    private final int NUM_FLOORS;
    private final BlockingQueue<Integer> requests = new LinkedBlockingQueue<>();

    public ElevatorActiveObject(int numFloors) {
        this.NUM_FLOORS = numFloors;
    }

    public void requestFloor(int floor) {
        requests.add(floor);
    }

    public void run() {
        while (true) {
            int targetFloor;
            try {
                targetFloor = requests.take();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("The end");
                return;
            }
            System.out.println("Moving to floor: " + targetFloor);
            currentFloor = targetFloor;
        }
    }
}

