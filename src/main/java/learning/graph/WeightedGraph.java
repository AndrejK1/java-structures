package learning.graph;

import java.util.List;

public interface WeightedGraph extends Graph {
    WeightedGraph mstw();

    FoundPath findShortestPath(int startVertex, int endVertex);

    FoundPath findShortestPath(String startVertex, String endVertex);

    interface FoundPath {
        boolean isPathPossible();

        List<Vertex> getPathVertexes();

        double getPathLength();
    }
}
