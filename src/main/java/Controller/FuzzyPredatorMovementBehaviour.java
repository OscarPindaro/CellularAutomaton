package Controller;

import Model.Entity;
import Model.Model;
import Model.Predator;
import Model.Prey;
import jfml.FuzzyInferenceSystem;
import jfml.compatibility.ImportMatlab;
import processing.core.PVector;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FuzzyPredatorMovementBehaviour extends MovementBehaviour {
    private static final String PREY_DISTANCE="cellDistance";
    private static final String PREY_THETA="cellOrientation";
    private static final String LINEAR_VEL = "LINEAR_VEL";
    private static final String ANGULAR_VEL = "ANGULAR_VEL";

    private static final int MAX_WORLD_DISTANCE = 708;

    private final Model model;
    private HashMap<Predator, Prey> targets = new HashMap<Predator, Prey>();
    private final FuzzyInferenceSystem fuzzySystem;

    public FuzzyPredatorMovementBehaviour(Model m, String path) throws IOException {
        ImportMatlab fis= new ImportMatlab();
        fuzzySystem = fis.importFuzzySystem(path);
        model = m;
    }


    @Override
    public void removeEntity(Entity e) {
        targets.remove(e);
    }

    @Override
    public void addPredator(Predator predator) {
        targets.put(predator, null);
    }

    @Override
    public void addPrey(Prey prey) {
        throw new RuntimeException("Can't add preys in this behaviour");
    }

    @Override
    public void updateAllEntitiesSpeed() {

        calculateTargets();

        for(Predator predator: targets.keySet()){
            updateFuzzySystem(predator, targets.get(predator));

            float linearSpeed = calculateLinearSpeed(predator);
            float w = calculateAngularSpeed(predator);
            predator.setSpeedMagnitude(linearSpeed);
            predator.rotateSpeed(w);
        }

    }

    private void calculateTargets(){
        List<Prey> preys = model.getPreys();
        for(Predator predator : targets.keySet()){
            Prey target= getNearestPrey(predator, preys);
            targets.put(predator, target);
        }
    }

    private Prey getNearestPrey(Predator predator, List<Prey> preys){
        float minDistance = -1;
        float distance = 0;
        Prey minPrey = null;
        for(Prey prey : preys){
            PVector predatorPosition = predator.getPosition();
            PVector preyPosition = prey.getPosition();
            distance =PVector.dist(predatorPosition, preyPosition);
            if (minDistance == -1 || distance < minDistance){
                minDistance = distance;
                minPrey = prey;
            }
        }
        return minPrey;
    }

    private void updateFuzzySystem(Predator predator, Prey target){
        PVector differenceVector = target.getPosition().sub(predator.getPosition());
        fuzzySystem.setVariableValue(PREY_DISTANCE, differenceVector.mag()/MAX_WORLD_DISTANCE);
        float differenceVectorHeading = differenceVector.heading();
        float speedHeading = predator.getSpeed().heading();
        float deltaAngle = (differenceVectorHeading - speedHeading)*180/3.14f;
        if(deltaAngle < -180)
            deltaAngle = 360 +deltaAngle;
        if(deltaAngle > 180)
            deltaAngle = deltaAngle - 360;
        fuzzySystem.setVariableValue(PREY_THETA, deltaAngle);
        fuzzySystem.evaluate();
    }

    private float calculateLinearSpeed(Predator predator){
        float speedPercentage = fuzzySystem.getVariable(LINEAR_VEL).getValue();
        return speedPercentage*predator.getMAX_LINEAR_VEL();
    }

    private float calculateAngularSpeed(Predator predator){
        float angularPercentage = fuzzySystem.getVariable(ANGULAR_VEL).getValue();
        //System.out.println(angularPercentage);
        return angularPercentage*predator.getMAX_ANGULAR_VEL();
    }


}
