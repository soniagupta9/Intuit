package models;

/**
 * Created by sonia
 */
public class MovingElevator extends MovingObject {

    private static final int BETWEEN_2_FLOORS_DURATION = 2;
    private Elevator elevator;
    private int elevatorStep;

    public MovingElevator(Elevator elevator) {
        this.elevator = elevator;
        elevatorStep = BETWEEN_2_FLOORS_DURATION;
    }

    public void update() {
        if (elevatorStep == BETWEEN_2_FLOORS_DURATION) {
            if (elevator.isMoving()) {
                elevator.setToNextFloor();
            }
            elevator.acts();
            if (elevator.isMoving()) {
                elevatorStep = 0;
            }

        }
        if (elevator.isMoving() && (elevatorStep != BETWEEN_2_FLOORS_DURATION)) {
            elevatorStep++;
        }
    }
}
