package controller.Genetic;

import model.genetic.Population;
import model.entity.Predator;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlghoritm {

    private Population population;
    private CrossOverManager crossover;
    private int generation;

    private final int iterations = 1000;
    private final int endFitness = 1000;
    private int currentIteration;
    private final float crossoverProbability = 0.8f;
    private final float mutationProbability = 0.1f;

    public GeneticAlghoritm(List<Predator> entities){
        population = new Population(new ArrayList<>(entities));
        generation = 0;
        crossover = new CrossOverManager();
    }

    public void evolve(List<Predator> entities){
        evaluateFitnessFunction();
        if(endConditions()) {
            return;
        }
        createNewPopulation(entities);
        generation++;
    }

    private void evaluateFitnessFunction(){
        population.evaluateFitness();
    }

    private boolean endConditions(){
        return currentIteration >= iterations || population.maxFitness()>=endFitness;
    }

    public void resetIndividuals(){
        population.reset();
    }

    public List<OldIndividual> getIndividuals(){
        return population.getIndividuals();
    }

    private void createNewPopulation(List<Predator> entities){
        int i = 0;
        while(i < population.getPopulationSize()){
            OldIndividual mother = population.selectIndividual();
            OldIndividual father = population.selectIndividual();
            List<OldIndividual> children;

            if( Math.random() <=  crossoverProbability){
                System.out.println(crossover);
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
