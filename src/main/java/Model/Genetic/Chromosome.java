package Model.Genetic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

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
        throw new NotImplementedException();
    }

}
