package Controller.Genetic;

import Model.*;
import Model.Genetic.Individual;
import Model.entity.Prey;

import java.util.List;

public class GeneticController {

    private GeneticAlghoritm ga;
    private Model model;

    public GeneticController(Model model){
        this.model = model;
        ga = new GeneticAlghoritm(model.getPredators());
    }

    public void createNextGeneration(){
        ga.evolve(model.getPredators());
        model.resetExistingEntities();
        ga.resetIndividuals();
    }

    //NB suppone che ci siano sempre prede!
    public void computeDecision(){
        computeTarget();
        computeAction();
    }

    private void computeTarget(){
        List<Individual> individuals = ga.getIndividuals();
        List<Prey> preys = model.getPreys();
        for(Individual i : individuals){
            Prey nearestPrey = preys.get(0);
            float minDistance = nearestPrey.getPosition().dist(i.getPosition());
            for(Prey prey : preys){
                if (prey.getPosition().dist(i.getPosition())< minDistance){
                    nearestPrey = prey;
                }
            }
            i.setTargetPosition(nearestPrey.getPosition().x, nearestPrey.getPosition().y);
        }
    }

    private void computeAction(){
        List<Individual> individuals = ga.getIndividuals();
        int action;
        for(Individual i : individuals){
            action = i.chooseAction();
            //System.out.println(i + "\t\t" + action);
        }
    }
}
