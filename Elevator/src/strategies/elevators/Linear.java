package strategies.elevators;

import models.Elevator;
import models.Person;
import strategies.ElevatorStrategy;

/**
 * Created by sonia
 */
public class Linear extends ElevatorStrategy {

    public Linear() {
        super();
    }

    public Linear(Elevator elevator) {
        super(elevator);
    }

    @Override
    public void performAction() {
        // If all people have not arrived to their destination perform actions
        if (!elevator.getBuilding().allPersonsAreArrived() && (elevator.getBuilding().getWaitingPersonsCount() != 0
                || elevator.getPersonCount() != 0)) {

            elevator.setMoving(false);
            elevator.releaseAllArrivedPersons();
            if (!elevator.isFull()) {
                int i = 0;
                // If elevator is not full and you have people waiting for an elevator at current Floor
                while (!elevator.isFull() && i < elevator.getBuilding().getWaitingPersonsCountAtFloor(elevator.getCurrentFloor())) {
                    Person p = elevator.getBuilding().getWaitingPersonAtFloorWithIndex(elevator.getCurrentFloor(), i);
                    if (p != null) {
                        p.canEnterElevator(elevator);
                    }
                    i++;
                }
            }

            // change direction based on where the elevator is and if anyone has called for an elevator
            if ((elevator.isGoingUp() && elevator.atTop()) || (!elevator.isGoingUp() && elevator.atBottom()) || (elevator.noCallOnTheWay())) {
                elevator.changeDirection();
            }
            // leave the floor once all the actions are performed for current floor
            elevator.leaveThisFloor();
        } else {
            elevator.setMoving(false);
        }

    }

    @Override
    public boolean takePerson(Person person) {
        return true;
    }

    @Override
    public void releasePerson(Person person) {
        System.out.println("Person " + person.getIdentifier() + " left elevator at floor " + person.getCurrentFloor() + " wanted floor "
                + person.getWantedFloor());
    }

    @Override
    public void leaveThisFloor() {
    }

}
