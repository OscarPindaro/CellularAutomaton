package model.entity;

import model.interfaces.cinematic.Cinematic;
import model.interfaces.cinematic.PositionBoundaryObserver;
import model.interfaces.cinematic.PositionObservable;
import view.Automata;
import processing.core.PVector;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class Entity implements Cinematic, PositionObservable {

    private final int SIZE = 40;
    private float size = SIZE;


    private final PVector position;
    private final PVector speed;

    private final List<PositionBoundaryObserver> observers = new LinkedList<>();

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

/********* CINEMATIC ******************/

    public synchronized void move() {
        position.add(speed);
        notifyPositionChange();
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
        notifyPositionChange();
    }

    public void setYPosition(float ypos){
        synchronized (position){
            this.position.y = ypos;
        }
        notifyPositionChange();
    }

    public synchronized void incrementPosition(PVector speed){
        position.add(speed);
        notifyPositionChange();
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

    /********************* POSITION OBSERVABLE *************************/

    @Override
    public void attach(PositionBoundaryObserver observer) {
        synchronized (observers){
            if(!observers.contains(observer))
                observers.add(observer);
        }
    }

    @Override
    public void remove(PositionBoundaryObserver observer) {
        synchronized (observers){
            observers.remove(observer);
        }
    }

    @Override
    public void notifyPositionChange() {
        for(PositionBoundaryObserver observer : observers)
            observer.checkBoundary(this, getEntityRadius());
    }

}
