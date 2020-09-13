package controller.behaviours;

import org.json.JSONObject;
import model.Model;
import model.entity.Entity;
import model.genetic.Function;
import model.genetic.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public abstract class AbstractBehaviour implements EntityBehaviour {

    private final Map<Entity, List<Function>> decisionFunctions = new HashMap<>();

    protected final Map<Entity, Integer> lastAction = new HashMap<>();
    protected final Map<Entity, List<Float>> entityInputs = new HashMap<>();

    protected final Model model;

    protected int numberOfActions;
    protected int numberOfInputs;

    /**
     * The number of actions is handled by the child classes
     */
    public AbstractBehaviour(){
        model = null;
        numberOfActions =-1;
        numberOfInputs = -1;
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
        try {
            String contents = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject specification = new JSONObject(contents);
            if (specification.has("numberOfInputs")){
                this.numberOfInputs = specification.getInt("numberOfInputs");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDecisionMakers(List<Entity> entities){
        for(Entity decider: entities){
            addDecisionMaker(decider);
        }
    }

    public void addDecisionMaker(Entity entity){
        List<Function> functions = new LinkedList<>();
        for(int i = 0; i < numberOfActions; i++){
            functions.add(Node.createRandomTree(this.numberOfInputs));
        }
        decisionFunctions.put(entity, functions);
        lastAction.put(entity, 0);
        List<Float> inputs = new ArrayList<>();
        for(int i = 0; i< numberOfInputs; i++)
            inputs.add(0f)
        entityInputs.put(entity, inputs);
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
        for(Entity entity : lastAction.keySet()){
            lastAction.put(entity, 0);
        }
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    public abstract void setEntityByName(String id, List<Function> trees);

}
