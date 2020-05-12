package Model.Genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Node {

    protected List<Float> variables;

    protected final int MAX_CONSTANT = 100;

    protected Node rightChildren;
    protected Node leftChildren;

    protected static Random generator = new Random();

    protected final static float HIGHERCONST = 100;

    protected static String[] operations = {"add" , "sub", "mult"};

    public Node(){}

    public Node(List<Float> variables){
        this.variables = new ArrayList<>(variables);
    }

    public abstract float getValue();

    public static Node createRandomCromosome(List<Float> variables){
        return new OperationNode(operations[generator.nextInt(operations.length)], variables);
    }
}
