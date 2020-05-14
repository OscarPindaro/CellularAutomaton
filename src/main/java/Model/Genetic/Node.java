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

    public static List<Float> copyVariables(List<Float> variables){
        List<Float> newVar = new ArrayList<>(variables.size());
        for(Float var: variables)
            newVar.add(new Float(var));

        return newVar;

    }

    public abstract void propagateVariables(List<Float> variables);

    public abstract int numberOfNodes();

    /**
     * Return a new tree with the new node at the specified position. The structure of the tree may have changed.
     * The list of variables is the same of the old tree.
     * @param i
     * @return
     */
    public Node setNode(Node node, int i){

        List<Float> newVar = new ArrayList<>(variables.size());

        for(Float var: variables)
                newVar.add(new Float(var));

        Node newRoot = this.copyTree(newVar);

        Node toSubstitute = newRoot.getNode(i);

        Node father = newRoot.getFather(toSubstitute);
        if (father == null)
            return node;

        if (toSubstitute == father.leftChildren)
            father.leftChildren = node;

        if(toSubstitute == father.rightChildren)
            father.rightChildren = node;

        return newRoot;
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
