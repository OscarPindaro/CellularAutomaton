package Network;

import controller.behaviours.AbstractBehaviour;
import model.entity.Entity;
import model.genetic.Function;
import model.genetic.Node;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneticInterface {

    private Logger logger;

    private String name;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Parameters sent to the evolution module
     */
    private float cxpb = 0.8f;
    private float mutpb = 0.1f;
    private int popSize = 0;



    public GeneticInterface(String name, ServerSocket serverSocket, String paramFilePath, int popSize) throws IOException {
        this.name = name;
        this.logger =Logger.getLogger(GeneticInterface.class.getName()+" " + this.name);
        logger.log(Level.INFO, "Waiting for connection");
        this.clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.popSize = popSize;

    }

    public GeneticInterface(){

    }

    public void setUpInterface(AbstractBehaviour behaviour, String entitiesName) throws IOException {
        sendSetUpParameters( behaviour.getNumberOfInputs(),  behaviour.getNumberOfActions(), entitiesName);
        logger.log(Level.INFO, "Parameters sent");
        sendPopulation(behaviour);
        logger.log(Level.INFO, "Population sent");
    }

    private void loadSpecification(String path){
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(path)){
            JSONObject specification = (JSONObject) parser.parse(reader);
            if (specification.has("cxpb")){
                this.cxpb= specification.getFloat("cxpb");
            }
            if (specification.has("mutpb")){
                this.mutpb= specification.getFloat("mutpb");
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void sendSetUpParameters( int numberOfInputs, int numberOfFunctions, String name) throws IOException {
        String request = in.readLine();
        if (!request.equals("parameters"))
            throw new RuntimeException("wrong moment of calling this function or wrong request formatting");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("populationSize", this.popSize);
        parameters.put("numberOfInputs", numberOfInputs);
        parameters.put("cxpb", this.cxpb);
        parameters.put("mutpb", this.mutpb);
        parameters.put("nOfFunctions", numberOfFunctions);
        parameters.put("nameOfEntity", name);
        JSONObject setUpParameters = new JSONObject(parameters);
        out.println(setUpParameters);
        out.flush();
    }


    /**
     * Sens a dictionary indexed with the names of the preys
     * @param behaviour
     * @throws IOException
     */
    public void sendPopulation(AbstractBehaviour behaviour) throws IOException {
        String request = in.readLine();
        if (!request.equals("population"))
            throw new RuntimeException("wrong moment of calling this function or wrong request formatting");
        JSONObject map = dictionaryEntityFunctions(behaviour);
        out.println(map);
        out.flush();
    }

    /**
     * Creates a dictionary that maps each decider to its fitness and decision functions
     * @param behaviour
     * @return
     */
    public JSONObject dictionaryEntityFunctions(AbstractBehaviour behaviour){
        List<Entity> entities = behaviour.getEntities();
        JSONObject entityDictionary = new JSONObject();
        for ( Entity entity :entities) {
            List<Function> functions = behaviour.getFunctions(entity);
            JSONObject jsonObject = new JSONObject();
            JSONArray arrayOfFunctions = new JSONArray();
            for(Function function: functions)
                arrayOfFunctions.put(function);
            jsonObject.put("functions", arrayOfFunctions);
//            jsonObject.put("fitness", entity.getEnergy());
            entityDictionary.put(entity.toString(), jsonObject);
        }
        return entityDictionary;
    }

    /**
     * Sens a dictionary where the keys are the names of the entities, and the values are the fitness values
     * @param behaviour
     * @throws IOException
     */
    public void sendFitness(AbstractBehaviour behaviour) throws IOException{
        String request = in.readLine();
        if (!request.equals("fitness"))
            throw new RuntimeException("wrong moment of calling this function or wrong request formatting");
        JSONObject fitDict = dictionaryEntityFitness(behaviour);
        out.println(fitDict);
    }

    /**
     * Returns a dictionary where the keys are the names of the preys and the values are their fitness
     * @param behaviour
     * @return
     */
    public JSONObject dictionaryEntityFitness(AbstractBehaviour behaviour){
        List<Entity> entities = behaviour.getEntities();
        JSONObject entityDictionary = new JSONObject();
        for(Entity entity: entities){
            JSONObject fitnessField = new JSONObject();
            fitnessField.put("fitness", entity.getEnergy());
            entityDictionary.put(entity.toString(), fitnessField);
        }
        return entityDictionary;
    }

    public JSONObject receiveNewPopulation() throws IOException{
        String populationString = in.readLine();
        JSONObject popJson = new JSONObject(populationString);
//        List<String> keys = new ArrayList<>(popJson.keySet());
//        Collections.sort(keys);
//        System.out.println(keys);
        return  popJson;
    }

    private void closeSocket(){
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Problem while closing the socket");
        }

    }

}
