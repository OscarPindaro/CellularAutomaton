package model.genetic;

import model.entity.Entity;
import model.interfaces.EnergyDependent;

import java.util.ArrayList;
import java.util.List;

public class Individual implements EnergyDependent {

    private Entity entity;
    private float energy;
    private Chromosome chromosome;
    private static final float STARTING_ENERGY = 100;

    private final List<Float> variables = new ArrayList<>(4);

    //inputs
    private final int MEMORY = 0;
    private final int TARGET_POSX = 1;
    private final int TARGET_POSY = 2;
    private final int ENERGY = 3;

    public Individual(Entity entity){
        this.entity = entity;
        this.energy = STARTING_ENERGY;
        variables.add(0f);
        variables.add(0f);
        variables.add(0f);
        variables.add(energy);
        this.chromosome = new Chromosome(variables);
        this.chromosome.setVariables(variables);
    }

    public Individual(Entity entity, Chromosome chromosome){
        this.entity = entity;
        this.energy = STARTING_ENERGY;
        variables.add(0f);
        variables.add(0f);
        variables.add(0f);
        variables.add(energy);
        this.chromosome = chromosome;
        this.chromosome.setVariables(variables);
    }


    public synchronized Chromosome getChromosome(){
        return new Chromosome(chromosome);
    }

    public synchronized float fitness(){
        return energy;
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
