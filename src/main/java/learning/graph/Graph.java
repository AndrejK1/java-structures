package learning.graph;

import java.util.function.Consumer;

public interface Graph {
    boolean addEdge(Edge edge);

    int addVertex(Vertex vertex);

    Vertex getVertexById(int id);

    int size();

    void clear();

    void dfs(int startVertexId, Consumer<Vertex> action);

    default void dfs(Consumer<Vertex> action) {
        dfs(0, action);
    }

    void bfs(int startVertexId, Consumer<Vertex> action);

    default void bfs(Consumer<Vertex> action) {
        bfs(0, action);
    }

    Graph mst(int startVertexId);

    default Graph mst() {
        return mst(0);
    }

    interface Vertex {
        String getName();
    }

    interface Edge {
        int getStartVertexId();

        int getEndVertexId();

        double getWeight();

        DirectionType getDirectionType();

        enum DirectionType {DIRECTED, NON_DIRECTED}
    }
}
