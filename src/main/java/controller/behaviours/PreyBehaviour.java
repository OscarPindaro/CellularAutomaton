package controller.behaviours;

import controller.action.*;
import model.Model;
import model.entity.Predator;
import model.entity.Prey;
import model.genetic.Function;
import model.genetic.Node;
import org.json.JSONObject;

import java.util.*;

public class PreyBehaviour extends AbstractBehaviour {

    private final List<Prey> preyList = new LinkedList<>();

    private final Map<Prey, List<Function>> preyDecisionFunctions;



    //parameters for python and the creation of the trees
    //numberOfInputs defined in the super class
    //numberOfActions needs to be coded in some way, for now, the numberOfActions are hardcoded in the class
    private static final String SPEC_FILE = "specificationFiles/prey.json";
    private final int  N_OF_ACTIONS_CONST = 2;


    public PreyBehaviour(Model model){
        super(model, SPEC_FILE);
        preyDecisionFunctions = new HashMap<>();
        this.numberOfActions = N_OF_ACTIONS_CONST;
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
            buildInputs(prey);
            float value = f.get(0).compute(inputs);
            float value2 = f.get(1).compute(inputs);
            ActionInterface action = null;
            if (value > value2){
                action = new VelocityFunc(new BasicAction(1, prey), prey, 5, 0);
            }
            else{
                action = new VelocityFunc(new BasicAction(2, prey), prey, 0.5f, 0.02f);
            }
            executor.addAction(action);
        }
    }

    private void buildInputs(Prey prey){
        List<Float> inputs = entityInputs.get(prey);
        float x = prey.getPosition().x;
        float y = prey.getPosition().y;
        float memory = lastAction.get(prey);
        Predator nearestPredator = getNearestPreadtor();
        float xPred = -1;
        float yPred = -1;
        if (nearestPredator != null){
            xPred = nearestPredator.getPosition().x;
            yPred = nearestPredator.getPosition().y;
        }
        inputs.set(0, x);
        inputs.set(1, y);
        inputs.set(2, memory);
        inputs.set(3, xPred);
        inputs.set(4, yPred);
        //this line may be overkill
        entityInputs.put(prey, inputs);
    }

    /**
     * returns the nearest predator to the given prey
     * @param prey
     * @return
     */
    private Predator getNearestPreadtor(Prey prey){
        List<Predator> predators = model.getPredators();
        if (predators.size() > 0){
            Predator nearest = predators.get(0);
            double dist = prey.getPosition().dist(predators.get(0).getPosition());
            for(Predator predator: predators){
                double newDist = prey.getPosition().dist(predator.getPosition());
                if( newDist< dist){
                    nearest = predator;
                    dist = newDist;
                }
                return predator;
            }
        }
        else return null;
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
