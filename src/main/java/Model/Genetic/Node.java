package Model.Genetic;

import java.util.List;
import java.util.Random;

public class Node {
    private List<Integer> variables;

    private static String[] operations = {"add" , "sub", "mult"};
    private String operation;
    private float constant;
    private int variable;

    private Node leftChildren;
    private Node rightChildren;

    public Node(String operation, List<Integer> variables){
        this.operation = operation;
        variable = -1;
        this.variables = variables;
    }

    public Node(float constant, List<Integer> variables){
        this.operation = null;
        this.constant = constant;
        this.variable = -1;
        leftChildren = null;
        rightChildren = null;
        this.variables = variables;
    }

    public Node(int variable, List<Integer> variables){
        this.operation = null;
        this.constant = -1;
        this.variable = variable;
        leftChildren = null;
        rightChildren = null;
        this.variables = variables;
    }

    public Node(List<Integer> variables){
        if( Math.random() <0.33){
            this.operation = operations[(int)(Math.random()*operations.length)];
            variable = -1;
            leftChildren = new Node(variables);
            rightChildren = new Node(variables);
        }
        else if (Math.random() >= 0.66){
            this.operation = null;
            this.constant = new Random().nextFloat()*100;
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

    public Node(String operation, float constant, int variable){
        this.operation = operation;
        this.constant = constant;
        this.variable = variable;

    }


    public static Node copySubTree(Node origin){
        Node root = new Node(null, -1, -1);
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

    public void setVariables(List<Integer> variables){
        this.variables = variables;
        if(leftChildren != null){
            leftChildren.setVariables(variables);
        }
        if (rightChildren != null){
            rightChildren.setVariables(variables);
        }
    }
}
