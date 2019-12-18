package Model;

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
        return new LinkedList<>(preys);
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
}
