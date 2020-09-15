package controller.action;

import model.interfaces.cinematic.Cinematic;
import processing.core.PVector;

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

    /**
     * alligns the toChange velocity with direction
     * @param action
     * @param toChange
     * @param linearVelocity
     * @param direction
     */
    public VelocityFunc(ActionInterface action, Cinematic toChange, float linearVelocity, PVector direction){
        this.action = action;
        this.target = toChange;
        this.linearVelocity = linearVelocity;
        float currentHeading = toChange.getPosition().heading();
        float targetHeading = direction.heading();
        this.deltaAngle = targetHeading - currentHeading;
    }

    @Override
    public void perform() {
        action.perform();
        target.setSpeedMagnitude(linearVelocity);
        target.rotateSpeed(deltaAngle);
    }
}
