package controller.behaviours;

import controller.action.*;
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
    private final int N_OF_ACTIONS_CONST = 4;

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
            buildInputs(predator);
            int indexMaxFunction = getIndexHighestFunction(predator);
            ActionInterface action = null;
            Prey prey = getNearestPrey(predator);
            switch (indexMaxFunction){
                case 0:
                    action = new VelocityFunc(new BasicAction(2, predator), predator, 1f, prey.getPosition());
                    break;
                case 1:
                    action = new VelocityFunc(new BasicAction(4, predator), predator, 0.8f, 0.04f);
                    break;
                case 2:
                    action = new VelocityFunc(new BasicAction(4, predator), predator, 0.8f, -0.04f);
                    break;
                case 3:
                    float reward = 0;
                    float damage = 0;
                    if(prey != null){
                        if (prey.getPosition().dist(predator.getPosition() )< predator.getEntityRadius()){
                            reward = 10;
                            damage = 10;
                        }
                    }
                    action = new DamageFunc(new RewardFunc(new VelocityFunc(new BasicAction(2, predator), predator, 0.5f, prey.getPosition() ),
                            predator,reward), prey, damage);
                    break;
                default:
                    throw new RuntimeException("Error with the index calculated for the actions");
            }
            executor.addAction(action);
        }
    }

    private void buildInputs(Predator predator){
        List<Float> inputs = entityInputs.get(predator);
        float x = predator.getPosition().x;
        float y = predator.getPosition().y;
        float memory = lastAction.get(predator);
        Prey nearestPrey = getNearestPrey(predator);
        float xPrey = -1;
        float yPrey = -1;
        if (nearestPrey != null){
            xPrey = nearestPrey.getPosition().x;
            yPrey = nearestPrey.getPosition().y;
        }
        inputs.set(0, x);
        inputs.set(1, y);
        inputs.set(2, memory);
        inputs.set(3, xPrey);
        inputs.set(4, yPrey);
        //this line may be overkill
        entityInputs.put(predator, inputs);
    }

    private Prey getNearestPrey(Predator predator){
        List<Prey> preys = model.getAlivePreys();
        Prey nearest = null;
        if (preys.size() > 0){
            nearest = preys.get(0);
            double dist = predator.getPosition().dist(preys.get(0).getPosition());
            for(Prey prey: preys){
                double newDist = predator.getPosition().dist(prey.getPosition());
                if( newDist< dist){
                    nearest = prey;
                    dist = newDist;
                }
            }
        }
        return nearest;
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
