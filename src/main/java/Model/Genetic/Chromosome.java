package Model.Genetic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromosome {

    private final Node root;

    /**
     * creates a random tree with no variables
     */
    public Chromosome(List<Float> variables){
        root = Node.createRandomTree(variables);
    }

    public Chromosome(Node root){
        this.root = root;
    }

    public Chromosome(Chromosome toCopy){
        this.root = toCopy.root.copyTree(new ArrayList<>(Node.copyVariables(toCopy.root.variables)));
    }

    public void setVariables(List<Float> variables){
        root.propagateVariables(variables);
    }


    /**
     * returns a copy!!!!
     * @return
     */
    public Node getRoot(){
        return root.copyTree(Node.copyVariables(root.variables));
    }


    public Chromosome mutate(){
        Random generator = new Random();

        int position = generator.nextInt(root.numberOfNodes());
        Node newNode = Node.createRandomTree(Node.copyVariables(root.variables));

        Node newRoot = root.setNode(newNode, position);

        Chromosome toRet = new Chromosome(newRoot);


        return toRet;
    }

}
