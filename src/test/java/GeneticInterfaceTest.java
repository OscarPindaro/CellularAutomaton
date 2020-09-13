import Network.GeneticInterface;
import controller.behaviours.PreyBehaviour;
import model.entity.Prey;
import model.genetic.Function;
import model.genetic.Node;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class GeneticInterfaceTest {

    @Test
    public void testConversionToJson(){
        String tree = "sub(add(-0.22533025603326018, 0.7051473705603539), mul(ARG0, ARG0))";
        Node n = Node.treeFromString(tree,1);
        List<Function> list = new LinkedList<>();
        list.add(n);
        GeneticInterface gi = new GeneticInterface();
        PreyBehaviour preyBehaviour = new PreyBehaviour(null);
        Prey prey = new Prey();
        preyBehaviour.setDecisionFunction(prey, list);
        JSONObject traduction = gi.dictionaryEntityFunctions(preyBehaviour);
        System.out.println(traduction);
    }
}
