package Model;

import processing.core.PVector;

public abstract class MovementBehaviour {

    public abstract PVector calculateSpeed(PVector oldSpeed);
}
