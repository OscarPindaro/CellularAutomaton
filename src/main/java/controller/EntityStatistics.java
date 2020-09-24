package controller;

import model.entity.Entity;
import model.interfaces.cinematic.Cinematic;
import model.interfaces.cinematic.PositionObserver;
import model.interfaces.energy.EnergyDependent;
import model.interfaces.energy.EnergyObserver;
import processing.core.PVector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityStatistics<T> implements PositionObserver, EnergyObserver {

    private final Map<T, Map<String, Float>> statistics = new HashMap<>();

    private final Map<Cinematic, PVector> lastPosition = new HashMap<>();

    private final String DISTANCE_KEY = "distanceCovered";
    private final String ENERGY = "energy";
    private final String MAX_ENERGY = "maxEnergy";
    private final String MIN_ENERGY = "minEnergy";
    private final String MEAN_ENERGY = "meanEnergy";

    public void addEntity(T e){
        if (!statistics.containsKey(e)){
            Map<String, Float> statDict = new HashMap<>();
            statistics.put(e, statDict);
        }
    }

    public void addAllEntities(Collection<T> entities){
        for(T e: entities)
            addEntity(e);
    }

    public Map<String, Float> getStatistics(T e){
        return statistics.get(e);
    }

    public void remove(T e){
        statistics.remove(e);
    }

    public void resetStatistics(){
        statistics.replaceAll((t, v) -> new HashMap<>());
        for(Cinematic c: lastPosition.keySet())
            lastPosition.remove(c);

    }

    /********* POSITION OBSERVER ******************/
    /**
     * Every iteration check the last position and the new position and adds the distance bw the 2 to the total space
     * covered
     *TODO the movement controller now keeps updating the speed of the cell. I need a way to avoid this, ethier by setting
     * to zero the movement in the cell or by adding to the movement controller a way to understand if the cinmeatic object is
     * dead
     * @param cinematic
     */
    @Override
    public void checkPosition(Cinematic cinematic) {
        if( !statistics.containsKey(cinematic))
            throw new RuntimeException("This object is not registered in the statistic module");
    checkDistanceCovered(cinematic);
    }

    private void checkDistanceCovered(Cinematic cinematic){
        if(!lastPosition.containsKey(cinematic)){
            lastPosition.put(cinematic, cinematic.getPosition().copy());
            statistics.get(cinematic).put(DISTANCE_KEY, 0f);
            return;
        }
        float distance = statistics.get(cinematic).get(DISTANCE_KEY);
        distance += PVector.dist(cinematic.getPosition(), lastPosition.get(cinematic));
        // update of the distance covered
        statistics.get(cinematic).put(DISTANCE_KEY, distance);
        //update of the position
        lastPosition.put(cinematic, cinematic.getPosition().copy());
    }

    /****************** ENERGY OBSERVER *************/
    /**
     * updares the current energy, the max energy and other parameters for the cell
     * @param e
     */
    @Override
    public void checkEnergy(EnergyDependent e) {
        if( !statistics.containsKey(e) )
            throw new RuntimeException("This object is not registered in the statistic module");
        if( !e.isDead() ){
            checkCurrentEnergy(e);
            checkMaxEnergy(e);
            checkMinEnergy(e);
            checkMeanEnergy(e);
        }
    }

    private void checkCurrentEnergy(EnergyDependent e){
        Map<String, Float> dict = statistics.get(e);
        dict.put(ENERGY, e.getEnergy());
    }

    private void checkMaxEnergy(EnergyDependent e){
        Map<String, Float> dict = statistics.get(e);
        if(!dict.containsKey(MAX_ENERGY)){
            dict.put(MAX_ENERGY, e.getEnergy());
            return;
        }
        if(dict.get(MAX_ENERGY) < e.getEnergy()){
            dict.put(MAX_ENERGY, e.getEnergy());
        }
    }

    private void checkMinEnergy(EnergyDependent e){
        Map<String, Float> dict = statistics.get(e);
        if(!dict.containsKey(MIN_ENERGY)){
            dict.put(MIN_ENERGY, e.getEnergy());
            return;
        }
        if(dict.get(MIN_ENERGY) > e.getEnergy()){
            dict.put(MIN_ENERGY, e.getEnergy());
        }
    }

    private void checkMeanEnergy(EnergyDependent e){
        Map<String, Float> dict = statistics.get(e);
        if(!dict.containsKey(MEAN_ENERGY)){
            dict.put(MEAN_ENERGY, e.getEnergy());
            dict.put("n", 1f);
            return;
        }
        float oldMean = dict.get(MEAN_ENERGY);
        float n = dict.get("n");
        n = n+1;
        float newMean = (oldMean*n + e.getEnergy())/n;
        dict.put(MEAN_ENERGY, newMean);
        dict.put("n", n);
    }

}
