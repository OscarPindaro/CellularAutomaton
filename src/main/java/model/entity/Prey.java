package model.entity;

import processing.core.PVector;

import java.awt.*;

public class Prey extends Entity {
    private final Color PREY_COLOR = Color.GREEN;

    public Prey(int posx, int posy, int speedx, int speedy){
        super(posx, posy, speedx, speedy);
        this.setColor(PREY_COLOR);
    }

    public Prey(PVector position, PVector speed){
        super(position, speed);
        this.setColor(PREY_COLOR);
    }

    public Prey() {
    }
}
