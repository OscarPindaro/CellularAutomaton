package Model.Genetic;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class OldNode {
    private List<Float> variables;

    private final int MAX_CONSTANT = 100;

    private static String[] operations = {"add" , "sub", "mult"};
    private String operation;
    private float constant;
    private int variable;

    private OldNode leftChildren;
    private OldNode rightChildren;

    public OldNode(String operation, List<Float> variables){
        this.operation = operation;
        variable = -1;
        this.variables = variables;
    }

    public OldNode(float constant, List<Float> variables){
        this.operation = null;
        this.constant = constant;
        this.variable = -1;
        leftChildren = null;
        rightChildren = null;
        this.variables = variables;
    }

    public OldNode(int variable, List<Float> variables){
        this.operation = null;
        this.constant = -1;
        this.variable = variable;
        leftChildren = null;
        rightChildren = null;
        this.variables = variables;
    }

    public OldNode(List<Float> variables){
        if( Math.random() <0.33){
            this.operation = operations[(int)(Math.random()*operations.length)];
            variable = -1;
            leftChildren = new OldNode(variables);
            rightChildren = new OldNode(variables);
        }
        else if (Math.random() >= 0.66){
            this.operation = null;
            this.constant = new Random().nextFloat()*MAX_CONSTANT;
            this.variable = -1;
            leftChildren = null;
            rightChildren = null;
        }
        else{
            variable = new Random().nextInt(variables.size());
            this.operation = null;
            this.constant = -1;
            rightChildren = null;
            leftChildren = null;
        }
        this.variables = variables;
    }

    public OldNode(String operation, float constant, int variable){
        this.operation = operation;
        this.constant = constant;
        this.variable = variable;

    }


    public static OldNode copySubTree(OldNode origin){
        OldNode root = new OldNode(null, -1, -1);
        root.operation = origin.operation;
        root.variable = origin.variable;
        root.constant = origin.constant;
        if(origin.leftChildren != null){
            root.leftChildren = copySubTree(origin.leftChildren);
        }
        if(origin.rightChildren != null){
            root.rightChildren = copySubTree(origin.rightChildren);
        }

        return root;
    }

    public float getValue(){
        if(this.operation != null){
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
        else if (this.variable>= 0){
            return variables.get(this.variable);
        }
        else{
            return this.constant;
        }
    }

    public void setVariables(List<Float> variables){
        this.variables = variables;
        if(leftChildren != null){
            leftChildren.setVariables(variables);
        }
        if (rightChildren != null){
            rightChildren.setVariables(variables);
        }
    }

    public List<OldNode> toList(){
        if(this.operation != null){
            List<OldNode> listOfNodes = new LinkedList<>();
            listOfNodes.add(this);
            listOfNodes.addAll(leftChildren.toList());
            listOfNodes.addAll(rightChildren.toList());
            return listOfNodes;
        }
        else{
            List<OldNode> list = new LinkedList<>();
            list.add(this);
            return list;
        }
    }

    public void setRandomOperation(){
        this.operation = operations[new Random().nextInt(operations.length)];
        leftChildren = new OldNode(variables);
        rightChildren = new OldNode(variables);
        this.variable = -1;
        this.constant = -1;
    }

    public void setVariable(){
        this.operation = null;
        this.variable = new Random().nextInt(variables.size());
        this.constant = -1;
        leftChildren = null;
        rightChildren = null;
    }

    public void setConstant(){
        this.operation = null;
        this.variable = -1;
        this.constant = new Random().nextFloat()*MAX_CONSTANT;
        leftChildren = null;
        rightChildren = null;
    }

    public static OldNode getFather(OldNode tree, OldNode child){
        OldNode current;
        current = tree;
        if (current == child){
            return null;
        }
        if (current.rightChildren == null && current.leftChildren== null){
            return null;
        }

        if(current.rightChildren == child){
            return current;
        }
        if(current.leftChildren == child){
            return current;
        }

        OldNode left = getFather(current.leftChildren, child);
        OldNode right = getFather(current.rightChildren, child);

        if (right == null){
            return left;
        }
        else{
            return right;
        }
    }

    public boolean isLeftChild(OldNode child){
        return child == leftChildren;
    }

    public boolean isRightChild(OldNode child){
        return child == rightChildren;
    }

    public void setLeftChildren(OldNode newChild){
        leftChildren = newChild;
    }

    public void setRightChildren(OldNode newChild){
        rightChildren = newChild;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (operation != null){
            builder.append(leftChildren);
            builder.append(rightChildren);
            switch (operation) {
                case "add":
                    builder.append(" +");
                    break;
                case "sub":
                    builder.append(" -");
                    break;
                case "mult":
                    builder.append(" *");
                    break;
                default:
                    throw new RuntimeException("Wrong operation");
            }
        }
        else if(variable>=0){
            builder.append(" x" + variable);
        }
        else{
            builder.append(" " + (int) constant);
        }
        return builder.toString();
    }
}
