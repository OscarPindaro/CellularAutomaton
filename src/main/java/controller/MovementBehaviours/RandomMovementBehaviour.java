package controller.MovementBehaviours;

import model.entity.Entity;
import processing.core.PVector;

import java.util.Random;

/**
 * Implements a random movement behaviour for the entities that are requesting it
 */
public class RandomMovementBehaviour extends MovementBehaviour {

    private static final double CHANGE_CHANCE = 0.1;
    private static final float MAX_SPEED = 3;
    private static final float MIN_SPEED = 0.5f;

    public void updateAllEntitiesSpeed() {
        for(Entity e : entityList){
            PVector oldSpeed = e.getSpeed();
            PVector newSpeed = calculateSpeed(oldSpeed);
            e.setSpeed(newSpeed);
        }
    }

    private PVector calculateSpeed(PVector oldSpeed) {
        if (isRandomThresholdMet()){
            return createRandomSpeed();
        }
        else{
            return oldSpeed;
        }
    }

    private PVector createRandomSpeed(){
        PVector newVector = PVector.random2D();
        Random random = new Random();
        newVector.mult((float) ((Math.random()*(MAX_SPEED-MIN_SPEED)) + MIN_SPEED));
        return newVector;
    }

    private boolean isRandomThresholdMet(){
        return Math.random() < CHANGE_CHANCE;
    }

}
