package Model;

import Controller.MovementBehaviours.MovementBehaviour;
import View.Automata;
import processing.core.PVector;

import java.util.LinkedList;
import java.util.List;

public class EntityFactory {

    public static List<Predator> createRandomPredators(int numberOfPredators, MovementBehaviour movementBehaviour){
        List<Predator> predators = new LinkedList<>();
        for(int i = 0; i < numberOfPredators; i++){
            PVector position = createRandomPositionPVector();
            PVector speed = createRandomSpeedPVector();
            Predator newPredator = new Predator(position, speed);
            predators.add(newPredator);
            movementBehaviour.addPredator(newPredator);
        }
        return predators;
    }

    public static List<Prey> createRandomPreys(int numberOfPreys, MovementBehaviour movementBehaviour){
        List<Prey> preys = new LinkedList<>();
        for(int i = 0; i < numberOfPreys; i++){
            PVector position = createRandomPositionPVector();
            PVector speed = createRandomSpeedPVector();
            Prey newPrey = new Prey(position, speed);
            preys.add(newPrey);
            movementBehaviour.addPrey(newPrey);
        }
        return preys;
    }

    private static PVector createRandomPositionPVector(){
        Automata mySketch = Automata.getInstance();
        return new PVector((float) Math.random()*mySketch.getWidth(), (float) Math.random()*mySketch.getHeight());
    }

    private static PVector createRandomSpeedPVector(){
        Automata mySketch = Automata.getInstance();
        PVector speed = PVector.random2D();
        speed.mult(mySketch.random(2));
        return speed;
    }
}
