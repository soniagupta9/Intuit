package models;

import controllers.MainController;

import java.util.Random;

/**
 * Created by sonia
 */
public class Person {

    private MainController controller;

    private Elevator elevator;
    private int currentFloor;
    private int wantedFloor;
    private String identifier;

    public Person(String identifier, int currentFloor, int wantedFloor) {
        this.controller = MainController.getInstance();
        this.currentFloor = currentFloor % this.controller.getBuilding().getFloorCount();
        this.wantedFloor = wantedFloor % this.controller.getBuilding().getFloorCount();
        this.elevator = null;
        this.identifier = identifier;
    }

    public Elevator getElevator() {
        return elevator;
    }

    // Set the elevator in which the person is
    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }


    public boolean isArrived() {
        return (wantedFloor == currentFloor) && !isInTheElevator();
    }

    public boolean isInTheElevator() {
        return elevator != null;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floor) {
        currentFloor = floor;
    }

    public int getWantedFloor() {
        return wantedFloor;
    }

    public boolean isWaitingAtFloor(int floor) {
        return !isArrived() && !isInTheElevator() && currentFloor == floor;
    }

    public boolean isArrivedAtFloor(int floor) {
        return isArrived() && currentFloor == floor;
    }

    public int getPersonCount() {
        return 1;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean canEnterElevator(Elevator elevator) {
        if (elevator.takePerson(this)) {
            System.out.println("Person " + this.identifier + "going in Elevator " + elevator.getIdentifier() + ", going to floor "
                    + wantedFloor + "! Elevator at floor " + elevator.getCurrentFloor() + " |" + elevator.getPersonCount() + "|");
        } else {
            System.out.println("Elevator " + elevator.getIdentifier() + " full.");
        }
        return isInTheElevator();
    }
}