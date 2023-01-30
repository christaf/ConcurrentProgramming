package zad2.elevator.synchronized_method;

import java.util.LinkedList;
import java.util.Queue;

public class Elevator {
    private int currentFloor = 1;
    private final int NUM_FLOORS;
    private int counter = 0;
    private final Queue<Integer> requests = new LinkedList<>();

    public Elevator(int numFloors) {
        this.NUM_FLOORS = numFloors;
    }

    public synchronized void requestFloor(int floor) {
        requests.offer(floor);
        notifyAll();
    }

    public synchronized void run() {
        while (counter < 5) {
            if (requests.isEmpty()) {
                try {
                    counter++;
                    wait(300);
                } catch (InterruptedException e) {
                    System.out.println("The end");
                }
            } else {
                int targetFloor = requests.poll();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Moving to floor: " + targetFloor);
                currentFloor = targetFloor;
            }
        }
    }
}



