import java.util.HashSet;
import java.util.Set;

/**
 * Вершина графа
 */
public class Node {
    /**
     * Сет соседних вершин
     */
    private Set<Node> links;

    /**
     * Добавление двунаправленной связи
     *
     * @param v -   соседняя вершина
     */
    public void addConnection(Node v) {
        this.links.add(v);
        v.links.add(this);
    }

    public Node(Node... links) {
        this.links = new HashSet<>();
        for (Node n : links) {
            addConnection(n);
        }
    }

    public Set<Node> getLinks() {
        return links;
    }
}
