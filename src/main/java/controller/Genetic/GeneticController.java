package controller.Genetic;

import controller.action.ActionExecutor;
import controller.action.ActionExecutorInterface;
import model.*;
import model.entity.Prey;
import model.genetic.Chromosome;
import model.genetic.Population;

import java.util.List;

public class GeneticController {

    private GeneticAlghoritm ga;
    private Model model;
    private Population population;
    PredatorController predatorController;
    private int iterations = 0;

    public GeneticController(Population population, Model model, ActionExecutorInterface actionExecutor){

        this.population = population;
        predatorController = new PredatorController(model, actionExecutor,population.getIndividuals());
        ga = new GeneticAlghoritm();

    }

}
