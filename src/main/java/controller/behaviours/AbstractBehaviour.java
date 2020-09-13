package controller.behaviours;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import model.Model;
import model.entity.Entity;
import model.genetic.Function;
import model.genetic.Node;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class AbstractBehaviour implements EntityBehaviour {

    private final Map<Entity, List<Function>> decisionFunctions = new HashMap<>();

    protected final Model model;

    protected int numberOfActions;
    protected int numberOfInputs;

    public AbstractBehaviour(){
        model = null;
        numberOfActions =0;
        numberOfInputs = 0;
    }

    public AbstractBehaviour(Model model, String path){
        this.model = model;
        this.numberOfActions = -1;
        this.numberOfInputs = -1;
        loadSpecFile(path);
    }

    public AbstractBehaviour(Model model, int nActions, int nInputs){
        this.model = model;
        this.numberOfActions = nActions;
        this.numberOfInputs = nInputs;
    }

    private void loadSpecFile(String filePath){
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)){
            JSONObject specification = (JSONObject) parser.parse(reader);
            if (specification.has("numberOfInputs")){
                this.numberOfInputs = specification.getInt("numberOfInputs");
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

    public void addDecisionMakers(List<Entity> entities){
        for(Entity decider: entities){
            List<Function> functions = new LinkedList<>();
            for(int i = 0; i < numberOfActions; i++){
                functions.add(Node.createRandomTree(this.numberOfInputs));
            }
            decisionFunctions.put(decider, functions);
        }
    }

    public void addDecisionMaker(Entity entity){
        List<Function> functions = new LinkedList<>();
        for(int i = 0; i < numberOfActions; i++){
            functions.add(Node.createRandomTree(this.numberOfInputs));
        }
        decisionFunctions.put(entity, functions);
    }

    public List<Function> getFunctions(Entity entity){
        return decisionFunctions.get(entity);
    }

    public List<Entity> getEntities(){
        return new LinkedList<>(decisionFunctions.keySet());
    }

    public void setDecisionFunctions(Entity entity, List<Function> functions){
        decisionFunctions.put(entity, functions);
    }

    public void resetEntities(){
        assert model != null;
        model.resetEntities(new ArrayList<>(decisionFunctions.keySet()));
    }
    public abstract void setEntityByName(String id, List<Function> trees);

}
