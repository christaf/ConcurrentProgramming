package zad2.elevator.active_pattern;

public class User {
    private final ElevatorActiveObject elevator;
    private final int targetFloor;

    public User(ElevatorActiveObject elevator, int targetFloor) {
        this.elevator = elevator;
        this.targetFloor = targetFloor;
    }

    public void start() {
        System.out.println("User requests floor: " + targetFloor);
        elevator.requestFloor(targetFloor);
    }
}
