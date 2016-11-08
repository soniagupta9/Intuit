package strategies;

import models.Elevator;
import models.Person;

/**
 * Scope to add various elevator strategies.
 * Created by sonia
 */
public abstract class ElevatorStrategy {

    protected Elevator elevator;

    public ElevatorStrategy() {
    }

    public ElevatorStrategy(Elevator elevator) {
        this.elevator = elevator;
    }

    public abstract void performAction();

    public abstract boolean takePerson(Person person);

    public abstract void releasePerson(Person person);

    public abstract void leaveThisFloor();

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

}
