import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ComponentsFinderTest {

    private Set<Node> asComponent(Node... nodes) {
        return new HashSet<>(Arrays.asList(nodes));
    }

    @SafeVarargs
    private final Set<Set<Node>> asComponentsSet(Set<Node>... components) {
        return new HashSet<>(Arrays.asList(components));
    }

    @Test
    public void testSimpleGraph() {
        Node n1 = new Node();
        Node n2 = new Node(n1);
        Node n3 = new Node(n2);
        Set<Set<Node>> components = ComponentsFinder.findComponents(Arrays.asList(n1, n2, n3));
        Assertions.assertEquals(1, components.size());
        Assertions.assertEquals(asComponentsSet(asComponent(n1, n2, n3)), components);
    }

    @Test
    public void testGraphWithOneComponent() {
        Node n1 = new Node();
        Node n2 = new Node(n1);
        Node n3 = new Node(n1);
        Node n4 = new Node(n1);
        Node n5 = new Node(n4);
        Node n6 = new Node(n5, n4);
        Set<Set<Node>> components = ComponentsFinder.findComponents(Arrays.asList(n1, n2, n3, n4, n5, n6));
        Assertions.assertEquals(1, components.size());
        Assertions.assertEquals(asComponentsSet(asComponent(n1, n2, n3, n4, n5, n6)), components);
    }

    @Test
    public void testGraphWithTwoComponent() {
        Node n1 = new Node();
        Node n2 = new Node(n1);
        Node n3 = new Node(n1);
        Node n4 = new Node();
        Node n5 = new Node(n4);
        Node n6 = new Node(n5, n4);
        Set<Set<Node>> components = ComponentsFinder.findComponents(Arrays.asList(n1, n5, n3, n4, n6, n2));
        Assertions.assertEquals(2, components.size());
        Assertions.assertEquals(asComponentsSet(asComponent(n1, n2, n3), asComponent(n4, n5, n6)), components);
    }

    @Test
    public void testGraphWithManyComponent() {
        Node n1 = new Node();
        Node n2 = new Node(n1);
        Node n3 = new Node(n1);

        Node n4 = new Node();
        Node n5 = new Node(n4);
        Node n6 = new Node(n5);

        Node n7 = new Node();

        Node n8 = new Node();
        Node n9 = new Node(n8);

        Set<Set<Node>> components = ComponentsFinder.findComponents(Arrays.asList(n9, n5, n8, n4, n6, n2, n3, n7, n1));
        Assertions.assertEquals(4, components.size());
        Assertions.assertEquals(
                asComponentsSet(asComponent(n1, n2, n3), asComponent(n4, n5, n6), asComponent(n7), asComponent(n8, n9)),
                components
        );
    }
}
