package model.interfaces;

public interface EnergyDependent {

    void increaseEnergy(float toAdd);

    void decreaseEnergy(float toSub);

    float getEnergy();
}
