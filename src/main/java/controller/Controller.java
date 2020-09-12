package controller;

import Network.GeneticInterface;
import controller.MovementBehaviours.MovementBehaviour;
import controller.action.ActionExecutor;
import controller.action.ActionExecutorInterface;
import controller.behaviours.PreyBehaviour;
import model.*;
import model.entity.EntityFactory;
import model.entity.Predator;
import model.entity.Prey;
import model.genetic.Function;
import model.genetic.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private static int SERVER_PORT = 12346;
    private ServerSocket server;

    private Model model;
    //controllers
    private MovementHandler movementHandler;
    private ActionExecutorInterface executor;

    private PreyBehaviour preyBehaviour;

    private List<MovementBehaviour> behaviours = new LinkedList<>();

    private GeneticInterface preyGeneticInterface;

    private final static Logger logger = Logger.getLogger(Controller.class.getName());
    //modo stupido per controllare il tempo
    private int iterations = 0;


    private int NOFINPUTS = 2;

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

        this.preyBehaviour = new PreyBehaviour(this.model, 2,2);
        this.preyBehaviour.addPreys(preys);

        logger.log(Level.INFO, "Creation of the server");
        this.server = setUpServer();
        setUpCommunication();

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
    private void setUpCommunication(){
        try {
            logger.log(Level.INFO, "Waiting for connection");
            this.preyGeneticInterface = new GeneticInterface(this.server);
            logger.log(Level.INFO, "Setting up");
            preyGeneticInterface.sendSetUpParameters(50, NOFINPUTS, 0.8f, 0.1f, 4, "Prey");
            logger.log(Level.INFO, "Parameters sent");
            preyGeneticInterface.sendPopulation(preyBehaviour);
            logger.log(Level.INFO, "Population sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        // decisione delle prede
        if( iterations < 60){
            preyBehaviour.makeDecisions(executor);
            //decisione dei predatori
            //esecuzione azioni
            executor.runActions();
            movementHandler.moveAll();
            iterations++;
        }
        else{
            logger.log(Level.INFO, "End of generation");
            iterations = 0;
            //inviamo la fitness che mi è richiesta
            sendFitness();
            //python calcola i nuovi individui e me li invia
            //modifico gli individui esistenti
            JSONObject jsonPop = null;
            try {
                jsonPop = preyGeneticInterface.receiveNewPopulation();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(String key: jsonPop.keySet()){
                JSONArray funArray = jsonPop.getJSONObject(key).getJSONArray("functions");
                List<Function> functions = new LinkedList<>();
                for(int a = 0; a <funArray.length(); a++){
                    String tree = funArray.getString(a);
                    Function functionTree = Node.treeFromString(tree, NOFINPUTS);
                    functions.add(functionTree);
                }
                preyBehaviour.setEntityByName(key, functions);
            }
            //resetto positioni e tutto
            preyBehaviour.resetEntities();

        }

    }

    private void sendFitness(){
        try {
            preyGeneticInterface.sendFitness(preyBehaviour);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
