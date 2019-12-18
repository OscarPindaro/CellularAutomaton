package Model;
import View.Automata;
import processing.core.PVector;


import java.awt.*;

public abstract class Entity implements Movable, Showable {

    private final int SIZE = 20;
    private float size = SIZE;


    private PVector position;
    private PVector speed;

    private Color color;

    public Entity(){
        super();
    }

    public Entity(int posx, int posy, int speedx, int speedy){
        position = new PVector(posx, posy);
        speed = new PVector(speedx, speedy);
    }

    public Entity(PVector position, PVector speed){
        this.position = position;
        this.speed = speed;
    }

    public void move() {
        position.add(speed);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void show(){
        Automata mySketch = Automata.getInstance();
        setFill();
        mySketch.ellipse(position.x, position.y, size, size);
    }


    private void setFill(){
        Automata mySketch = Automata.getInstance();
        mySketch.noStroke();
        mySketch.fill(color.getRed(), color.getGreen(), color.getBlue());
    }

    public PVector getPosition() {
        return new PVector(position.x, position.y);
    }

    public void setXPosition(float xpos) {
        this.position.x = xpos;
    }

    public void setYposition(float ypos){
        this.position.y = ypos;
    }

    public void incrementPosition(PVector speed){
        position.add(speed);
    }

    public PVector getSpeed() {
        return new PVector(speed.x, speed.y);
    }

    public void setSpeed(PVector speed) {
        this.speed = speed;
    }

    public void setSpeedMagnitude(float linearVelocity){
        speed.setMag(linearVelocity);
    }

    public void rotateSpeed(float angle){
        speed.rotate(angle);
    }

    public float getEntityRadius(){
        return size/2;
    }
}
