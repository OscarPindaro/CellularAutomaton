package model.genetic;

import model.entity.Entity;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Population {

    private final List<Individual> individuals;
    private SelectionInterface selection = new RankSelection();
    private int populationSize;


    public Population(List<Entity> entities){
        individuals = new ArrayList<>(entities.size());
        for(Entity e: entities){
            individuals.add(new Individual(e, new Chromosome(new OperationNode("add", null))));
            throw  new RuntimeException("Corretto per problemi con altro codice. Da CAMBIARE IL COSTRUTTORE");
        }
        populationSize = entities.size();
    }

    public void addIndividual(Individual individual){
        // "Potrebbe creare problemi con thread Producer consumer"
    }

    public Individual getIndividual(int index){
        synchronized (individuals){
            return individuals.get(index);
        }
    }


    public Individual selectIndividual(){
        List<Individual> copy = null;
        synchronized (individuals){
            copy = new ArrayList<>(individuals);
        }
        copy.sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual individual, Individual t1) {
                return Float.compare(individual.fitness(), t1.fitness());
            }
        });
        return selection.extractIndividual(copy);
    }

    public float maxFitness(){
        synchronized (individuals) {
            float max = individuals.get(0).fitness();
            for (Individual i : individuals) {
                if (i.fitness() > max) {
                    max = i.fitness();
                }
            }
            return max;
        }
    }

    public synchronized int getPopulationSize(){
        return populationSize;
    }

    public List<Individual> getIndividuals(){
        synchronized (individuals){
            return new ArrayList<>(individuals);
        }
    }
}
