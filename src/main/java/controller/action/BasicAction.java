package controller.action;

import model.entity.Entity;

public class BasicAction implements ActionInterface {

    private  float cost;

    private Entity source;

    public BasicAction(float cost, Entity entity){
        this.cost = cost;
        this.source = entity;
    }


    @Override
    public void perform() {

    }
}
