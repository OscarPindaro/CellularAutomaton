package model.genetic;

import java.util.List;

public class OperationNode  extends Node{


    private final String operation;


    public OperationNode(String operation, int ninputs){
        super(ninputs);
        if(!checkOperation(operation)) throw new RuntimeException("No operation with this name");
        this.operation = operation;
        rightChildren = createChildren();
        leftChildren = createChildren();
    }

    public OperationNode(String operation, Node node1, Node node2, int ninputs){
        super(ninputs);
        if(!checkOperation(operation)) throw new RuntimeException("No operation with this name");
        this.operation = operation;
        rightChildren = node1;
        leftChildren = node2;
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
            return new VariableNode(generator.nextInt(ninputs), ninputs);
        }
        else if (probabilty > 0.67){
            return new ConstantNode(generator.nextFloat()*Node.HIGHERCONST);
        }
        else return new OperationNode(operations[generator.nextInt(operations.length)], ninputs);

    }


    @Override
    public float compute(List<Float> inputs) {
        switch (this.operation){
            case "add":
                return leftChildren.compute(inputs) + rightChildren.compute(inputs);
            case "sub":
                return leftChildren.compute(inputs) - rightChildren.compute(inputs);
            case "mul":
                return leftChildren.compute(inputs) * rightChildren.compute(inputs);
            default:
                throw new RuntimeException("uncompleted switch");
        }
    }

    @Override
    public Node copyTree(int ninputs) {
        OperationNode ret = new OperationNode(operation, ninputs);
        ret.rightChildren = rightChildren.copyTree(ninputs);
        ret.leftChildren = leftChildren.copyTree(ninputs);
        return ret;
    }

    @Override
    public int numberOfNodes() {
        return 1 + rightChildren.numberOfNodes() + leftChildren.numberOfNodes();
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
            case "mul":
                return ("*");
            default:
                throw new RuntimeException("Wrong operation");
        }
    }

    @Override
    public String toStringPython() {
        StringBuilder builder = new StringBuilder();
        builder.append(operation).append("(")
                .append(leftChildren.toStringPython()).append(", ").append(rightChildren.toStringPython())
                .append(")");
        return builder.toString();
    }
}
