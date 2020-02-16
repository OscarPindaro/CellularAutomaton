package Model.Genetic;

import java.util.List;

public class Chromosome {

    private Node root;

    public Chromosome(Chromosome chromosome){
        root = Node.copySubTree(chromosome.root);
    }

    public Chromosome(List<Integer> variables){
        root = new Node(variables);
    }

    public void setVariables(List<Integer> variables){
        root.setVariables(variables);
    }
}
