package model.genetic;

import model.entity.Entity;
import model.interfaces.EnergyDependent;
import model.interfaces.cinematic.Cinematic;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Individual implements EnergyDependent {

    private Cinematic entity;
    private float energy;
    private Chromosome chromosome;
    private static final float STARTING_ENERGY = 100;

    private final List<Float> variables = new ArrayList<>(4);

    //inputs
    private final int MEMORY = 0;
    private final int TARGET_POSX = 1;
    private final int TARGET_POSY = 2;
    private final int ENERGY = 3;

    public Individual(Cinematic entity, List<Float> variables){
        this.entity = entity;
        this.energy = STARTING_ENERGY;
        this.chromosome = new Chromosome(variables);
    }

    public Individual(Entity entity, Chromosome chromosome){
        this.entity = entity;
        this.energy = STARTING_ENERGY;
        this.chromosome = chromosome;
    }


    public synchronized Chromosome getChromosome(){
        return new Chromosome(chromosome);
    }

    public synchronized float fitness(){
        return energy;
    }

    public synchronized PVector getPosition(){
        return entity.getPosition();
    }

    public  synchronized Cinematic getCinematic(){
        return entity;
    }

    @Override
    public synchronized void increaseEnergy(float toAdd) {
        if(toAdd < 0) throw new RuntimeException("toAdd should be positive");
        energy += toAdd;
    }

    @Override
    public  synchronized void decreaseEnergy(float toSub) {
        if(toSub <0) throw new RuntimeException("toSub should be negative");
        energy -= toSub;
    }

    /*********************** ENERGY DEPENDENT ********/


    @Override
    public synchronized float getEnergy() {
        return energy;
    }
}
