package Model.Genetic;

import Model.Entity;

import java.util.LinkedList;

public class Individual implements MutationInterface{

    private Entity entity;
    private float fitness;
    private float energy;
    private Chromosome chromosome;

    public Individual(Individual individual){
        this.entity = null;
        this.fitness = individual.getFitness();
        this.energy = individual.getEnergy();
        this.chromosome = individual.getChromosome();
    }


    public Individual(Entity entity){
        this.entity = entity;
        this.fitness = 0;
        this.energy = 100;
        this.chromosome = new Chromosome(new LinkedList<>());
    }

    @Override
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
        return chromosome.getRoot();
    }

    public void setTree(Node root){
        chromosome.setRoot(root);
    }
}
