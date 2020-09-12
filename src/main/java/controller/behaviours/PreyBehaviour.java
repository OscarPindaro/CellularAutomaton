package controller.behaviours;

import controller.action.*;
import model.Model;
import model.entity.Prey;
import model.genetic.Function;
import model.genetic.Node;

import java.util.*;

public class PreyBehaviour extends AbstractBehaviour {

    private final List<Prey> preyList = new LinkedList<>();

    private final Map<Prey, List<Function>> preyDecisionFunctions;


    public PreyBehaviour(Model model, int nActions, int numberOfInputs){
        super(model, nActions, numberOfInputs);
        preyDecisionFunctions = new HashMap<>();
    }

    public void addPreys(List<Prey> preys){
        preyList.addAll(preys);
        for(Prey prey: preyList){
            List<Function> functions = new LinkedList<>();
            for(int i = 0; i < numberOfActions; i++){
                functions.add(Node.createRandomTree(this.numberOfInputs));
            }
            preyDecisionFunctions.put(prey, functions);
        }
        //add to super class
        for(Prey prey : preyList)
            super.setDecisionFunctions(prey, preyDecisionFunctions.get(prey));
    }

    public List<Function> getDecisionFunctions(Prey prey){
        return preyDecisionFunctions.get(prey);
    }

    public void setDecisionFunction(Prey prey, List<Function> functions){
        preyDecisionFunctions.put(prey, functions);
        super.setDecisionFunctions(prey, functions);
    }

    @Override
    public void makeDecisions(ActionExecutorInterface executor) {
        for(Prey prey: preyDecisionFunctions.keySet()){
            List<Function> f = preyDecisionFunctions.get(prey);
            float x = prey.getPosition().x;
            float y = prey.getPosition().y;
            List<Float> inputs= new ArrayList<>(2);
            inputs.add(x);
            inputs.add(y);
            float value = f.get(0).compute(inputs);
            float value2 = f.get(1).compute(inputs);
            ActionInterface action = null;
            if (value > value2){
                action = new VelocityFunc(new BasicAction(1, prey), prey, 2, -0.03f);
            }
            else{
                action = new VelocityFunc(new BasicAction(1, prey), prey, 1, 0.05f);
            }
            executor.addAction(action);
        }
    }

    @Override
    public void setEntityByName(String name, List<Function> trees) {
        Prey toUpdate = null;
        for(Prey prey: preyList){
            String preyName = "Prey" + prey.getId();
            if(preyName.equals(name)){
                toUpdate = prey;
            }
        }
        preyDecisionFunctions.put(toUpdate, trees);
        super.setDecisionFunctions(toUpdate, trees);
    }
}
