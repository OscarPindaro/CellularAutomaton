import Model.Genetic.Chromosome;
import Model.Genetic.CrossoverUnit;
import com.sun.org.apache.xpath.internal.operations.Variable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CrossoverUnitTest {

    CrossoverUnit crossoverUnit;
    Chromosome chromosome1;
    Chromosome chromosome2;
    List<Float> variables;

    @BeforeEach
    public void createUnit(){
        variables = new ArrayList<>(4);
        variables.add(0f);
        variables.add(3f);
        variables.add(-7f);
        crossoverUnit = new CrossoverUnit();
        chromosome1 = new Chromosome(variables);
        chromosome2 = new Chromosome(variables);
    }

    @Test
    public void testCrossover(){
        assertDoesNotThrow(()-> crossoverUnit.crossover(chromosome1,chromosome2));
    }


    public void testCrossoverPrint(){
        Chromosome[] returned = crossoverUnit.crossover(chromosome1,chromosome2);
        System.out.println("Chromosome1 " + chromosome1.getRoot());
        System.out.println("Chromosome2 " + chromosome2.getRoot());

        System.out.println("Children1 " + returned[0].getRoot());
        System.out.println("Children2 " + returned[1].getRoot());
    }
}
