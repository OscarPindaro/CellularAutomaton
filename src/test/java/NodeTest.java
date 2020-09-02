import model.genetic.Node;
import org.junit.jupiter.api.Test;

public class NodeTest {

    @Test
    public void parserTest(){
        String tree = "add(sub(add(-0.22533025603326018, 0.7051473705603539), mul(ARG0, ARG0)), add(add(-0.9239744290346261, ARG0), mul(ARG0, 0.7879025951121006)))";
        Node n = Node.treeFromString(tree, 1);
        System.out.println(n);
        System.out.println(tree);
        System.out.println(n.toStringPython());
    }
}
