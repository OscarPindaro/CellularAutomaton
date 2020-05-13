import Model.Genetic.ConstantNode;
import Model.Genetic.Node;
import Model.Genetic.OperationNode;
import Model.Genetic.VariableNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    List<Float> variables;
    List<Float> toPropagate;

    Node root;

    @BeforeEach
    public void createVariables(){
        variables = new LinkedList<>();
        toPropagate = new ArrayList<>(4);
        variables.add(1f);
        variables.add(2f);
        variables.add(3f);

        for(Float var: variables) toPropagate.add(new Float(var));
        variables.add(4f);
        toPropagate.add(5f);
        root = Node.createRandomTree(variables);

    }


    @Test
    public void testCreationNode(){
        assertDoesNotThrow(()->(new VariableNode(1, variables)));
        assertDoesNotThrow(() -> (new OperationNode("mult", variables)));
        assertDoesNotThrow(() ->new ConstantNode(52.4f));
    }

    @Test
    public void testCallMethods(){
        Node node =new OperationNode("mult", variables);
        assertDoesNotThrow(node::getValue);
    }

    @Test
    public void testCopyTree(){
        List<Float> newVariables = new ArrayList<>(variables.size());
        for(Float var: variables){
            newVariables.add(new Float(var));
        }
        Node copyRoot = root.copyTree(newVariables);

        assertEquals(root.toString(), copyRoot.toString());
        assertEquals(root.getValue(), copyRoot.getValue());

    }

    @Test
    public void testPropagationValues(){
        float previousValue = root.getValue();
        root.propagateVariables(toPropagate);
        assertNotEquals(previousValue, root.getValue());
    }

    @Test
    public void testNumberOfNodes(){
        assertDoesNotThrow(root::numberOfNodes);
    }
}
