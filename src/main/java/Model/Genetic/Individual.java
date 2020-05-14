package Model.Genetic;

import Model.entity.Entity;
import processing.core.PVector;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Individual{

    private Entity entity;
    private float fitness;
    private float energy;
    private Chromosome chromosome;
    private static final float STARTING_ENERGY = 100;

    //inputs
    private final int MEMORY = 0;
    private final int TARGET_POSX = 1;
    private final int TARGET_POSY = 2;
    private final int ENERGY = 3;

    private List<Float> variables = new ArrayList<>(4);


    public Individual(Individual individual){
        this.entity = null;
        this.fitness = individual.getFitness();
        this.energy = individual.getEnergy();
        this.chromosome = individual.getChromosome();

    }


    public Individual(Entity entity){
        this.entity = entity;
        this.fitness = 0;
        this.energy = STARTING_ENERGY;
        variables.add(0f);
        variables.add(0f);
        variables.add(0f);
        variables.add(energy);
        this.chromosome = new Chromosome(variables);
    }

    public void mutate() {
        chromosome.mutate();
    }

    public float getFitness(){
        return fitness;
    }


    private float getEnergy() {
        return energy;
    }

    public Chromosome getChromosome() {
        return new Chromosome(chromosome);
    }

    public void setEntity(Entity e){
        this.entity = e;
    }

    public void evaluateFitnessFunction(){
        fitness = energy;
    }

    public Node getTree(){
        return null; //chromosome.getRoot();
    }

    public void setTree(Node root){
        throw new NotImplementedException();
    }

    public void reset(){
        fitness = 0;
        energy = STARTING_ENERGY;
        variables = new ArrayList<>(4);
        variables.add(0f);
        variables.add(0f);
        variables.add(0f);
        variables.add(energy);
        chromosome.setVariables(variables);
    }

    public void setTargetPosition(float x, float y){
        variables.set(TARGET_POSX, x);
        variables.set(TARGET_POSY, y);
    }

    public PVector getPosition(){
        return new PVector(entity.getPosition().x, entity.getPosition().y);
    }

    public int chooseAction(){
        int functionValue = (int) chromosome.computeFunction();
        return functionValue % variables.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("individuo");
        builder.append(":\t");
        builder.append(this.chromosome);
        return builder.toString();
    }
}
