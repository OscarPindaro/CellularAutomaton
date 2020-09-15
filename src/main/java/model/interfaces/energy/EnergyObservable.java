package model.interfaces.energy;

import model.interfaces.cinematic.PositionBoundaryObserver;

public interface EnergyObservable {

    void attach(PositionBoundaryObserver observer);

    void remove(PositionBoundaryObserver observer);

    void notifyEnergyChange();
}
