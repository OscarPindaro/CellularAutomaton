package model.entity;

import processing.core.PVector;

import java.awt.*;

public class Predator extends Entity {
    private final Color PREDATOR_COLOR = Color.RED;

    private final float MAX_LINEAR_VEL = 3;
    private final float MAX_ANGULAR_VEL = 0.08f;

    public Predator(){
        super();
    }

    public Predator(int posx, int posy, int speedx, int speedy){
        super(posx, posy, speedx, speedy);
        this.setColor(PREDATOR_COLOR);
    }

    public Predator(PVector position, PVector speed){
        super(position, speed);
        this.setColor(PREDATOR_COLOR);
    }

    public float getMAX_LINEAR_VEL() {
        return MAX_LINEAR_VEL;
    }

    public float getMAX_ANGULAR_VEL() {
        return MAX_ANGULAR_VEL;
    }
}
