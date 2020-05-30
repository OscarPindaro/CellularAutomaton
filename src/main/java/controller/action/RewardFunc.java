package controller.action;

public class RewardFunc extends AdditionalFunctionality {

    float reward;

    public RewardFunc(ActionInterface action, float reward ){
        this.reward = reward;
        this.action = action;
    }

    @Override
    public void perform() {
        action.perform();
    }
}
