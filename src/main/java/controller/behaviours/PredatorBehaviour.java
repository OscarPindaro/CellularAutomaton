package controller.behaviours;

import controller.action.ActionExecutorInterface;
import controller.action.ActionInterface;
import controller.action.BasicAction;
import controller.action.VelocityFunc;
import model.Model;
import model.entity.Predator;
import model.entity.Prey;
import model.genetic.Function;
import model.genetic.Node;

import java.util.*;

public class PredatorBehaviour extends AbstractBehaviour{

    private final List<Predator> predatorList = new LinkedList<>();

    private final Map<Predator, List<Function>> predatorDecisionFunctions;

    private static final String SPEC_FILE = "specificationFiles/predator.json";
    private final int N_OF_ACTIONS_CONST = 2;

    public PredatorBehaviour(Model model){
        super(model, SPEC_FILE);
        predatorDecisionFunctions = new HashMap<>();
        this.numberOfActions = N_OF_ACTIONS_CONST;

    }

    public void addPredators(List<Predator> predators){
        predatorList.addAll(predators);
        for(Predator predator: predatorList){
            List<Function> functions = new LinkedList<>();
            for(int i = 0; i < numberOfActions; i++){
                functions.add(Node.createRandomTree(this.numberOfInputs));
            }
            predatorDecisionFunctions.put(predator, functions);
        }
        //add to super class
        for(Predator predator : predators)
            super.setDecisionFunctions(predator, predatorDecisionFunctions.get(predator));
    }

    public void setDecisionFunction(Predator predator, List<Function> functions){
        predatorDecisionFunctions.put(predator, functions);
        super.setDecisionFunctions(predator, functions);
    }

    @Override
    public void makeDecisions(ActionExecutorInterface executor) {
        for(Predator predator: predatorDecisionFunctions.keySet()){
            List<Function> f = predatorDecisionFunctions.get(predator);
            float x = predator.getPosition().x;
            float y = predator.getPosition().y;
            List<Float> inputs= new ArrayList<>(2);
            inputs.add(x);
            inputs.add(y);
            float value = f.get(0).compute(inputs);
            float value2 = f.get(1).compute(inputs);
            ActionInterface action = null;
            if (value > value2){
                action = new VelocityFunc(new BasicAction(1, predator), predator, 5, 0);
            }
            else{
                action = new VelocityFunc(new BasicAction(2, predator), predator, 0.5f, 0.02f);
            }
            executor.addAction(action);
        }
    }

    @Override
    public void setEntityByName(String name, List<Function> trees) {
        Predator toUpdate = null;
        for(Predator predator: predatorList){
            String predatorName = "Predator" + predator.getId();
            if(predatorName.equals(name)){
                toUpdate = predator;
            }
        }
        predatorDecisionFunctions.put(toUpdate, trees);
        super.setDecisionFunctions(toUpdate, trees);
    }
}
