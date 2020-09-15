package model.interfaces.energy;

public interface EnergyDependent {

    void increaseEnergy(float toAdd);

    void decreaseEnergy(float toSub);

    float getEnergy();

    void setEnergy(float newEnergy);
}
