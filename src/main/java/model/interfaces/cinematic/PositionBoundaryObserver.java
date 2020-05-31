package model.interfaces.cinematic;

import model.interfaces.cinematic.Cinematic;

public interface PositionBoundaryObserver {

    void checkBoundary(Cinematic cinematic, float radius);
}
