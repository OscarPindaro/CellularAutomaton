package Controller.MovementBehaviours;

import Model.entity.Entity;
import Model.entity.Predator;
import Model.entity.Prey;

import java.util.LinkedList;
import java.util.List;

public abstract class MovementBehaviour {

    protected final List<Entity> entityList = new LinkedList<>();

    public abstract void updateAllEntitiesSpeed();

    private void addEntity(Entity e){
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
