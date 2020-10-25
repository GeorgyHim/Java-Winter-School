import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для поиска компонент связности
 */
public class ComponentsFinder {
    /**
     * Поиск в глубину
     *
     * @param v       - Начальная вершина
     * @param visited - помеченные вершины
     * @param color   - цвет, он же номер компоненты
     */
    private static void dfs(Node v, Map<Node, Integer> visited, int color) {
        visited.put(v, color);
        for (Node to : v.getLinks()) {
            if (!visited.containsKey(to)) {
                dfs(to, visited, color);
            }
        }
    }

    /**
     * Метод поиска компонент связности     *
     *
     * @param graph - граф в виде списка смежности
     * @return      - сет сетов вершин, каждый внутренний сет - отдельная компонента связности
     */
    public static Set<Set<Node>> findComponents(List<Node> graph) {
        Map<Node, Integer> visited = new HashMap<>();
        int color = 1;
        for (Node v : graph) {
            if (!visited.containsKey(v)) {
                dfs(v, visited, color++);
            }
        }
        return visited.keySet().stream().collect(Collectors.groupingBy(visited::get))
                .values().stream().map(HashSet::new).collect(Collectors.toSet());

    }
}
