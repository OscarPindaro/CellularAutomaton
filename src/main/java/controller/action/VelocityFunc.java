package controller.action;

import model.interfaces.cinematic.Cinematic;

public class VelocityFunc extends AdditionalFunctionality {

    private float linearVelocity;
    private float deltaAngle;
    private Cinematic target;

    public VelocityFunc(ActionInterface action, Cinematic toChange, float linearVelocity, float deltaDirection){
        this.action = action;
        this.target = toChange;
        this.linearVelocity = linearVelocity;
        this.deltaAngle = deltaDirection;
    }

    @Override
    public void perform() {
        action.perform();
        target.setSpeedMagnitude(linearVelocity);
        target.rotateSpeed(deltaAngle);
    }
}
