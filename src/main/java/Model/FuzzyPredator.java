package Model;

import View.Automata;
import jfml.FuzzyInferenceSystem;
import jfml.compatibility.ImportMatlab;
import processing.core.PVector;

import java.io.IOException;

public class FuzzyPredator extends Predator {

    private static final String PREY_DISTANCE="cellDistance";
    private static final String PREY_THETA="cellOrientation";
    private static final String LINEAR_VEL = "LINEAR_VEL";
    private static final String ANGULAR_VEL = "ANGULAR_VEL";

    private static final int MAX_WORLD_DISTANCE = 708;

    private final float MAX_LINEAR_VEL = 3;
    private final float MAX_ANGULAR_VEL = 0.08f;

    private Entity prey;

    private FuzzyInferenceSystem fuzzySystem;

    public FuzzyPredator(int posx, int posy, int speedx, int speedy) throws IOException {
        super(posx, posy, speedx, speedy);
        ImportMatlab fis= new ImportMatlab();
        fuzzySystem = fis.importFuzzySystem(getClass().getResource("fuzzyControllers/cellular.fis").getPath());

    }

    public FuzzyPredator(int posx, int posy, int speedx, int speedy,Entity prey) throws IOException {
        super(posx, posy, speedx, speedy);
        ImportMatlab fis= new ImportMatlab();
        fuzzySystem = fis.importFuzzySystem("/home/oscar/IdeaProjects/cellularAutomata/src/main/resources/fuzzyControllers/cellular.fis");
        this.prey = prey;
    }

    public FuzzyPredator(PVector position, PVector speed) {
        super(position, speed);
    }

    @Override
    public void move() {
        updateFuzzySystem();
        float linearSpeed = calculateLinearSpeed();
        float w = calculateAngularSpeed();
        this.setSpeedMangitude(linearSpeed);
        this.rotateSpeed(w);
        incrementPosition(getSpeed());

    }

    private void updateFuzzySystem(){
        PVector differenceVector = prey.getPosition().sub(this.getPosition());
        fuzzySystem.setVariableValue(PREY_DISTANCE, differenceVector.mag()/MAX_WORLD_DISTANCE);
        fuzzySystem.setVariableValue(PREY_THETA, PVector.angleBetween(getSpeed(), differenceVector)*180/ Automata.getInstance().PI);
        System.out.println(PVector.angleBetween(getSpeed(), differenceVector)*180/ 3.14);
        fuzzySystem.evaluate();
    }

    private float calculateLinearSpeed(){
        float speedPercentage = fuzzySystem.getVariable(LINEAR_VEL).getValue();
        return speedPercentage*MAX_LINEAR_VEL;
    }

    private float calculateAngularSpeed(){
        float angularPercentage = fuzzySystem.getVariable(ANGULAR_VEL).getValue();
        return angularPercentage*MAX_ANGULAR_VEL;
    }
}
