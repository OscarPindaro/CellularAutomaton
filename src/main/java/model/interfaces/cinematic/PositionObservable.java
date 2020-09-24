package model.interfaces.cinematic;

public interface PositionObservable {

    void attach(PositionBoundaryObserver observer);

    void attach(PositionObserver observer);

    void remove(PositionBoundaryObserver observer);

    void remove(PositionObserver observer);

    void notifyPositionChange();

}
