package Controller;

import Controller.MovementBehaviours.MovementBehaviour;
import Model.Entity;
import Model.Model;

import java.util.LinkedList;
import java.util.List;

public class MovementController {

    private final Model model;

    private final List<MovementBehaviour> movementBehaviours = new LinkedList<>();

    public MovementController(Model model){
        this.model = model;
    }

    public void updateAllEntitiesSpeed(){
        for(MovementBehaviour m : movementBehaviours){
            m.updateAllEntitiesSpeed();
        }
    }

    public void updateAllEntitiesPosition(){
        List<Entity> entities = model.getEntities();
        for(Entity e: entities){
            e.move();
            checkXBoundaries(e);
            checkYBoundaries(e);
        }
    }

    /**
     * checks boundaries only for round cells
     * @param e
     */
    private void checkXBoundaries(Entity e){
        float threshold = e.getEntityRadius();
        if( e.getPosition().x < 0 + threshold){
            e.setXPosition(0 + threshold);
        }
        else if (e.getPosition().x > model.getWorldWidth() -threshold){
            e.setXPosition(model.getWorldWidth() - threshold);
        }
    }

    /*
    checks boundaries only for round cells
     */
    private void checkYBoundaries(Entity e){
        float threshold = e.getEntityRadius();

        if(e.getPosition().y < 0 +threshold){
            e.setYposition(0 + threshold);
        }
        else if(e.getPosition().y > model.getWorldHeight() - threshold){
            e.setYposition(model.getWorldHeight()- threshold);
        }
    }

    public void addMovementBehaviour(MovementBehaviour m){
        if(!movementBehaviours.contains(m))
            movementBehaviours.add(m);
    }
}
