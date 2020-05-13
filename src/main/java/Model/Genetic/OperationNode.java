package Model.Genetic;

import java.util.List;

public class OperationNode  extends Node{


    private final String operation;


    public OperationNode(String operation, List<Float> variables){
        super(variables);
        if(!checkOperation(operation)) throw new RuntimeException("No operation with this name");

        this.operation = operation;

        rightChildren = createChildren();
        leftChildren = createChildren();



    }

    private boolean checkOperation(String operation){
        for(int i = 0; i < operations.length; i++){
            if (operations[i].equals(operation))
                    return true;
        }
        return false;
    }

    private Node createChildren(){
        float probabilty =generator.nextFloat();

        if(probabilty < 0.33){
            return new VariableNode(generator.nextInt(variables.size()), variables);
        }
        else if (probabilty > 0.67){
            return new ConstantNode(generator.nextFloat()*Node.HIGHERCONST);
        }
        else return new OperationNode(operations[generator.nextInt(operations.length)], variables);

    }


    @Override
    public float getValue() {
        switch (this.operation){
            case "add":
                return leftChildren.getValue() + rightChildren.getValue();
            case "sub":
                return leftChildren.getValue() - rightChildren.getValue();
            case "mult":
                return leftChildren.getValue() * rightChildren.getValue();
            default:
                throw new RuntimeException("uncompleted switch");
        }
    }

    @Override
    public void propagateVariables(List<Float> variables) {
        this.variables = variables;
        rightChildren.propagateVariables(variables);
        leftChildren.propagateVariables(variables);
    }

    @Override
    public Node copyTree(List<Float> variables) {
        OperationNode ret = new OperationNode(operation, variables);
        ret.rightChildren = rightChildren.copyTree(variables);
        ret.leftChildren = leftChildren.copyTree(variables);
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("( ").append(leftChildren).append(" " + convertOperation(operation) + " ").append(rightChildren).append(" )");
        return  builder.toString();
    }

    private String convertOperation(String operation){
        switch (operation) {
            case "add":
                return "+";
            case "sub":
               return ("-");
            case "mult":
                return ("*");
            default:
                throw new RuntimeException("Wrong operation");
        }
    }


}
