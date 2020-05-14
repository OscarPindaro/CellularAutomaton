package Model.Genetic;


import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Random;

public class CrossoverUnit {

    /**
     * return 2 new chromosomes with new trees.
     * @param mother
     * @param father
     * @return
     */
    public Chromosome[]  crossover(Chromosome mother, Chromosome father){

        Random generator = new Random();

        Node rootMother = mother.getRoot();
        Node rootFather = father.getRoot();
//        System.out.println("rootMother " + rootMother);
//        System.out.println("rootFather " + rootFather);

        int sizeMother = rootMother.numberOfNodes();
        int sizeFather = rootFather.numberOfNodes();
        System.out.println("sizeMother " + sizeMother + " sizeFather " +sizeFather );

        int posMother = generator.nextInt(sizeMother);
        int posFather = generator.nextInt(sizeFather);
        System.out.println("posMother " + posMother + " posFather " +posFather );

        Node crossMother = rootMother.getNode(posMother);
        Node crossFather = rootFather.getNode(posFather);
        ;

        Node rootChild1 = rootMother.setNode(crossFather, posMother);
        Node rootChild2 = rootFather.setNode(crossMother, posFather);

        Chromosome[] toRet = new Chromosome[2];
        toRet[0] = new Chromosome(rootChild1);
        toRet[1] = new Chromosome(rootChild2);

        return toRet;
    }
}
