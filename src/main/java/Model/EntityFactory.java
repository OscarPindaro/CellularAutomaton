package Model;

import Model.Entity;
import Model.Predator;
import Model.Prey;
import View.Automata;
import processing.core.PVector;

import java.util.LinkedList;
import java.util.List;

public class EntityFactory {


    public static List<Entity> createRandomPredators(int numberOfPredators){
        List<Entity> predators = new LinkedList<>();
        for(int i = 0; i < numberOfPredators; i++){
            PVector position = createRandomPositionPVector();
            PVector speed = createRandomSpeedPVector();
            predators.add(new Predator(position, speed));
        }
        return predators;
    }

    public static List<Entity> createPreys(int numberOfPreys){
        List<Entity> preys = new LinkedList<>();
        for(int i = 0; i < numberOfPreys; i++){
            PVector position = createRandomPositionPVector();
            PVector speed = createRandomSpeedPVector();
            preys.add(new Prey(position, speed));
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
