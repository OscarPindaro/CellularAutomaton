package Controller;

import Model.Entity;
import processing.core.PVector;

import java.util.LinkedList;
import java.util.List;

public abstract class MovementBehaviour {

    protected final List<Entity> entityList = new LinkedList<>();

    public abstract void updateAllEntitiesSpeed();

    public void addEntity(Entity e){
        entityList.add(e);
    }

    public void removeEntity(Entity e){
        entityList.remove(e);
    }

}
