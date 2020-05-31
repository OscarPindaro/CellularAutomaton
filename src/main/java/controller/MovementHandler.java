package controller;

import model.Model;
import model.interfaces.cinematic.Cinematic;
import model.interfaces.cinematic.PositionBoundaryObserver;

import java.util.LinkedList;
import java.util.List;

public class MovementHandler implements PositionBoundaryObserver {

    protected final Model model;
    protected final List<Cinematic> movableObjects = new LinkedList<>();

    public MovementHandler(Model model){
        this.model = model;
    }

    public void moveAll(){
        for(Cinematic mov : movableObjects){
            mov.move();
        }
    }

    public void addCinematicObject(Cinematic cinematic){
        if(!movableObjects.contains(cinematic))
            movableObjects.add(cinematic);
    }


    /************** POSITION BOUNDARY OBSERVER *********************/
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
