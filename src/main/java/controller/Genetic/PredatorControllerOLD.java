package controller.Genetic;

import controller.action.*;
import model.Model;
import model.entity.Prey;
import model.genetic.Chromosome;
import model.genetic.Individual;
import model.interfaces.EnergyDependent;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredatorControllerOLD {

    private List<Individual> predators;
    private Model model;
    private final ActionExecutorInterface executor;
    private int N_OF_ACTIONS = 5;
    private int N_OF_INPUTS = 3;
    private int VISION_RADIUS = 100;


    Map<Individual, Prey> targets = new HashMap<>();

    Map<Individual, Integer> lastActionHistory = new HashMap<>();

    private float DELTA_ANGLE = 0.05f;
    private final float WALKSPEED = 3;
    private final float RUNSPEED = 7;
    private final float BITE_RANGE = 40;

    public PredatorControllerOLD(Model model, ActionExecutorInterface executor, List<Individual> individuals){
        this.predators = individuals;
        this.executor = executor;
        this.model = model;
        for(Individual individual : individuals)
            lastActionHistory.put(individual, 0);
    }




    public void run(){

        for(Individual predator: predators){
            List<Float> variables = new ArrayList<>(N_OF_INPUTS+ N_OF_ACTIONS);
            chooseTarget(variables, predator);
            updateEnergy(variables, predator);
            updateMemory(variables, lastActionHistory.get(predator));
            Chromosome chromosome = predator.getChromosome();
            chromosome.setVariables(variables);
            float chromosomeValue = chromosome.getValue();
            int actionNumber = Math.abs( ((int) chromosomeValue) % N_OF_ACTIONS);
            lastActionHistory.put(predator, actionNumber);
            ActionInterface action = chooseAction(actionNumber, predator);
            executor.addAction(action);
        }


    }

    /**
     * Variables è vuoto
     *
     * @param variables
     *
     */
    private void chooseTarget(List<Float> variables, Individual predator){
        PVector myPos = predator.getPosition();
        List<Prey> preys = model.getPreys();
        float min = getDistance(preys.get(0).getPosition(), myPos);
        Prey minPrey = preys.get(0);
        for(Prey prey : preys){
            PVector position = prey.getPosition();
            float possMin = getDistance(position, myPos);
            if( possMin < min ){
                min = possMin;
                minPrey = prey;
            }
        }
        targets.put(predator, minPrey);
        PVector relativePosition = minPrey.getPosition().sub(myPos);
        variables.add(relativePosition.x);
        variables.add(relativePosition.y);
    }

    private float getDistance(PVector v1, PVector v2){
        return v1.sub(v2).mag();
    }


    private void updateEnergy(List<Float> variables, EnergyDependent predator){
        variables.add(predator.getEnergy());
    }

    private void updateMemory(List<Float> variables, int action){
        for(int i = 0; i < N_OF_ACTIONS; i++){
            if(action == i) variables.add(1f);
            else variables.add(0f);
        }

    }


    public ActionInterface chooseAction(int actionNumber, Individual predator){
        switch (actionNumber){
            case 0:
                return new VelocityFunc(new BasicAction(5, predator), predator.getCinematic(), WALKSPEED, 0);
            case 1:
                return new VelocityFunc(new BasicAction(10, predator), predator.getCinematic(), RUNSPEED, 0);
            case 2:
                return new VelocityFunc(new BasicAction(1, predator), predator.getCinematic(), predator.getCinematic().getSpeed().mag(), DELTA_ANGLE);
            case 3:
                return new VelocityFunc(new BasicAction(1, predator), predator.getCinematic(), predator.getCinematic().getSpeed().mag(), -DELTA_ANGLE);
            case 4:
                float reward = 0;
                if( getDistance(predator.getPosition(), targets.get(predator).getPosition()) < BITE_RANGE){
                    reward = 50;
                }
                return new RewardFunc(new BasicAction(3, predator), predator, reward);
            default:
                throw new RuntimeException("AAAAAAAAAAAAAAAAA c'è qualcosa che non va nel calcolo delle azioni");
        }
    }
}
