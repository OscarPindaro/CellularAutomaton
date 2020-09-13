package controller;

import Network.GeneticInterface;
import controller.MovementBehaviours.MovementBehaviour;
import controller.action.ActionExecutor;
import controller.action.ActionExecutorInterface;
import controller.behaviours.AbstractBehaviour;
import controller.behaviours.EntityBehaviour;
import controller.behaviours.PredatorBehaviour;
import controller.behaviours.PreyBehaviour;
import model.*;
import model.entity.Entity;
import model.entity.EntityFactory;
import model.entity.Predator;
import model.entity.Prey;
import model.genetic.Function;
import model.genetic.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private static int SERVER_PORT = 12346;
    private ServerSocket server;

    private String PREY_SPEC_FILE = "specificationFiles/prey.json";
    private String PRED_SPEC_FILE = "specificationFiles/pred.json";

    private Model model;
    //controllers
    private MovementHandler movementHandler;
    private ActionExecutorInterface executor;

    Map<AbstractBehaviour, GeneticInterface> interfaceMap = new HashMap<>();

    private PreyBehaviour preyBehaviour;
    private PredatorBehaviour predatorBehaviour;
    private List<AbstractBehaviour> entityBehaviours = new LinkedList<>();

    private List<MovementBehaviour> movementBehaviours = new LinkedList<>();

    private GeneticInterface preyGeneticInterface;
    private GeneticInterface predatorGeneticInterface;
    private List<GeneticInterface> geneticInterfaces = new LinkedList<>();

    private final static Logger logger = Logger.getLogger(Controller.class.getName());
    //modo stupido per controllare il tempo
    private int iterations = 0;
    private int ngen = 0;

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

    public void setUp(int nOfPreys, int nOfPredators ){
        this.executor = new ActionExecutor();

        List<Predator> predators = EntityFactory.createPredators(nOfPredators);
        List<Prey> preys = EntityFactory.createPreys(nOfPreys);
        this.movementHandler = new MovementHandler(model);
        registerPredators(predators);
        registerPreys(preys);

        this.preyBehaviour = new PreyBehaviour(this.model);
        this.preyBehaviour.addPreys(preys);

        this.predatorBehaviour = new PredatorBehaviour(this.model);
        this.predatorBehaviour.addPredators(predators);

        logger.log(Level.INFO, "Creation of the server");
        this.server = setUpServer();
        if(preys.size()>0){
            setUpCommunicationPreys(PREY_SPEC_FILE, preys.size());
        }
        if(predators.size()>0){
            setUpCommunicationPredators(PRED_SPEC_FILE, predators.size());
        }


    }

    private void registerPredators(List<Predator> predators){
        model.addPredators(predators);
        for(Predator predator : predators){
            movementHandler.addCinematicObject(predator);
            predator.attach(movementHandler);
        }
    }

    /**
     * Adds prey in the model and links the movement handler to them.
     * @param preys
     */
    private void registerPreys(List<Prey> preys){
        model.addPreys(preys);
        for(Prey prey: preys){
            movementHandler.addCinematicObject(prey);
            prey.attach(movementHandler);
        }
    }

    /**
     * Set up the server used to communicate with the python module
     * @return a server
     */
    private ServerSocket setUpServer(){
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(SERVER_PORT);

        }
        catch (IOException ioe){
            throw new RuntimeException(ioe.getMessage());
        }
        return  serverSocket;
    }

    /**
     * Set ups the communication with the python module by sending parameters and population
     */
    private void setUpCommunicationPreys(String specPath, int popSize){
        try {
            logger.log(Level.INFO, "Waiting for connection");
            this.preyGeneticInterface = new GeneticInterface("Prey Interface",this.server, specPath, popSize);
            this.preyGeneticInterface.setUpInterface(preyBehaviour, "Prey");
            entityBehaviours.add(preyBehaviour);
            interfaceMap.put(preyBehaviour, preyGeneticInterface);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpCommunicationPredators(String specPath, int popSize){
        try {
            logger.log(Level.INFO, "Waiting for connection");
            this.predatorGeneticInterface = new GeneticInterface("Predator Interface",this.server, specPath, popSize);
            this.predatorGeneticInterface.setUpInterface(predatorBehaviour, "Predator");
            entityBehaviours.add(predatorBehaviour);
            interfaceMap.put(predatorBehaviour, predatorGeneticInterface);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        // decisione delle prede
        if( iterations < 180){
            if (iterations == 0) logger.log(Level.INFO, "Generation " + ngen);
            for(EntityBehaviour behaviour: entityBehaviours){
                behaviour.makeDecisions(executor);
            }
            executor.runActions();
            movementHandler.moveAll();
            iterations++;
        }
        else{
            logger.log(Level.INFO, "End of generation " + ngen);
            iterations = 0;
            ngen++;
            //inviamo la fitness che mi è richiesta
            sendFitness();
            //python calcola i nuovi individui e me li invia
            //modifico gli individui esistenti
            modifyIndividuals();
        }

    }

    private void sendFitness(){
        try {
            for(AbstractBehaviour behaviour: entityBehaviours){
                interfaceMap.get(behaviour).sendFitness(behaviour);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This methods modifies the individuals of each behaviour. The python module is asked to give the new population,
     * which is set and then resetted
     */
    private void modifyIndividuals(){
        for(AbstractBehaviour behaviour: entityBehaviours){
            JSONObject jsonPop = null;
            GeneticInterface gi = interfaceMap.get(behaviour);
            try {
                jsonPop = gi.receiveNewPopulation();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(String key: jsonPop.keySet()){
                JSONArray funArray = jsonPop.getJSONObject(key).getJSONArray("functions");
                List<Function> functions = new LinkedList<>();
                for(int a = 0; a <funArray.length(); a++){
                    String tree = funArray.getString(a);
                    Function functionTree = Node.treeFromString(tree, behaviour.getNumberOfInputs());
                    functions.add(functionTree);
                }
                behaviour.setEntityByName(key, functions);
            }
            behaviour.resetEntities();
        }
    }


}
