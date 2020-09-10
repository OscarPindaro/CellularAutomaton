package controller.behaviours;

import controller.action.*;
import model.Model;
import model.entity.Prey;
import model.genetic.Function;
import model.genetic.Node;

import java.util.*;

public class PreyBehaviour implements EntityBehaviour {

    final List<Prey> preyList = new LinkedList<>();

    final Map<Prey, List<Function>> decisionFunctions;

    final Model model;

    public PreyBehaviour(Model model){
        this.model = model;
        decisionFunctions = new HashMap<>();
    }

    public void addPreys(List<Prey> preys, int ninputs){
        preyList.addAll(preys);
        for(Prey prey: preyList){
            decisionFunctions.put(prey, Node.createRandomTree(ninputs));
        }
    }

    public Function getDecisionFunction(Prey prey){
        return decisionFunctions.get(prey);
    }

    public void setDecisionFunction(Prey prey, Node node){
        decisionFunctions.put(prey, node);
    }

    @Override
    public void makeDecisions(ActionExecutorInterface executor) {
        for(Prey prey: decisionFunctions.keySet()){
            Function f = decisionFunctions.get(prey);
            float x = prey.getPosition().x;
            float y = prey.getPosition().y;
            List<Float> inputs= new ArrayList<>(2);
            inputs.add(x);
            inputs.add(y);
            float value = f.compute(inputs);
            ActionInterface action = null;
            if (value > 0){
                action = new VelocityFunc(new BasicAction(1, prey), prey, 2, -0.03f);
            }
            else{
                action = new VelocityFunc(new BasicAction(1, prey), prey, 1, 0.05f);
            }
            executor.addAction(action);
        }
    }

    @Override
    public List<List<Function>> getAllDecisionFunctions() {
        List<List<Function>>functionList = new LinkedList<>();
        for(List<Function> functions: decisionFunctions.values() ){

        }
    }
}
