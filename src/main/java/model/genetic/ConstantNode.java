package model.genetic;

import java.util.List;

public class ConstantNode  extends Node {

    private final float constantValue;

    public ConstantNode(float constant){
        constantValue = constant;
        leftChildren = null;
        rightChildren = null;
    }

    @Override
    public float compute(List<Float> inputs) {
        return constantValue;
    }

    @Override
    public Node copyTree(List<Float> variables) {
        return new ConstantNode(constantValue);
    }

    @Override
    public int numberOfNodes() {
        return 1;
    }

    @Override
    public String toString() {
        return ""+ constantValue;
    }
}
