package controller.behaviours;

import model.Model;
import model.entity.Prey;
import model.genetic.Function;
import model.genetic.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractBehaviour implements EntityBehaviour {

    private final Map<DecisionMaker, List<Function>> decisionFunctions = new HashMap<>();

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

    public void addDecisionMakers(List<DecisionMaker> decisionMakers){
        for(DecisionMaker decider: decisionMakers){
            List<Function> functions = new LinkedList<>();
            for(int i = 0; i < numberOfActions; i++){
                functions.add(Node.createRandomTree(this.numberOfInputs));
            }
            decisionFunctions.put(decider, functions);
        }
    }

    public void addDecisionMaker(DecisionMaker decisionMaker){
        List<Function> functions = new LinkedList<>();
        for(int i = 0; i < numberOfActions; i++){
            functions.add(Node.createRandomTree(this.numberOfInputs));
        }
        decisionFunctions.put(decisionMaker, functions);
    }

    public List<Function> getFunctions(DecisionMaker decisionMaker){
        return decisionFunctions.get(decisionMaker);
    }

    public void setDecisionFunctions(DecisionMaker decisionMaker, List<Function> functions){
        decisionFunctions.put(decisionMaker, functions);
    }

}
