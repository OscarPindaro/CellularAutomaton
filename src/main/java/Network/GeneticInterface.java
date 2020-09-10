package Network;

import controller.behaviours.AbstractBehaviour;
import model.entity.Entity;
import model.genetic.Function;
import model.genetic.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GeneticInterface {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public GeneticInterface(ServerSocket serverSocket) throws IOException {
        this.clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public GeneticInterface(){

    }

    public void setUp(int populationSize, int ninputs) throws IOException {
        System.out.println("bbbbbbbbbbb");
        String request = in.readLine();
        System.out.println("aaaaaaaaaa");
        if (!request.equals("parameters"))
            throw new RuntimeException("wrong moment of calling this function or wrong request formatting");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("populationSize", populationSize);
        parameters.put("numberOfInputs", ninputs);
        JSONObject setUpParameters = new JSONObject(parameters);
        System.out.println("Sending parameters");
        out.println(setUpParameters);
        out.flush();
    }


    public void sendPopulation(AbstractBehaviour behaviour) throws IOException {
        String request = in.readLine();
        if (!request.equals("population"))
            throw new RuntimeException("wrong moment of calling this function or wrong request formatting");
        JSONObject map = dictionaryEntityFunctions(behaviour);
        out.println(map);
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
            jsonObject.put("fitness", entity.getEnergy());
            entityDictionary.put(entity.toString(), jsonObject);
        }
        return entityDictionary;
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
