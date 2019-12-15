package Model;

import processing.core.PVector;

import java.awt.*;

public class Predator extends Entity {
    private final Color PREDATOR_COLOR = Color.RED;

    public Predator(int posx, int posy, int speedx, int speedy){
        super(posx, posy, speedx, speedy);
        this.setColor(PREDATOR_COLOR);
    }

    public Predator(PVector position, PVector speed){
        super(position, speed);
        this.setColor(PREDATOR_COLOR);
    }
}
