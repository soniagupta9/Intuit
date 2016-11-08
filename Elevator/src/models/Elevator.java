package models;

import controllers.MainController;
import strategies.ElevatorStrategy;

import java.util.LinkedList;

/**
 * Created by sonia
 */
public class Elevator {

    private static final int TO_TOP = 1;
    private static final int TO_BOTTOM = -1;

    private int currentFloor;
    private boolean goingUp;
    private int maxPersons;
    private int identifier;
    private LinkedList<Person> persons;
    private boolean moving;

    private MainController controller;
    private Building building;
    private ElevatorStrategy strategy;

    public Elevator(int max_persons, ElevatorStrategy strategy) {
        this.maxPersons = max_persons;
        constructor(strategy);
    }

    private void constructor(ElevatorStrategy strategy) {
        this.controller = MainController.getInstance();
        this.building = controller.getBuilding();
        this.strategy = strategy;
        this.strategy.setElevator(this);
        this.currentFloor = 1;
        this.goingUp = true;
        this.persons = new LinkedList<>();
        this.moving = false;
    }

    public void acts() {
        strategy.performAction();
    }

    public boolean takePerson(Person person) {
        if (isMaxNumberOfPersonsReached()) {
            return false;
        } else {
            persons.add(person);
            person.setElevator(this);
            strategy.takePerson(person);
            System.out.println("Person entered elevator " + this.getIdentifier() + " at floor " + currentFloor +
                    " going to floor " + person.getWantedFloor());
            return true;
        }
    }

    private void releasePerson(Person person) {
        if (persons.contains(person)) {
            persons.remove(person);
            person.setElevator(null);
            strategy.releasePerson(person);
        }
    }

    public void releaseAllArrivedPersons() {
        for (int i = 0; i < getPersonCount(); i++) {
            if (persons.get(i).getWantedFloor() == getCurrentFloor()) {
                releasePerson(persons.get(i));
            }
        }
    }

    private boolean isMaxNumberOfPersonsReached() {
        return persons.size() == maxPersons;
    }

    public boolean atTop() {
        return currentFloor >= controller.getBuilding().getFloorCount();
    }

    public boolean atBottom() {
        return currentFloor <= 0;
    }

    /**
     * Return the step (-1 or +1, for going to bottom or to top)
     *
     * @return -1 or +1
     */
    private int getStep() {
        return goingUp ? TO_TOP : TO_BOTTOM;
    }

    public boolean noCallOnTheWay() {
        if (goingUp) {
            for (int i = currentFloor; i <= building.getFloorCount(); i++) {
                if (building.getWaitingPersonsCountAtFloor(i) > 0) {
                    return false;
                }
                for (Person p : persons) {
                    if (p.getWantedFloor() == i) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            for (int i = currentFloor; i >= 1; i--) {
                if (building.getWaitingPersonsCountAtFloor(i) > 0) {
                    return false;
                }
                for (Person p : persons) {
                    if (p.getWantedFloor() == i) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public boolean isFull() {
        return getPersonCount() >= maxPersons;
    }

    public int getPersonCount() {
        return persons.size();
    }

    public LinkedList<Person> getPersons() {
        return persons;
    }

    public void changeDirection() {
        goingUp = !goingUp;
    }

    public Person getLastPerson() {
        return persons.get(persons.size() - 1);
    }

    public Building getBuilding() {
        return building;
    }

    public void setToNextFloor() {
        currentFloor += getStep();
        System.out.println("Elevator is now at floor:" + currentFloor);
        for (Person p : persons) {
            p.setCurrentFloor(currentFloor);
        }
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean move) {
        moving = move;
    }

    public int getPersonIndex(Person person) {
        return persons.indexOf(person);
    }

    public void leaveThisFloor() {
        // The elevator moves
        setMoving(true);
        strategy.leaveThisFloor();
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public boolean isGoingUp() {
        return goingUp;
    }

}
