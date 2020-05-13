package Model.Genetic;

import java.util.*;

public abstract class Node {

    protected List<Float> variables;

    protected final int MAX_CONSTANT = 100;

    protected  Node rightChildren;
    protected  Node leftChildren;

    protected static Random generator = new Random();

    protected final static float HIGHERCONST = 100;

    protected static String[] operations = {"add" , "sub", "mult"};

    public Node(){}

    public Node(List<Float> variables){
        this.variables = new ArrayList<>(variables);
    }

    public abstract float getValue();

    public static Node createRandomTree(List<Float> variables){
        return new OperationNode(operations[generator.nextInt(operations.length)], variables);
    }

    public abstract void propagateVariables(List<Float> variables);

    public abstract int numberOfNodes();

    /**
     * Return a tree with the new node at the specified position. The structure of the tree may have changed.
     * @param i
     * @return
     */
    public Node setNode(Node node, int i){
        Node toSubstitute = getNode(i);
        if (toSubstitute == null)
            return node;

        Node father = getFather(toSubstitute);
        if (toSubstitute == father.leftChildren)
            father.leftChildren = node;

        if(toSubstitute == father.rightChildren)
            father.rightChildren = node;

        return this;
    }

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
        }
        return currentNode;
    }




    public abstract Node copyTree(List<Float> variables);
}
