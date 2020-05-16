package model.genetic;

import model.entity.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Population {

    private List<OldIndividual> individuals;
    private SelectionInterface selection = new RankSelection();
    private int populationSize;


    public Population(List<Entity> entities){
        individuals = new ArrayList<>(entities.size());
        for(Entity e: entities){
            individuals.add(new OldIndividual(e));
        }
        populationSize = entities.size();
        for(OldIndividual i : individuals){
            System.out.println(i);
        }
    }

    public OldIndividual selectIndividual(){
        List<OldIndividual> copy = new ArrayList<>(individuals);
        copy.sort(new DescendingComparator());
        return selection.extractIndividual(copy);
    }

    public void evaluateFitness(){
        for(OldIndividual i : individuals){
            i.evaluateFitnessFunction();
        }
    }

    public float maxFitness(){
        float max = individuals.get(0).getFitness();
        for (OldIndividual i : individuals){
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
        for(OldIndividual i: individuals){
            i.reset();
        }
    }

    public List<OldIndividual> getIndividuals(){
        return new ArrayList<>(individuals);
    }
}

class DescendingComparator implements Comparator<OldIndividual>{

    @Override
    public int compare(OldIndividual individual, OldIndividual t1) {
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