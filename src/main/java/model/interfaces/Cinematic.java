package model.interfaces;

import processing.core.PVector;

public interface Cinematic {
    void move();

    PVector getPosition();

    void setXPosition(float xpos);

    void setYPosition(float ypos);

    PVector getSpeed();

    void setSpeed(PVector speed);

    void setSpeedMagnitude(float linearVelocity);

    void rotateSpeed(float angle);


}
