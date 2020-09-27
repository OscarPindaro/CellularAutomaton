package model.entity;

import model.interfaces.cinematic.PositionObserver;
import model.interfaces.energy.EnergyDependent;
import model.interfaces.cinematic.Cinematic;
import model.interfaces.cinematic.PositionBoundaryObserver;
import model.interfaces.cinematic.PositionObservable;
import model.interfaces.energy.EnergyObservable;
import model.interfaces.energy.EnergyObserver;
import view.Automata;
import processing.core.PVector;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class Entity implements Cinematic, PositionObservable, EnergyDependent, EnergyObservable {

    private final int SIZE = 40;
    private float size = SIZE;
    private float STARTING_ENERGY = 900;


    private final PVector position;
    private final PVector speed;
    private float energy = STARTING_ENERGY;
    private boolean isDead = false;

    protected int id=-1;

    private final List<PositionBoundaryObserver> positionBoundaryObservers = new LinkedList<>();
    private final List<EnergyObserver> energyObservers = new LinkedList<>();
    private final List<PositionObserver> positionObservers = new LinkedList<>();

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

    public int getId(){
        return id;
    }

    public float getStartingEnergy(){
        return STARTING_ENERGY;
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
        synchronized (positionBoundaryObservers){
            if(!positionBoundaryObservers.contains(observer))
                positionBoundaryObservers.add(observer);
        }
    }

    @Override
    public void remove(PositionBoundaryObserver observer) {
        synchronized (positionBoundaryObservers){
            positionBoundaryObservers.remove(observer);
        }
    }

    @Override
    public void notifyPositionChange() {
        for(PositionBoundaryObserver observer : positionBoundaryObservers){
            observer.checkBoundary(this, getEntityRadius());
        }
        for(PositionObserver observer: positionObservers)
            observer.checkPosition(this);
    }

    /************** Energy dependent ************/
    @Override
    public void increaseEnergy(float toAdd) {
        this.energy += toAdd;
        notifyEnergyChange();
    }

    @Override
    public void decreaseEnergy(float toSub) {
        this.energy -=toSub;
        notifyEnergyChange();
    }

    @Override
    public float getEnergy() {
        return this.energy;
    }

    @Override
    public void setEnergy(float newEnergy) {
        this.energy = newEnergy;
        notifyEnergyChange();
    }

    @Override
    public void setDeath(boolean isDead) {
        this.isDead = isDead;
        this.setSpeedMagnitude(0.000001f);
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    /******** ENERGY OBSERVABLE ****************/
    @Override
    public void notifyEnergyChange() {
        for(EnergyObserver observer : energyObservers){
            observer.checkEnergy(this);
        }
    }

    @Override
    public void attach(EnergyObserver observer) {
        energyObservers.add(observer);
    }

    @Override
    public void remove(EnergyObserver observer) {
        energyObservers.remove(observer);
    }

    @Override
    public void attach(PositionObserver observer) {
        positionObservers.add(observer);
    }

    @Override
    public void remove(PositionObserver observer) {
        positionObservers.add(observer);
    }
}
