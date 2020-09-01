package controller;

import controller.MovementBehaviours.MovementBehaviour;
import controller.action.ActionExecutor;
import controller.action.ActionExecutorInterface;
import controller.behaviours.PreyBehaviour;
import model.*;
import model.entity.EntityFactory;
import model.entity.Predator;
import model.entity.Prey;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    private static int SERVER_PORT = 12345;

    private Model model;
    //controllers
    private MovementHandler movementHandler;
    private ActionExecutorInterface executor;

    private PreyBehaviour preyBehaviour;

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
        this.executor = new ActionExecutor();

        List<Predator> predators = EntityFactory.createPredators(npreds);
        List<Prey> preys = EntityFactory.createPreys(npreys);
        this.movementHandler = new MovementHandler(model);
        registerPredators(predators);
        registerPreys(preys);

        this.preyBehaviour = new PreyBehaviour(this.model);
        this.preyBehaviour.addPreys(preys, 2);

        try{
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

        }
        catch (IOException ioe){
            throw new RuntimeException(ioe.getMessage());
        }

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
        // decisione delle prede
        preyBehaviour.makeDecisions(executor);
        //decisione dei predatori

        //esecuzione azioni
        executor.runActions();

        movementHandler.moveAll();
    }

}
