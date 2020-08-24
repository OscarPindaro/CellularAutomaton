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
import model.genetic.Population;

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
    private int iterations = 0;


    public Controller(Model model){
        this.model = model;


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

    public void setUp(int npreys, int npreds ){
        List<Predator> predators = EntityFactory.createPredators(npreds);
        List<Prey> preys = EntityFactory.createPreys(npreys);
        this.movementHandler = new MovementHandler(model);
        registerPredators(predators);
        registerPreys(preys);


        //genetic controller for behaviours
    }

    private void registerPredators(List<Predator> predators){
        model.addPredators(predators);
        for(Predator predator : predators){
            movementHandler.addCinematicObject(predator);
            predator.attach(movementHandler);
        }
    }

    private void registerPreys(List<Prey> preys){
        model.addPreys(preys);
        for(Prey prey: preys){
            movementHandler.addCinematicObject(prey);
            prey.attach(movementHandler);
        }
    }

    public void update(){
        movementHandler.moveAll();
    }

}
