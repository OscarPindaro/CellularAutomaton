package Model.Genetic;

import Model.entity.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Population {

    private List<Individual> individuals;
    private SelectionInterface selection = new RankSelection();
    private int populationSize;


    public Population(List<Entity> entities){
        individuals = new ArrayList<>(entities.size());
        for(Entity e: entities){
            individuals.add(new Individual(e));
        }
        populationSize = entities.size();
        for(Individual i : individuals){
            System.out.println(i);
        }
    }

    public Individual selectIndividual(){
        List<Individual> copy = new ArrayList<>(individuals);
        copy.sort(new DescendingComparator());
        return selection.extractIndividual(copy);
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

class DescendingComparator implements Comparator<Individual>{

    @Override
    public int compare(Individual individual, Individual t1) {
        if(individual.getFitness() > t1.getFitness()){
            return 1;
        }
        else if(individual.getFitness() == t1.getFitness()){
            return 0;
        }
        else {
            return -1;
        }
    }
}