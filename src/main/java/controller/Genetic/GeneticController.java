package controller.Genetic;

import controller.action.ActionExecutorInterface;
import model.*;
import model.genetic.Population;

public class GeneticController {

    private GeneticAlghoritm ga;
    private Model model;
    private Population population;
    PredatorControllerOLD predatorController;
    private int iterations = 0;

    public GeneticController(Population population, Model model, ActionExecutorInterface actionExecutor){

        this.population = population;
        predatorController = new PredatorControllerOLD(model, actionExecutor,population.getIndividuals());
        ga = new GeneticAlghoritm();

    }

}
