package Controller.Genetic;

import Model.Genetic.Individual;
import Model.Genetic.Population;
import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlghoritm {

    private Population population;
    private CrossOverManager crossover;
    private final int iterations = 1000;
    private final int endFitness = 1000;
    private int currentIteration;
    private final float crossoverProbability = 0.8f;
    private final float mutationProbability = 0.1f;

    public GeneticAlghoritm(List<Entity> entities){
        population = new Population(new ArrayList<>(entities));

    }

    public void evolve(List<Entity> entities){
        evaluateFitnessFunction();
        if(endConditions()) {
            return;
        }
        createNewPopulation(entities);
    }

    private void evaluateFitnessFunction(){
        population.evaluateFitness();
    }

    private boolean endConditions(){
        return currentIteration >= iterations || population.maxFitness()>=endFitness;
    }

    private void createNewPopulation(List<Entity> entities){
        int i = 0;
        while(i < population.getPopulationSize()){
            Individual mother = population.selectIndividual();
            Individual father = population.selectIndividual();
            List<Individual> children;

            if( Math.random() <=  crossoverProbability){
                children =crossover.cross(mother, father);
                mother = children.get(0);
                father = children.get(1);
            }
            if ( Math.random() <= mutationProbability){
                mother.mutate();
            }
            if ( Math.random() <= mutationProbability){
                father.mutate();
            }

            mother.setEntity(entities.get(i));
            father.setEntity(entities.get(i+1));

            i = i+2;
        }

    }
}
