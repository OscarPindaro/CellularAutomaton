package controller;

import controller.Genetic.GeneticController;

import controller.MovementBehaviours.MovementBehaviour;
import controller.MovementBehaviours.RandomMovementBehaviour;
import model.*;
import model.entity.EntityFactory;
import model.entity.Predator;
import model.entity.Prey;

import java.util.LinkedList;
import java.util.List;

public class Controller {

    private Model model;
    //controllers
    private MovementHandler movementHandler;
    private GeneticController geneticController;

    private List<MovementBehaviour> behaviours = new LinkedList<>();

    //modo stupido per controllare il tempo
    private int i = 0;


    public Controller(Model model){
        this.movementHandler = new MovementHandler(model);
        this.model = model;
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
        for(MovementBehaviour behaviour : behaviours){
            behaviour.updateAllEntitiesSpeed();
        }

        movementHandler.moveAll();
    }

}
