package controller;

import controller.Genetic.GeneticController;

import controller.Genetic.PredatorController;
import controller.MovementBehaviours.MovementBehaviour;
import controller.MovementBehaviours.RandomMovementBehaviour;
import controller.action.ActionExecutor;
import controller.action.ActionExecutorInterface;
import model.*;
import model.entity.Entity;
import model.entity.EntityFactory;
import model.entity.Predator;
import model.entity.Prey;
import model.genetic.Chromosome;
import model.genetic.Individual;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    private Model model;
    //controllers
    private MovementHandler movementHandler;
    private GeneticController geneticController;
    private ActionExecutorInterface executor;

    private PredatorController predatorController;

    private List<MovementBehaviour> behaviours = new LinkedList<>();

    //modo stupido per controllare il tempo
    private int i = 0;


    public Controller(Model model){
        this.movementHandler = new MovementHandler(model);
        this.model = model;


    }

    public Controller(Model model, int numberOfPredators){
        this.movementHandler = new MovementHandler(model);
        this.model = model;

        /********** CANCELLAAAAAAAAAAAAAAAAAA *************/
        List<Predator> predators =EntityFactory.createPredators(numberOfPredators);
        for(Predator predator: predators){
            predator.attach(movementHandler);
            movementHandler.addCinematicObject(predator);
        }


        model.addPredators(predators);

        List<Individual> individuals = new LinkedList<>();
        for(int i = 0; i < predators.size(); i++){
            List<Float> variables = new ArrayList<>();
            for(int j = 0; j < 8; j++) variables.add(0f);
            individuals.add(new Individual(predators.get(i), new Chromosome(variables)));
        }

        this.executor = new ActionExecutor();
        this.predatorController = new PredatorController(model, executor, individuals );

    }

    private void createPredatorsWithIndividualeDABUTTARE(){


    }

    public void addMovementBehaviour(MovementBehaviour behaviour){
        if(!behaviours.contains(behaviour)){
            behaviours.add(behaviour);
        }
    }

    public void createRandomPredators(int numPredators, MovementBehaviour movementBehaviour){
        List<Predator> predators = EntityFactory.createRandomPredators(numPredators, movementBehaviour);
        for(Predator predator : predators){
            movementHandler.addCinematicObject(predator);
            predator.attach(movementHandler);
        }
        model.addPredators(predators);
    }

    public void createRandomPreys(int numPreys, MovementBehaviour movementBehaviour){
        List<Prey> preys = EntityFactory.createRandomPreys(numPreys, movementBehaviour);
        for(Prey prey : preys){
            movementHandler.addCinematicObject(prey);
            prey.attach(movementHandler);
        }
        model.addPreys(preys);
    }




    public void update(){
        predatorController.run();
        executor.runActions();

        for(MovementBehaviour behaviour : behaviours){
            behaviour.updateAllEntitiesSpeed();
        }

        movementHandler.moveAll();
    }

}
