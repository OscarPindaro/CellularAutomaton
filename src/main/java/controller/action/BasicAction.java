package controller.action;

import model.entity.Entity;
import model.interfaces.EnergyDependent;

public class BasicAction implements ActionInterface {

    private  final float cost;

    private final EnergyDependent agent;

    public BasicAction(float cost, EnergyDependent agent){
        if(cost < 0) throw new RuntimeException("cost should be always >= 0");
        this.cost = cost;
        this.agent = agent;
    }


    @Override
    public void perform() {
        agent.decreaseEnergy(cost);
    }
}
