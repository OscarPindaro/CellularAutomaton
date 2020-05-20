package model.genetic;

import model.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Individual {

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

    public float getEnergy(){return energy;}

    public synchronized Chromosome getChromosome(){
        return new Chromosome(chromosome);
    }

    public synchronized float fitness(){
        return energy;
    }



}
