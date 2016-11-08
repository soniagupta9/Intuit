package controllers;

import factories.SimulatorFactory;
import main.Simulator;
import models.*;
import strategies.ElevatorStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * MainController is designed to be a singleton to give a single instance
 * to the program.
 * <p>
 * Created by sonia
 */
public class MainController {

    private static Building building = null;

    private MainController() {
    }

    public static MainController getInstance() {
        return SingletonHelper.innerInstance;
    }

    public Building getBuilding() {
        return building;
    }

    public void startSimulation(int floorCount, int elevatorCount, int personPerElevator, int personCount, ElevatorStrategy elevatorStrategy) throws InstantiationException, IllegalAccessException {
        System.out.println("Initializing building with " + floorCount + " floors, " + elevatorCount +
                " elevators, " + personPerElevator + " max people per elevator, " + personCount + " people and ");

        SimulatorFactory sf = new SimulatorFactory();

        // Constructs the buildings.
        building = sf.getBuilding(floorCount);

        // Constructs the elevators
        ArrayList<Elevator> elevators = new ArrayList<>(elevatorCount);
        Elevator elevator;
        for (int i = 1; i <= elevatorCount; i++) {
            // set elevator strategy
            elevator = sf.getElevator(elevatorStrategy.getClass().newInstance(), personPerElevator);
            elevator.setIdentifier(i);
            //Place the elevator randomly at any floor
            Random rand = new Random();
            elevator.setCurrentFloor(rand.nextInt(floorCount - 1) + 1);
            elevators.add(elevator);
        }
        // Add elevators to the building
        building.setElevators(elevators);

        // Constructs the persons
        LinkedList<Person> persons = new LinkedList<>();
        for (int j = 0; j < personCount; j++) {
            persons.add(sf.getPerson(building.getFloorCount()));
        }
        // Add persons to the building
        building.setPersons(persons);

        // Initialize the simulator
        Simulator simulator = new Simulator();

        for (Elevator elevator1: elevators) {
            simulator.addMovingObject(new MovingElevator(elevator1));
        }
        for (Person person: persons) {
            simulator.addMovingObject(new MovingPerson(person));
        }
    }

    private static class SingletonHelper {
        private static final MainController innerInstance = new MainController();
    }

}