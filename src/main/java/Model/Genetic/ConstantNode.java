package Model.Genetic;

import java.util.List;

public class ConstantNode  extends Node {

    private final float constantValue;

    public ConstantNode(float constant, List<Float> variables){
        super(variables);
        constantValue = constant;
        leftChildren = null;
        rightChildren = null;
    }

    @Override
    public float getValue() {
        return constantValue;
    }

    @Override
    public void propagateVariables(List<Float> variables) {};

    @Override
    public Node copyTree(List<Float> variables) {
        return new ConstantNode(constantValue, variables);
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
