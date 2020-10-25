import java.util.*;
import java.util.stream.Collectors;

public class ConnectedComponentsFinder {

    private static void dfs(Node v, Map<Node, Integer> visited, int color) {
        visited.put(v, color);
        for (Node n : v.getLinks()) {
            if (!visited.containsKey(n)) {
                dfs(n, visited, color);
            }
        }
    }

    public static Set<Set<Node>> findConnectedComponents(List<Node> graph) {
        Map<Node, Integer> visited = new HashMap<>();
        int color = 1;
        for (Node v : graph) {
            if (!visited.containsKey(v))
                dfs(v, visited, color++);
        }
        return visited.keySet().stream().collect(Collectors.groupingBy(visited::get))
                .values().stream().<Set<Node>>map(HashSet::new).collect(Collectors.toSet());
    }

}
