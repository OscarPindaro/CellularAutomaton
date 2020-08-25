package controller.behaviours;

import controller.action.ActionExecutor;
import model.Model;
import model.entity.Prey;
import model.genetic.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PreyBehaviour implements EntityBehaviour {

    final List<Prey> preyList = new LinkedList<>();

    final Map<Prey, Node> decisionFunctions;

    final Model model;

    public PreyBehaviour(Model model){
        this.model = model;
        decisionFunctions = new HashMap<>();
    }

    public void addPreys(List<Prey> preys){
        preyList.addAll(preys);
        for(Prey prey: preyList){
            decisionFunctions.put(prey, Node.createRandomTree());
        }
    }

    public void getDecisionFunction(Prey prey){
        Node decisor = decisionFunctions.get(prey);
        return decisor.copyTree();
    }

    public void setDecisionFunction(Prey prey, Node node){
        // link here preys with nodes? better to think about it
        // node.propagateVariables();
        decisionFunctions.put(prey, node);
    }

    @Override
    public void makeDecisions(ActionExecutor executor) {
        for(Prey prey : preyList){

        }
    }
}
