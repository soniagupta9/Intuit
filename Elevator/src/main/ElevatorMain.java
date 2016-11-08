package main;

import controllers.MainController;
import strategies.elevators.Linear;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sonia
 */
public class ElevatorMain {

    public static int FLOORS = 5;
    public static int ELEVATORS = 1;
    public static int PEOPLE_PER_ELEVATOR = 5;
    public static int TOTAL_PEOPLE = 3;

    public static void main(String[] args) {
        try {
            MainController mainController = MainController.getInstance();
            mainController.startSimulation(FLOORS, ELEVATORS, PEOPLE_PER_ELEVATOR, TOTAL_PEOPLE, new Linear());
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}