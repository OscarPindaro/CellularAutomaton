package model.genetic;

import java.util.*;

public abstract class Node implements Function {

    protected final int MAX_CONSTANT = 100;

    protected int ninputs;
    protected  Node rightChildren;
    protected  Node leftChildren;

    protected static Random generator = new Random();

    protected final static float HIGHERCONST = 100;

    protected static String[] operations = {"add" , "sub", "mul"};

    public Node(){

    }
    public Node(int ninputs){
        this.ninputs = ninputs;
    }

    @Override
    public abstract float compute(List<Float> inputs);

    public static Node createRandomTree(int ninputs){
        return new OperationNode(operations[generator.nextInt(operations.length)], ninputs);
    }

    public abstract int numberOfNodes();

    private Node getFather(Node child){
        Queue<Node> queue = new LinkedList();
        Node currentNode = null;

        //the children is the root, so it doesn't have a father
        if(child == this)
            return null;

        queue.add(this);

        while (!queue.isEmpty()){
            currentNode = queue.remove();
            if(currentNode.rightChildren == child || currentNode.leftChildren == child){
                return currentNode;
            }
            if (currentNode.leftChildren != null)
                queue.add(currentNode.leftChildren);
            if (currentNode.rightChildren != null)
                queue.add(currentNode.rightChildren);

        }

        throw new RuntimeException("No such node inside this tree");
    }

    public Node getNode(int i){
        Queue<Node> queue = new LinkedList();
        int iterations = 0;
        Node currentNode = null;
        queue.add(this);

        while (!queue.isEmpty()){
            currentNode = queue.remove();
            if (iterations == i)
                return currentNode;
            iterations++;
            if(currentNode.leftChildren != null)
                queue.add(currentNode.leftChildren);
            if(currentNode.rightChildren != null)
                queue.add(currentNode.rightChildren);
        }
        return currentNode;
    }

    public abstract Node copyTree(int ninputs);

    public abstract String toStringPython();
}
