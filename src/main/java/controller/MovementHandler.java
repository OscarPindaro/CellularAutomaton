package controller;

import model.Model;
import model.interfaces.cinematic.Cinematic;
import model.interfaces.cinematic.PositionBoundaryObserver;

import java.util.List;

public class MovementHandler implements PositionBoundaryObserver {

    final Model model;
    List<Cinematic> movableObjects;

    public MovementHandler(Model model){
        this.model = model;
    }

    public void run(){
        for(Cinematic mov : movableObjects){
            mov.move();
        }
    }

    @Override
    public void checkBoundary(Cinematic cinematic, float radius) {
        checkXBoundaries(cinematic, radius);
        checkYBoundaries(cinematic, radius);
    }

    private void checkXBoundaries(Cinematic cinematic, float threshold){
        if(cinematic.getPosition().x < 0 + threshold)
            cinematic.setXPosition(0 + threshold);
        else if(cinematic.getPosition().x > model.getWorldWidth() - threshold)
            cinematic.setXPosition(model.getWorldWidth() - threshold);
    }

    private void  checkYBoundaries(Cinematic cinematic, float threshold){
        if(cinematic.getPosition().y < 0 +threshold){
            cinematic.setYPosition(0 + threshold);
        }
        else if(cinematic.getPosition().y > model.getWorldHeight() - threshold){
            cinematic.setYPosition(model.getWorldHeight()- threshold);
        }
    }
}
