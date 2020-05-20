package controller.Genetic;

import model.genetic.Chromosome;
import model.genetic.CrossoverUnit;
import model.genetic.Individual;
import model.genetic.Population;
import model.entity.Predator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GeneticAlghoritm {


    private int generation;
    private CrossoverUnit crossoverUnit = new CrossoverUnit();

    private final int iterations = 1000;
    private final int endFitness = 1000;
    private int currentIteration;
    private final float crossoverProbability = 0.8f;
    private final float mutationProbability = 0.1f;

    public GeneticAlghoritm(){
        generation = 0;
    }


    private boolean endConditions(Population population){
        return currentIteration >= iterations || population.maxFitness()>=endFitness;
    }

    public List<Chromosome> evolveChromosomes(Population population){
        int i = 0;
        List<Chromosome> newChromosomes = new ArrayList<>(population.getPopulationSize());
        while(i < population.getPopulationSize()){
            Chromosome motherCr = population.selectIndividual().getChromosome();
            Chromosome fatherCr = population.selectIndividual().getChromosome();
            Chromosome[] children;

            if( Math.random() <=  crossoverProbability){
                children =crossoverUnit.crossover(motherCr, fatherCr);
                motherCr = children[0];
                fatherCr = children[1];
            }
            if ( Math.random() <= mutationProbability){
                motherCr = motherCr.mutate();
            }
            if ( Math.random() <= mutationProbability){
                fatherCr = fatherCr.mutate();
            }
            newChromosomes.add(motherCr);
            newChromosomes.add(fatherCr);
            i = i+2;
        }

        return newChromosomes;
    }
}
