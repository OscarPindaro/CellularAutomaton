package model;

import model.entity.Entity;
import model.entity.Predator;
import model.entity.Prey;
import view.Automata;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Entity> getAliveEntities(){
        List<Entity> entities = preys.stream().filter(e -> !e.isDead()).collect(Collectors.toCollection(LinkedList::new));
        entities.addAll( predators.stream().filter(e -> !e.isDead()).collect(Collectors.toList()));
        return entities;
    }

    public List<Prey> getPreys(){
        return new ArrayList<Prey>(preys);
    }

    public List<Prey> getAlivePreys(){
        return  preys.stream().filter(e -> !e.isDead()).collect(Collectors.toList());
    }

    public List<Predator> getPredators(){
        return new ArrayList<>(predators);
    }

    public List<Predator> getAlivePredators(){
        return  predators.stream().filter(e -> !e.isDead()).collect(Collectors.toList());
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public void resetExistingEntities(){
        for(Entity e : getEntities()){
            resetEntity(e);
        }
    }

    public void resetEntities(List<Entity> entities){
        for(Entity e: entities)
            resetEntity(e);
    }

    /**
     * Resets all fields of an entity. Its speed and position are random, the energy is set back to a starting value
     * and is considered again alive
     * @param entity
     */
    public void resetEntity(Entity entity){
        PVector randomPosition = Model.createRandomPositionPVector();
        entity.setXPosition(randomPosition.x);
        entity.setYPosition(randomPosition.y);
        PVector randomSpeed = Model.createRandomSpeedPVector();
        entity.setSpeed(randomSpeed);
        entity.setEnergy(entity.getStartingEnergy());
        entity.setDeath(false);

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
