package controller.action;

import model.entity.Entity;
import model.interfaces.EnergyDependent;

public class DamageFunc extends AdditionalFunctionality {

    private final float damage;
    private final EnergyDependent target;

    public DamageFunc(ActionInterface action, EnergyDependent target, float damage){
        this.action = action;
        this.target = target;
        this.damage = damage;

    }


    @Override
    public void perform() {
        action.perform();
        target.decreaseEnergy(damage);
    }
}
