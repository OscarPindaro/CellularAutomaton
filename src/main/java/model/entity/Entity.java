package model.entity;

import model.interfaces.Cinematic;
import view.Automata;
import processing.core.PVector;

import java.awt.*;

public abstract class Entity implements Cinematic {

    private final int SIZE = 40;
    private float size = SIZE;


    private final PVector position;
    private final PVector speed;

    private Color color;

    public Entity(){
        super();
        position = new PVector(0,0);
        speed = new PVector(0,0 );
    }

    public Entity(int posx, int posy, int speedx, int speedy){
        position = new PVector(posx, posy);
        speed = new PVector(speedx, speedy);
    }

    public Entity(PVector position, PVector speed){
        this.position = position;
        this.speed = speed;
    }

    public synchronized void move() {
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

    public Color getColor(){
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }
    public PVector getPosition() {
        synchronized (speed){
            return new PVector(position.x, position.y);
        }
    }

    public void setXPosition(float xpos) {
        synchronized (position){
            this.position.x = xpos;
        }
    }

    public void setYPosition(float ypos){
        synchronized (position){
            this.position.y = ypos;
        }
    }

    public synchronized void incrementPosition(PVector speed){
        position.add(speed);
    }

    public PVector getSpeed() {
        synchronized (speed){
            return new PVector(speed.x, speed.y);
        }
    }

    public void setSpeed(PVector newSpeed) {
        synchronized (this.speed){
            this.speed.x = newSpeed.x;
            this.speed.y = newSpeed.y;
        }
    }

    public void setSpeedMagnitude(float linearVelocity){
        synchronized (speed){
            speed.setMag(linearVelocity);
        }
    }

    public void rotateSpeed(float angle){
        synchronized (speed){
            speed.rotate(angle);
        }
    }

    public synchronized float getEntityRadius(){
        return size/2;
    }
}
