package zad2.elevator.synchronized_method;

public class User implements Runnable {
    private final Elevator elevator;
    private final int targetFloor;

    public User(Elevator elevator, int targetFloor) {
        this.elevator = elevator;
        this.targetFloor = targetFloor;
    }

    @Override
    public void run() {
        System.out.println("User requests floor: " + targetFloor);
        elevator.requestFloor(targetFloor);
    }
}