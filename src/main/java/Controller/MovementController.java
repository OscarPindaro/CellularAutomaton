package Controller;

import Model.*;

import java.util.List;

public class MovementController {

    private final Model model;

    public MovementController(Model model){
        this.model = model;
    }

    public void updateAllEntitiesMovement(){
        List<Entity> entities = model.getEntities();
        for(Entity e: entities){
            e.move();
            checkXBoundaries(e);
            checkYBoundaries(e);
        }
    }

    private void checkXBoundaries(Entity e){
        float threshold = e.getEntityRadius();
        if( e.getPosition().x < 0 + threshold){
            e.setXPosition(0 + threshold);
        }
        else if (e.getPosition().x > model.getWorldWidth() -threshold){
            e.setXPosition(model.getWorldWidth() - threshold);
        }
    }

    private void checkYBoundaries(Entity e){
        float threshold = e.getEntityRadius();

        if(e.getPosition().y < 0 +threshold){
            e.setYposition(0 + threshold);
        }
        else if(e.getPosition().y > model.getWorldHeight() - threshold){
            e.setYposition(model.getWorldHeight()- threshold);
        }
    }
}
