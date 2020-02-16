package Controller;

import Controller.Genetic.GeneticController;
import Controller.MovementBehaviours.FuzzyPredatorMovementBehaviour;
import Controller.MovementBehaviours.MovementBehaviour;
import Controller.MovementBehaviours.RandomMovementBehaviour;
import Model.*;

import java.io.IOException;
import java.util.List;

public class Controller {

    private Model model;
    //controllers
    private MovementController movementController;
    private GeneticController geneticController;

    //modo stupido per controllare il tempo
    private int i = 0;


    public Controller(Model model){
        movementController = new MovementController(model);
        this.model = model;
    }

    public RandomMovementBehaviour getRandomMovementBehaviour(){
        return new RandomMovementBehaviour();
    }

    public FuzzyPredatorMovementBehaviour getFuzzyPredatorMovementBehaviour(){
        String path = Controller.class.getResource("/fuzzyControllers/cellular.fis").getPath();
        FuzzyPredatorMovementBehaviour fuzzyBehaviour;
        try{
            fuzzyBehaviour = new FuzzyPredatorMovementBehaviour(model, path);
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            throw new RuntimeException("Controller non caricato");
        }
        return fuzzyBehaviour;
    }

    public void createRandomPredators(int numPredators, MovementBehaviour movementBehaviour){
        List<Predator> predators = EntityFactory.createRandomPredators(numPredators, movementBehaviour);
        movementController.addMovementBehaviour(movementBehaviour);
        model.addPredators(predators);
    }

    public void createRandomPreys(int numPreys, MovementBehaviour movementBehaviour){
        List<Prey> preys = EntityFactory.createRandomPreys(numPreys, movementBehaviour);
        movementController.addMovementBehaviour(movementBehaviour);
        model.addPreys(preys);
    }

    public void evolvePredators(){
        geneticController = new GeneticController(model);
    }

    public void update(){
        i++;
        if( i > 10000){
            geneticController.createNextGeneration();
        }
        geneticController.computeDecision();
        movementController.updateAllEntitiesSpeed();
        movementController.updateAllEntitiesPosition();
    }

}
