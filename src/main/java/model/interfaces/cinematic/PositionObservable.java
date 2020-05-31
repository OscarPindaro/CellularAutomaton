package model.interfaces.cinematic;

public interface PositionObservable {

    void attach(PositionBoundaryObserver observer);

    void remove(PositionBoundaryObserver observer);

    void notifyPositionChange();

}
