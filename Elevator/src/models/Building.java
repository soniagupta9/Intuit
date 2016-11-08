package models;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by sonia
 */
public class Building {
    private ArrayList<Elevator> elevators;
    private LinkedList<Person> persons;

    private int floorCount;

    public Building(int numberOfFloors) {
        constructor(numberOfFloors, new ArrayList<>(numberOfFloors), new LinkedList<>());
    }

    private void constructor(int numberOfFloors, ArrayList<Elevator> elevatorList, LinkedList<Person> PersonList) {
        this.floorCount = numberOfFloors;
        this.elevators = elevatorList;
        this.persons = PersonList;
    }

    public int getFloorCount() {
        return floorCount;
    }

    public LinkedList<Person> getPersons() {
        return persons;
    }

    public void setPersons(LinkedList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(ArrayList<Elevator> elevators) {
        this.elevators = elevators;
    }

    public boolean allPersonsAreArrived() {
        for (Person person : persons) {
            if (!person.isArrived()) {
                return false;
            }
        }
        return true;
    }


    private LinkedList<Person> getWaitingPersonsAtFloor(int floor) {
        LinkedList<Person> waiting = new LinkedList<>();
        for (Person p : persons) {
            if (p.isWaitingAtFloor(floor)) {
                waiting.add(p);
            }
        }
        return waiting;
    }

    public Person getWaitingPersonAtFloorWithIndex(int floor, int i) {
        LinkedList<Person> ps = getWaitingPersonsAtFloor(floor);
        if (i >= ps.size()) {
            return null;
        } else {
            return ps.get(i);
        }
    }

    public int getWaitingPersonsCountAtFloor(int floor) {
        int sum = 0;
        for (Person person : getWaitingPersonsAtFloor(floor)) {
            sum += person.getPersonCount();
        }
        return sum;
    }


    public LinkedList<Person> getPersonsAtFloor(Person person) {
        if (person.isArrived()) {
            return getArrivedPersonsAtFloor(person.getCurrentFloor());
        } else {
            return getWaitingPersonsAtFloor(person.getCurrentFloor());
        }
    }

    public int getWaitingPersonsCount() {
        int sum = 0;
        for (Person p : persons) {
            if (!p.isArrived() && !p.isInTheElevator()) {
                sum += p.getPersonCount();
            }
        }
        return sum;
    }

    public int getPersonIndexAtHisFloor(Person person) {
        LinkedList<Person> list = getPersonsAtFloor(person);
        return list.indexOf(person);
    }

    private LinkedList<Person> getArrivedPersonsAtFloor(int floor) {
        LinkedList<Person> arrived = new LinkedList<>();
        for (Person p : persons) {
            if (p.isArrivedAtFloor(floor)) {
                arrived.add(p);
            }
        }
        return arrived;
    }
}
