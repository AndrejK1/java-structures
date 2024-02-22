package learning.graph;

import java.util.function.BiConsumer;

public interface Graph {
    boolean addEdge(Edge edge);

    int addVertex(Vertex vertex);

    Vertex getVertexById(int id);

    Vertex getVertexByName(String name);

    boolean areAdjacent(int startVertexId, int endVertexId);

    int size();

    void clear();

    void dfs(int startVertexId, BiConsumer<Integer, Graph.Vertex> action);

    default void dfs(BiConsumer<Integer, Graph.Vertex> action) {
        dfs(0, action);
    }

    void bfs(int startVertexId, BiConsumer<Integer, Vertex> action);

    default void bfs(BiConsumer<Integer, Graph.Vertex> action) {
        bfs(0, action);
    }

    Graph mst(int startVertexId);

    default Graph mst() {
        return mst(0);
    }

    enum DirectionType {DIRECTED, NON_DIRECTED, MIXED}

    enum WeightType {WEIGHTED, NON_WEIGHTED}

    interface Vertex {
        String getName();
    }

    interface Edge {
        int getStartVertexId();

        int getEndVertexId();

        Double getWeight();

        default boolean isWeighted() {
            return getWeight() != null;
        }

        DirectionType getDirectionType();

        enum DirectionType {DIRECTED, NON_DIRECTED}
    }
}
