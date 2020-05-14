import Model.Genetic.Chromosome;
import com.sun.org.apache.xpath.internal.operations.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChromosomeTest {

    Chromosome chromosome;
    List<Float> variables;

    @BeforeEach
    public void createChromosome(){
        variables = new ArrayList<>(4);
        variables.add(0f);
        variables.add(1f);
        variables.add(34f);
        variables.add(-4f);

        chromosome = new Chromosome(variables);
    }

    @Test
    public void testMutation(){
        assertDoesNotThrow(()->chromosome.mutate());

        assertNotSame(chromosome, chromosome.mutate());

        assertNotNull(chromosome.mutate());

    }
}
