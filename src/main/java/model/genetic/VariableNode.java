package model.genetic;

import java.util.List;

public class VariableNode extends Node {

    private int selectedVariable;

    public VariableNode(int index, int ninputs){
        super(ninputs);
        if(index >= ninputs) throw new RuntimeException("Index too big for the list of variables");

        selectedVariable = index;

        leftChildren = null;
        rightChildren = null;
    }

    @Override
    public float compute(List<Float> input) {
        return input.get(selectedVariable);
    }

    @Override
    public int numberOfNodes() {
        return 1;
    }

    @Override
    public Node copyTree(int ninputs) {
        return new VariableNode(selectedVariable, ninputs);
    }

    @Override
    public String toString() {
        return "ARG" + selectedVariable;
    }
}
