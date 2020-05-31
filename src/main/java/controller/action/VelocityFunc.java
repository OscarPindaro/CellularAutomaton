package controller.action;

import model.interfaces.cinematic.Cinematic;

public class VelocityFunc extends AdditionalFunctionality {

    private float linearVelocity;
    private float direction;
    private Cinematic target;

    public VelocityFunc(ActionInterface action, Cinematic toChange, float linearVelocity, float velocityDirection){
        this.action = action;
        this.target = toChange;
        this.linearVelocity = linearVelocity;
        this.direction = velocityDirection;
    }

    @Override
    public void perform() {
        action.perform();
        target.setSpeedMagnitude(linearVelocity);
        target.rotateSpeed(direction);
    }
}
