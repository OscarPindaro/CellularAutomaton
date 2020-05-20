import model.entity.Entity;
import model.entity.Predator;
import model.entity.Prey;
import model.genetic.Individual;
import model.genetic.Population;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationTest {

    Population population;

    @BeforeEach
    public void createPopulation(){
        List<Entity> list = new LinkedList<>();
        list.add(new Prey());
        list.add(new Prey());

        population = new Population(list);
    }

    @Test
    public void testGetIndividual(){
        assertDoesNotThrow(() ->population.getIndividual(0));

        assertEquals(population.getIndividual(0), population.getIndividual(0));
        assertSame(population.getIndividual(0), population.getIndividual(0));

        assertNotEquals(population.getIndividual(0), population.getIndividual(1));
        assertNotSame(population.getIndividual(0), population.getIndividual(1));
    }

    @Test
    public void testSelectIndividual(){
        assertTrue(()-> population.getIndividuals().contains(population.selectIndividual()));
    }

    @Test
    public void testMaxFitness(){
        List<Individual> individuals = population.getIndividuals();
        float max = individuals.get(0).fitness();
        for(Individual individual: individuals){
            if(individual.fitness() > max)
                max = individual.fitness();
        }
        assertEquals(max, population.maxFitness());
    }
}
