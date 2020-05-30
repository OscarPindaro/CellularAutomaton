package model.genetic;

import java.util.List;

public class VariableNode extends Node {

    private int selectedVariable;

    public VariableNode(int index, List<Float> variables){
        super(variables);
        if(index >= variables.size()) throw new RuntimeException("Index too big for the list of variables");

        selectedVariable = index;

        leftChildren = null;
        rightChildren = null;
    }

    @Override
    public float getValue() {
        return variables.get(selectedVariable);
    }

    @Override
    public void propagateVariables(List<Float> variables) {
        this.variables = variables;
    }

    @Override
    public int numberOfNodes() {
        return 1;
    }

    @Override
    public Node copyTree(List<Float> variables) {
        return new VariableNode(selectedVariable, variables);
    }

    @Override
    public String toString() {
        return "x" + selectedVariable;
    }

}