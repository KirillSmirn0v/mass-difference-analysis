import java.util.ArrayList;
import java.util.List;

/**
 * The basic class representing a graph.
 * @param <N> generic parameter representing a node.
 * @param <E> generic parameter representing an edge.
 */
public abstract class Graph<N, E> {
    private List<N> nodes;
    private List<E> edges;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(N node) {
        nodes.add(node);
    }

    public void addEdge(E edge) {
        edges.add(edge);
    }

    public List<N> getNodes() {
        return nodes;
    }

    public List<E> getEdges() {
        return edges;
    }
}
