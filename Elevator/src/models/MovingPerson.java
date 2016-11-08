package models;

import controllers.MainController;

/**
 * Created by sonia
 */
public class MovingPerson extends MovingObject {

    private Person person;

    public MovingPerson(Person person) {
        this.person = person;
    }

    @Override
    public void update() {
        if (person.isInTheElevator()) {
            System.out.println("Person " + person.getIdentifier() + " is in Elevator");
        } else {
            if (person.isArrived()) { // Is arrived
                System.out.println("Person " + person.getIdentifier() + " has arrived to its floor");
            } else { // Is waiting
                System.out.println("Person " + person.getIdentifier() + " is waiting for an elevator");
            }
        }
    }
}
