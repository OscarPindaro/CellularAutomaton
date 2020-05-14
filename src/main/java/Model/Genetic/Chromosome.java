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
    public Chromosome(){
        root = Node.createRandomTree(null);
    }

    public Chromosome(Chromosome toCopy){
        this.root = toCopy.root.copyTree(new ArrayList<>(null));
    }

    public void setVariables(List<Float> variables){
        root.propagateVariables(variables);
    }


    public Chromosome mutate(){
        Random generator = new Random();

        int position = generator.nextInt(root.numberOfNodes());


        return null;
    }

}
