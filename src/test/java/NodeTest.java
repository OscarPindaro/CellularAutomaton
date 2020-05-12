import Model.Genetic.ConstantNode;
import Model.Genetic.Node;
import Model.Genetic.OperationNode;
import Model.Genetic.VariableNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    List<Float> variables;

    @BeforeEach
    public void createVariables(){
        variables = new LinkedList<>();
        variables.add(1f);
        variables.add(2f);
        variables.add(3f);
        variables.add(4f);

    }

    @Test
    public void testCreationNode(){
        assertDoesNotThrow(()->(new VariableNode(1, variables)));
        assertDoesNotThrow(() -> (new OperationNode("mult", variables)));
        assertDoesNotThrow(() ->new ConstantNode(52.4f));

        Node node =new OperationNode("mult", variables);
        System.out.println(node + "\n " + node.getValue());
    }
}
