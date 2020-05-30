import controller.Genetic.GeneticAlghoritm;
import model.entity.Entity;
import model.entity.Prey;
import model.genetic.Individual;
import model.genetic.Population;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GeneticAlgTest {

    GeneticAlghoritm ga;

    @BeforeEach
    public void creationGeneticAlg(){
        ga = new GeneticAlghoritm();
    }

    @Test
    public void testEvolution(){
        List<Entity> entities= new LinkedList<>();
        entities.add(new Prey());
        entities.add(new Prey());

        Population population = new Population(entities);

        List<Individual> individuals = population.getIndividuals();

        assertNotSame(individuals.get(0).getChromosome(), ga.evolveChromosomes(population).get(0));


    }

}
