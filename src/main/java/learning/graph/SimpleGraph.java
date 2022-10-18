package learning.graph;

import additional.Pair;
import additional.Todo;
import learning.list.SimpleLinkedList;
import learning.queue.Queue;
import learning.stack.LinkedStack;
import learning.stack.Stack;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class SimpleGraph implements WeightedGraph {
    private static final Integer INVALID_ID = -1;
    private final Graph.DirectionType graphDirectionType;
    private final Graph.WeightType graphWeightType;

    private final List<Graph.Vertex> vertexes;
    private final AdjustableSquareMatrix<Double> adjacencyMatrix;

    public SimpleGraph() {
        this(16, Graph.DirectionType.NON_DIRECTED, Graph.WeightType.NON_WEIGHTED);
    }

    public SimpleGraph(int expectedSize, Graph.DirectionType directionType, Graph.WeightType weightType) {
        this.graphDirectionType = directionType;
        this.graphWeightType = weightType;
        this.vertexes = new ArrayList<>(expectedSize);
        this.adjacencyMatrix = new AdjustableSquareMatrix<>(expectedSize);
    }

    public SimpleGraph(Graph.DirectionType directionType, Graph.WeightType weightType,
                       List<? extends Graph.Vertex> vertexes, List<? extends Graph.Edge> edges) {
        this(vertexes.size(), directionType, weightType);
        vertexes.forEach(this::addVertex);
        edges.forEach(this::addEdge);
    }

    @Override
    public boolean addEdge(Graph.Edge edge) {
        validateEdge(edge);

        double edgeWeightValue = edge.isWeighted() ? edge.getWeight() : 1D;

        adjacencyMatrix.set(edge.getStartVertexId(), edge.getEndVertexId(), edgeWeightValue);

        if (Graph.Edge.DirectionType.NON_DIRECTED == edge.getDirectionType()) {
            adjacencyMatrix.set(edge.getEndVertexId(), edge.getStartVertexId(), edgeWeightValue);
        }

        return true;
    }

    @Override
    public int addVertex(Graph.Vertex vertex) {
        validateVertex(vertex);
        vertexes.add(vertex);
        return vertexes.size() - 1;
    }

    @Override
    public Graph.Vertex getVertexById(int id) {
        validateVertexId(id);
        return vertexes.get(id);
    }

    @Override
    public boolean areAdjacent(int startVertexId, int endVertexId) {
        validateEdgeVertexIds(startVertexId, endVertexId);
        return adjacencyMatrix.get(startVertexId, endVertexId) != null;
    }

    @Override
    public int size() {
        return vertexes.size();
    }

    @Override
    public void clear() {
        vertexes.clear();
        adjacencyMatrix.clear();
    }

    @Override
    public void dfs(int startVertexId, BiConsumer<Integer, Graph.Vertex> action) {
        requireNotNull(action, "Action");

        if (vertexes.isEmpty()) {
            return;
        }

        validateVertexId(startVertexId);

        Stack<Integer> vertexStack = new LinkedStack<>();
        Set<Integer> visitedVertexes = new HashSet<>();

        // actions for first vertex
        vertexStack.push(startVertexId);
        visitedVertexes.add(startVertexId);
        action.accept(startVertexId, vertexes.get(startVertexId));

        while (!vertexStack.isEmpty()) {
            Integer currentVertex = vertexStack.peek();

            Integer adjustedUnvisited = findAdjustedUnvisited(currentVertex, visitedVertexes);

            if (adjustedUnvisited.equals(INVALID_ID)) {
                vertexStack.pop();
                continue;
            }

            vertexStack.push(adjustedUnvisited);
            visitedVertexes.add(adjustedUnvisited);
            action.accept(adjustedUnvisited, vertexes.get(adjustedUnvisited));
        }
    }

    @Override
    public void bfs(int startVertexId, BiConsumer<Integer, Graph.Vertex> action) {
        requireNotNull(action, "Action");

        if (vertexes.isEmpty()) {
            return;
        }

        validateVertexId(startVertexId);

        Set<Integer> visitedVertexes = new HashSet<>();
        Queue<Integer> vertexQueue = new SimpleLinkedList<>();

        // actions for first vertex
        vertexQueue.push(startVertexId);
        visitedVertexes.add(startVertexId);
        action.accept(startVertexId, vertexes.get(startVertexId));

        Integer vertexBufferId;

        while (!vertexQueue.isEmpty()) {
            Integer currentVertex = vertexQueue.pop();

            while (!(vertexBufferId = findAdjustedUnvisited(currentVertex, visitedVertexes)).equals(INVALID_ID)) {
                visitedVertexes.add(vertexBufferId);
                vertexQueue.push(vertexBufferId);
                action.accept(vertexBufferId, vertexes.get(vertexBufferId));
            }
        }
    }

    @Override
    public Graph mst(int startVertexId) {
        if (vertexes.isEmpty()) {
            return new SimpleGraph(0, Graph.DirectionType.NON_DIRECTED, Graph.WeightType.NON_WEIGHTED);
        }

        validateVertexId(startVertexId);

        List<Pair<Integer, Graph.Vertex>> mstVertexes = new ArrayList<>();
        dfs(startVertexId, (id, v) -> mstVertexes.add(Pair.of(id, new SimpleVertex(v.getName()))));

        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < mstVertexes.size(); i++) {
            edges.add(SimpleEdge.nonDirected(mstVertexes.get(i - 1).getKey(), mstVertexes.get(i).getKey()));
        }

        return new SimpleGraph(Graph.DirectionType.NON_DIRECTED, Graph.WeightType.NON_WEIGHTED,
                mstVertexes.stream().map(Pair::getValue).collect(Collectors.toList()), edges);
    }

    @Override
    public WeightedGraph mstw() {
        if (graphWeightType != Graph.WeightType.WEIGHTED) {
            throw new IllegalStateException("Graph type must be WEIGHTED");
        }

        throw new Todo();
    }

    private Integer findAdjustedUnvisited(int id, Set<Integer> visitedVertexes) {
        for (int i = 0; i < adjacencyMatrix.width(); i++) {
            Double relationWeight = adjacencyMatrix.get(id, i);

            if (relationWeight != null && !visitedVertexes.contains(i)) {
                return i;
            }
        }

        return INVALID_ID;
    }

    private void requireNotNull(Object o, String title) {
        if (o == null) {
            throw new IllegalArgumentException(title + " is null");
        }
    }

    private void validateVertex(Graph.Vertex vertex) {
        requireNotNull(vertex, "Vertex");

        if (vertex.getName() == null) {
            throw new IllegalArgumentException("Vertex name is null");
        }
    }

    private void validateEdge(Graph.Edge edge) {
        requireNotNull(edge, "Edge");
        requireNotNull(edge.getDirectionType(), "Edge direction type");

        validateEdgeVertexIds(edge.getStartVertexId(), edge.getEndVertexId());

        if (graphDirectionType != Graph.DirectionType.MIXED && !isEdgeTypeTheSame(edge)) {
            throw new IllegalArgumentException("Edge type must be " + graphDirectionType);
        }

        if (graphWeightType == Graph.WeightType.WEIGHTED && !edge.isWeighted()) {
            throw new IllegalArgumentException("Edge must have weight");
        }

        if (graphWeightType == Graph.WeightType.NON_WEIGHTED && edge.isWeighted()) {
            throw new IllegalArgumentException("Edge must not have weight");
        }
    }

    private void validateEdgeVertexIds(int startVertexId, int endVertexId) {
        validateVertexId(startVertexId);
        validateVertexId(endVertexId);

        if (startVertexId == endVertexId) {
            throw new IllegalArgumentException("Edge start matches it's end");
        }
    }

    private boolean isEdgeTypeTheSame(Graph.Edge edge) {
        return graphDirectionType.name().equals(edge.getDirectionType().name());
    }

    private void validateVertexId(int vertexId) {
        if (vertexId >= vertexes.size()) {
            throw new IllegalArgumentException("Vertex id " + vertexId + " is not allowed");
        }
    }

    @Data
    public static class SimpleVertex implements Graph.Vertex {
        private final String name;
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SimpleEdge implements Graph.Edge {
        private final int startVertexId;
        private final int endVertexId;
        private final Double weight;
        private final DirectionType directionType;

        public static SimpleEdge nonDirected(int startVertexId, int endVertexId) {
            return new SimpleEdge(startVertexId, endVertexId, null, DirectionType.NON_DIRECTED);
        }

        public static SimpleEdge directed(int startVertexId, int endVertexId) {
            return new SimpleEdge(startVertexId, endVertexId, null, DirectionType.DIRECTED);
        }

        public static SimpleEdge weighted(int startVertexId, int endVertexId, double weight) {
            return new SimpleEdge(startVertexId, endVertexId, weight, DirectionType.NON_DIRECTED);
        }

        public static SimpleEdge weightedDirected(int startVertexId, int endVertexId, double weight) {
            return new SimpleEdge(startVertexId, endVertexId, weight, DirectionType.DIRECTED);
        }
    }
}
