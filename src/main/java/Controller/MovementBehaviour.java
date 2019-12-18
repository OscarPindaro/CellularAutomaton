package Controller;

import Model.Entity;
import Model.Predator;
import Model.Prey;

import java.util.LinkedList;
import java.util.List;

public abstract class MovementBehaviour {

    protected final List<Entity> entityList = new LinkedList<>();

    public abstract void updateAllEntitiesSpeed();

    public void addEntity(Entity e){
        if(entityList.contains(e)) throw new RuntimeException("Entity already present");

        entityList.add(e);
    }

    public void removeEntity(Entity e){
        entityList.remove(e);
    }

    public void addPrey(Prey prey){
        addEntity(prey);
    }

    public void addPredator(Predator predator){
        addEntity(predator);
    }

}
