package factories;

import models.Building;
import models.Elevator;
import models.Person;
import strategies.ElevatorStrategy;

import java.util.Random;

/**
 * Created by sonia
 */
public class SimulatorFactory {

    public Building getBuilding(int floorCount) {
        return new Building(floorCount);
    }

    public Person getPerson(int maxFloor) {
        int currentFloor, wantedFloor;
        Random rand = new Random();
        currentFloor = rand.nextInt(maxFloor - 1) + 1;
        do {
            wantedFloor = rand.nextInt(maxFloor - 1) + 1;
        } while (wantedFloor == currentFloor);
        byte[] nbyte = new byte[30];
        rand.nextBytes(nbyte);
        String identifier = nbyte.toString();
        return new Person(identifier, currentFloor, wantedFloor);
    }

    public Elevator getElevator(ElevatorStrategy elevatorStrategy, int maxPerson) {
        return new Elevator(maxPerson, elevatorStrategy);
    }

}