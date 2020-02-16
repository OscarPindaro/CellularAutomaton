package Model.Genetic;

import Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Individual> individuals;
    private SelectionInterface selection;
    private int populationSize;


    public Population(List<Entity> entities){
        individuals = new ArrayList<>(entities.size());
        for(Entity e: entities){
            individuals.add(new Individual(e));
        }
        populationSize = entities.size();
    }

    public Individual selectIndividual(){
        return selection.extractIndividual(new ArrayList<>(individuals));
    }

    public void evaluateFitness(){
        for(Individual i : individuals){
            i.evaluateFitnessFunction();
        }
    }

    public float maxFitness(){
        float max = individuals.get(0).getFitness();
        for (Individual i : individuals){
            if(i.getFitness() > max){
                max = i.getFitness();
            }
        }
        return max;
    }

    public int getPopulationSize(){
        return populationSize;
    }

    public void reset(){
        for(Individual i: individuals){
            i.reset();
        }
    }

    public List<Individual> getIndividuals(){
        return new ArrayList<>(individuals);
    }
}
