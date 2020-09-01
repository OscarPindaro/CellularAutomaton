package Network;

import model.genetic.Function;
import model.genetic.Node;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class GeneticInterface {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public GeneticInterface(ServerSocket serverSocket) throws IOException {
        this.clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void setUp(List<Node> decisionFunctions){
        JSONArray array = new JSONArray();
        array.put(decisionFunctions);
        out.println(array.toString());
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

    public List<Function> getNewFunctions(){
        String functions = null;
        try {
             functions= in.readLine();
        } catch (IOException e) {
            closeSocket();
            throw new RuntimeException("Error with input stream");
        }
        JSONArray jsonFun = new JSONArray(functions);

        for(int i = 0; i < jsonFun.length(); i++){
            String tree = jsonFun.getString(i);

        }

        return null;
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
