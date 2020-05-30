package controller.action;

import model.entity.Entity;

public class DamageFunc extends AdditionalFunctionality {

    private Entity target;
    private float damage;


    public DamageFunc(ActionInterface action, Entity target, float damage){
        this.action = action;
        this.target = target;
        this.damage = damage;

    }


    @Override
    public void perform() {
        super.perform();
    }
}
