package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Model {
    private final List<Entity> entities = new LinkedList<>();
    private final List<Predator> predators = new LinkedList<>();
    private final List<Prey> preys = new LinkedList<>();
    private final int worldWidth;
    private final int worldHeight;

    public Model(int width, int height){
        worldWidth = width;
        worldHeight = height;
    }

    public void addEntity(Entity entity){
        if(entities.contains(entity)) throw new RuntimeException("The entity was already in the model");
        entities.add(entity);
    }

    public void addEntities(List<Entity> entities){
        for(Entity e : entities)
            addEntity(e);
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
            entities.add(prey);
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
            entities.add(predator);
        }
    }

    public void removeEntity(Entity entity){
        if (!entities.contains(entity)) throw new NoSuchElementException("This entity was never added to the controller");

        entities.remove(entity);
    }



    public List<Entity> getEntities(){
        return new LinkedList<>(entities);
    }

    public List<Prey> getPreys(){
        return new ArrayList<Prey>(preys);
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
}
