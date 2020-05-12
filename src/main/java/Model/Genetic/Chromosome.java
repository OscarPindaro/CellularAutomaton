package Model.Genetic;

import java.util.List;
import java.util.Random;

/**
 * immutable class
 */
public class Chromosome {

    private Node root;

    public Chromosome(Chromosome chromosome){
        root = Node.copySubTree(chromosome.root);
    }

    public Chromosome(List<Float> variables){
        root = new Node(variables);
    }

    public void setVariables(List<Float> variables){
        root.setVariables(variables);
    }

    public Chromosome mutate(){
        Chromosome newChromosome = new Chromosome(this);
        List<Node> list = newChromosome.root.toList();
        Node mutatedNode = list.get(new Random().nextInt(list.size()));
        if( Math.random() <= 0.33){
            mutatedNode.setRandomOperation();
        }
        else if ( Math.random() >= 0.66){
            mutatedNode.setVariable();
        }
        else{
            mutatedNode.setConstant();
        }

        return newChromosome;
    }

    public void setRoot(Node root){
        this.root = root;
    }

    public float computeFunction(){
        return root.getValue();
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
