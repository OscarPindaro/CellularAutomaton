package model;

import model.entity.Entity;
import model.entity.Predator;
import model.entity.Prey;
import view.Automata;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Model {
    private final List<Predator> predators = new LinkedList<>();
    private final List<Prey> preys = new LinkedList<>();
    private final int worldWidth;
    private final int worldHeight;

    public Model(int width, int height){
        worldWidth = width;
        worldHeight = height;
    }

    public void addPreys(List<Prey> preys){
        for(Prey prey : preys){
            addPrey(prey);
        }
    }

    public void addPrey(Prey prey){
        if ( this.preys.contains(prey)) throw new RuntimeException("The prey was already in the model");
        else{
            this.preys.add(prey);
        }
    }

    public void addPredators(List<Predator> predators){
        for(Predator predator : predators){
            addPredator(predator);
        }
    }

    public void addPredator(Predator predator){
        if ( predators.contains(predator)) throw new RuntimeException("The predator was already in the model");
        else{
            predators.add(predator);
        }
    }

    public void removePredator(Predator p){
        predators.remove(p);
    }

    public void removePrey(Prey p){
        preys.remove(p);
    }

    public List<Entity> getEntities(){
        List<Entity> entities = new LinkedList<Entity>();
        entities.addAll(preys);
        entities.addAll(predators);
        return entities;
    }

    public List<Prey> getPreys(){
        return new ArrayList<Prey>(preys);
    }

    public List<Predator> getPredators(){
        return new ArrayList<>(predators);
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public void resetExistingEntities(){
        for(Entity e : getEntities()){
            PVector randomPosition = Model.createRandomPositionPVector();
            e.setXPosition(randomPosition.x);
            e.setYPosition(randomPosition.y);
            PVector randomSpeed = Model.createRandomSpeedPVector();
            e.setSpeed(randomSpeed);
        }
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
