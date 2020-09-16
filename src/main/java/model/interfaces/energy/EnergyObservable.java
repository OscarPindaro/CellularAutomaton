package model.interfaces.energy;

import model.interfaces.cinematic.PositionBoundaryObserver;

public interface EnergyObservable {

    void attach(EnergyObserver observer);

    void remove(EnergyObserver observer);

    void notifyEnergyChange();
}
