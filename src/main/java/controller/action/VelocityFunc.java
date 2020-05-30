package controller.action;

public class VelocityFunc extends AdditionalFunctionality {

    private float linearVelocity;
    private float direction;

    public VelocityFunc(ActionInterface action, float linearVelocity, float velocityDirection){
        this.action = action;
    }

    @Override
    public void perform() {
        action.perform();
    }
}
