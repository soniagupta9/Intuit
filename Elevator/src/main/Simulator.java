package main;

import controllers.MainController;
import models.MovingObject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sonia
 */
public class Simulator implements Runnable {

    private ArrayList<MovingObject> listOfMovingObjects;
    private boolean inPause = false;

    public Simulator() {
        listOfMovingObjects = new ArrayList<>();
        Thread t = new Thread(this);
        t.start();
    }

    public void addMovingObject(MovingObject movingObject) {
        listOfMovingObjects.add(movingObject);
    }

    @Override
    public void run() {
        while (!inPause) {
            for (MovingObject movingObject : listOfMovingObjects) {
                movingObject.update();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (MainController.getInstance().getBuilding().allPersonsAreArrived()) {
                inPause = true;
            }
        }
    }
}
