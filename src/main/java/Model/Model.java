package Model;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Model {
    private final List<Entity> entities = new LinkedList<>();
    private final int worldWidth;
    private final int worldHeight;

    public Model(int width, int height){
        worldWidth = width;
        worldHeight = height;
    }

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public void addEntities(List<Entity> entities){
        this.entities.addAll(entities);
    }

    public void removeEntity(Entity entity){
        if (!entities.contains(entity)) throw new NoSuchElementException("This entity was never added to the controller");
        entities.remove(entity);
    }

    public List<Entity> getEntities(){
        return new LinkedList<>(entities);
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
}
