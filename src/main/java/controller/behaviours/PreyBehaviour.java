package controller.behaviours;

import controller.action.*;
import model.Model;
import model.entity.Predator;
import model.entity.Prey;
import model.genetic.Function;
import model.genetic.Node;

import java.util.*;
import java.util.stream.Collectors;

public class PreyBehaviour extends AbstractBehaviour {

    private final List<Prey> preyList = new LinkedList<>();

    private final Map<Prey, List<Function>> preyDecisionFunctions;



    //parameters for python and the creation of the trees
    //numberOfInputs defined in the super class
    //numberOfActions needs to be coded in some way, for now, the numberOfActions are hardcoded in the class
    private static final String SPEC_FILE = "specificationFiles/prey.json";
    private final int  N_OF_ACTIONS_CONST = 4;


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
        //only alive cells can make decisions
        List<Prey> alivePreys = preyDecisionFunctions.keySet().stream().filter(p -> !p.isDead()).collect(Collectors.toList());
        for(Prey prey: alivePreys){
            List<Function> f = preyDecisionFunctions.get(prey);
            buildInputs(prey);
            int indexMaxFunction = getIndexHighestFunction(prey);
            ActionInterface action = null;
            switch (indexMaxFunction){
                case 0:
                    action = new VelocityFunc(new BasicAction(1, prey), prey, 3, 0);
                    break;
                case 1:
                    action = new VelocityFunc(new BasicAction(1, prey), prey, 0.5f, 0.02f);
                    break;
                case 2:
                    action = new VelocityFunc(new BasicAction(1, prey), prey, 0.5f, -0.02f);
                    break;
                case 3:
                    action = new RewardFunc(new VelocityFunc(new BasicAction(0, prey), prey, 0.000001f,0),
                            prey,2);
                    break;
                default:
                    throw new RuntimeException("Error with the index calculated for the actions");
            }
            executor.addAction(action);
        }
    }

    private void buildInputs(Prey prey){
        List<Float> inputs = entityInputs.get(prey);
        float x = prey.getPosition().x;
        float y = prey.getPosition().y;
        float memory = lastAction.get(prey);
        Predator nearestPredator = getNearestPredator(prey);
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
    private Predator getNearestPredator(Prey prey){
        List<Predator> predators = model.getAlivePredators();
        Predator nearest = null;
        if (predators.size() > 0){
            nearest = predators.get(0);
            double dist = prey.getPosition().dist(predators.get(0).getPosition());
            for(Predator predator: predators){
                double newDist = prey.getPosition().dist(predator.getPosition());
                if( newDist< dist){
                    nearest = predator;
                    dist = newDist;
                }
            }
        }
        return nearest;
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
