package controller.action;

import model.interfaces.EnergyDependent;

public class RewardFunc extends AdditionalFunctionality {

    /**
     * can be both positive and negative
     */
    private final float reward;
    private final EnergyDependent rewardAgent;

    public RewardFunc(ActionInterface action, EnergyDependent agent, float reward ){
        this.reward = reward;
        this.rewardAgent = agent;
        this.action = action;
    }

    @Override
    public void perform() {
        action.perform();
        changeEnergy();
    }

    private void changeEnergy(){
        if(reward >0)
            rewardAgent.increaseEnergy(reward);
        else
            rewardAgent.decreaseEnergy(-reward);
    }
}
