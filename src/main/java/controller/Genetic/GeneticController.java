package controller.Genetic;

import model.*;
import model.entity.Prey;

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
        List<OldIndividual> individuals = ga.getIndividuals();
        List<Prey> preys = model.getPreys();
        for(OldIndividual i : individuals){
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
        List<OldIndividual> individuals = ga.getIndividuals();
        int action;
        for(OldIndividual i : individuals){
            action = i.chooseAction();
            //System.out.println(i + "\t\t" + action);
        }
    }
}
