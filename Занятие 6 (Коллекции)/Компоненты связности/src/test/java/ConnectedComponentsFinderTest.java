import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ConnectedComponentsFinderTest {

    private Set<Node> asComponent(Node... nodes) {
        return new HashSet<>(Arrays.asList(nodes));
    }

    @SafeVarargs
    private final Set<Set<Node>> getComponentsSet(Set<Node>... components) {
        return new HashSet<Set<Node>>() {{
            this.addAll(Arrays.asList(components));
        }};
    }

    @Test
    public void testSimpleGraph() {
        Node n1 = new Node("1");
        Node n2 = new Node("2", n1);
        Node n3 = new Node("3", n1, n2);

        List<Node> graph = Arrays.asList(n1, n2, n3);
        Set<Set<Node>> components = ConnectedComponentsFinder.findConnectedComponents(graph);
        Assertions.assertEquals(1, components.size());
        Assertions.assertEquals(getComponentsSet(asComponent(n1, n2, n3)), components);
    }

    @Test
    public void testGraphWithOneComponent() {
        Node n1 = new Node("1");
        Node n2 = new Node("2", n1);
        Node n3 = new Node("3", n1);
        Node n4 = new Node("4", n1);
        Node n5 = new Node("5", n4);
        Node n6 = new Node("6", n4, n5);
        List<Node> graph = Arrays.asList(n1, n2, n3, n4, n5, n6);
        Set<Set<Node>> components = ConnectedComponentsFinder.findConnectedComponents(graph);
        Assertions.assertEquals(1, components.size());
        Assertions.assertEquals(getComponentsSet(asComponent(n1, n2, n3, n4, n5, n6)), components);
    }

    @Test
    public void testGraphWithTwoComponents() {
        Node n1 = new Node("1");
        Node n2 = new Node("2", n1);
        Node n3 = new Node("3", n1);
        Node n4 = new Node("4");
        Node n5 = new Node("5", n4);
        Node n6 = new Node("6", n4, n5);
        List<Node> graph = Arrays.asList(n1, n2, n3, n4, n5, n6);
        Set<Set<Node>> components = ConnectedComponentsFinder.findConnectedComponents(graph);
        Assertions.assertEquals(2, components.size());
        Assertions.assertEquals(getComponentsSet(asComponent(n1, n2, n3), asComponent(n4, n5, n6)), components);
    }

    @Test
    public void testGraphWithManyComponents() {
        Node n1 = new Node("1");
        Node n2 = new Node("2", n1);
        Node n3 = new Node("3", n2);

        Node n4 = new Node("4");
        Node n5 = new Node("5", n4);
        Node n6 = new Node("6", n4, n5);

        Node n7 = new Node("7");

        Node n8 = new Node("8");
        Node n9 = new Node("9", n8);

        List<Node> graph = Arrays.asList(n5, n7, n2, n9, n8, n1, n4, n3, n6);
        Set<Set<Node>> components = ConnectedComponentsFinder.findConnectedComponents(graph);
        Assertions.assertEquals(4, components.size());
        Assertions.assertEquals(
            getComponentsSet(asComponent(n1, n2, n3), asComponent(n4, n5, n6), asComponent(n7), asComponent(n8, n9)),
            components
        );
    }
}
