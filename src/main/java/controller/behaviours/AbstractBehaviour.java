package controller.behaviours;

import model.Model;
import model.entity.Entity;
import model.genetic.Function;
import model.genetic.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractBehaviour implements EntityBehaviour {

    private final Map<Entity, List<Function>> decisionFunctions = new HashMap<>();

    protected final Model model;

    protected final int numberOfActions;
    protected final int numberOfInputs;

    public AbstractBehaviour(){
        model = null;
        numberOfActions =0;
        numberOfInputs = 0;
    }

    public AbstractBehaviour(Model model, int nActions, int nInputs){
        this.model = model;
        this.numberOfActions = nActions;
        this.numberOfInputs = nInputs;
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

}
