package Model.Genetic;

public class ConstantNode  extends Node {

    private final float constantValue;

    public ConstantNode(float constant){
        constantValue = constant;
        leftChildren = null;
        rightChildren = null;
    }

    @Override
    public float getValue() {
        return constantValue;
    }

    @Override
    public String toString() {
        return ""+ constantValue;
    }
}
