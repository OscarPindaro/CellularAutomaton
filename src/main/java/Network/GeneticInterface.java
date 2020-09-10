package Network;

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

    public void setUp(int populationSize, int ninputs, List<List<Function>> decisionFunctions){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("populationSize", populationSize);
        parameters.put("ninputs", ninputs);
        JSONObject setUpParameters = new JSONObject(parameters);
        out.println(setUpParameters);
        JSONArray array = funcToJsonArray(decisionFunctions);
        out.println(array.toString());
    }

    private JSONArray funcToJsonArray(List<List<Function>> decisionFunctions){
        JSONArray arrayExt = new JSONArray();
        for(List<Function> list : decisionFunctions){
            JSONArray arrayInt = new JSONArray();
            for(Function function : list){
                arrayInt.put(function);
            }
            arrayExt.put(arrayInt);
        }
        return arrayExt;
    }

    public void sendFitness(float[] fitnessArray){
        String communicationCheck = null;
        try {
            communicationCheck = in.readLine();
        } catch (IOException e) {
            closeSocket();
            throw new RuntimeException("Error with input stream");
        }
        if (!communicationCheck.equals("askFitness")){
            closeSocket();
            throw new RuntimeException("Error in the process of communication");
        }
        JSONArray jsonArray = new JSONArray(fitnessArray);
        out.println(jsonArray.toString());
    }

    public void sendTrees(List<List<Function>> functions){
        JSONArray toSend = funcToJsonArray(functions);
        out.println(toSend.toString());
    }

    public List<List<Function>> getNewFunctions(int ninputs){
        String functions = null;
        try {
             functions= in.readLine();
        } catch (IOException e) {
            closeSocket();
            throw new RuntimeException("Error with input stream");
        }
        JSONArray arrayExt = new JSONArray(functions);

        List<List<Function>> newFunctions = new LinkedList<>();
        for(int a = 0; a < arrayExt.length(); a++){
            List<Function> oneIndividualFunction = new LinkedList<>();
            JSONArray arrayInt = new JSONArray(arrayExt.get(a));
            for(int b =0; b < arrayInt.length(); b++){
                String tree = arrayInt.getString(a);
                Node newTree = Node.treeFromString(tree, ninputs);
                oneIndividualFunction.add(newTree);
            }
            newFunctions.add(oneIndividualFunction);
        }
        return newFunctions;
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
