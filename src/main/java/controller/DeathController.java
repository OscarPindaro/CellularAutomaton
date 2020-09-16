package controller;

import model.Model;
import model.interfaces.energy.EnergyObserver;
import model.interfaces.energy.EnergyDependent;


/**
 * This controller checks if the cells are dying. If they are, then they are kept in the model, but marked as "dead"
 * In this way, the checks on nearestNeighbours or on eating will not involve anymore the dead cell.
 * Maybe also the movement controller should be an observer of this event, since it should remove (and in the future add)
 * the dead cell
 */
public class DeathController implements EnergyObserver {

    public DeathController(){

    }

    /**
     * This implementation checks if the cell has 0 energy
     * @param e
     */
    @Override
    public void checkEnergy(EnergyDependent e) {
        if (e.getEnergy() <= 0){
            e.setDeath(true);
        }
    }
}
