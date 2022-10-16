package learning.graph;

import learning.list.SimpleLinkedList;
import learning.queue.Queue;
import learning.stack.LinkedStack;
import learning.stack.Stack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class SimpleGraph implements Graph {
    private static final Integer INVALID_ID = -1;
    private final List<Graph.Vertex> vertexes;
    private final AdjustableSquareMatrix<Integer> matrix;

    public SimpleGraph() {
        this(16);
    }

    public SimpleGraph(int expectedSize) {
        this.vertexes = new ArrayList<>(expectedSize);
        this.matrix = new AdjustableSquareMatrix<>(expectedSize, 0);
    }

    public SimpleGraph(List<? extends Graph.Vertex> vertexes, List<? extends Graph.Edge> edges) {
        this.vertexes = new ArrayList<>(vertexes);
        this.matrix = new AdjustableSquareMatrix<>(vertexes.size(), 0);
        edges.forEach(this::addEdge);
    }

    @Override
    public boolean addEdge(Graph.Edge edge) {
        validateEdge(edge);

        matrix.set(edge.getStartVertexId(), edge.getEndVertexId(), 1);

        if (Edge.DirectionType.NON_DIRECTED == edge.getDirectionType()) {
            matrix.set(edge.getEndVertexId(), edge.getStartVertexId(), 1);
        }

        return true;
    }

    @Override
    public int addVertex(Graph.Vertex vertex) {
        vertexes.add(vertex);
        return vertexes.size() - 1;
    }

    @Override
    public Graph.Vertex getVertexById(int id) {
        return vertexes.get(id);
    }

    @Override
    public int size() {
        return vertexes.size();
    }

    @Override
    public void clear() {
        vertexes.clear();
        matrix.clear(0);
    }

    @Override
    public void dfs(int startVertexId, Consumer<Graph.Vertex> action) {
        if (vertexes.isEmpty()) {
            return;
        }

        validateVertexId(startVertexId);

        Stack<Integer> vertexStack = new LinkedStack<>();
        Set<Integer> visitedVertexes = new HashSet<>();

        // actions for first vertex
        vertexStack.push(startVertexId);
        visitedVertexes.add(startVertexId);
        action.accept(vertexes.get(startVertexId));

        while (!vertexStack.isEmpty()) {
            Integer currentVertex = vertexStack.peek();

            Integer adjustedUnvisited = findAdjustedUnvisited(currentVertex, visitedVertexes);

            if (adjustedUnvisited.equals(INVALID_ID)) {
                vertexStack.pop();
                continue;
            }

            vertexStack.push(adjustedUnvisited);
            visitedVertexes.add(adjustedUnvisited);
            action.accept(vertexes.get(adjustedUnvisited));
        }
    }

    @Override
    public void bfs(int startVertexId, Consumer<Graph.Vertex> action) {
        if (vertexes.isEmpty()) {
            return;
        }

        validateVertexId(startVertexId);

        Set<Integer> visitedVertexes = new HashSet<>();
        Queue<Integer> vertexQueue = new SimpleLinkedList<>();

        // actions for first vertex
        vertexQueue.push(startVertexId);
        visitedVertexes.add(startVertexId);
        action.accept(vertexes.get(startVertexId));

        Integer vertexBufferId;

        while (!vertexQueue.isEmpty()) {
            Integer currentVertex = vertexQueue.pop();


            while (!(vertexBufferId = findAdjustedUnvisited(currentVertex, visitedVertexes)).equals(INVALID_ID)) {
                visitedVertexes.add(vertexBufferId);
                vertexQueue.push(vertexBufferId);
                action.accept(vertexes.get(vertexBufferId));
            }
        }
    }

    @Override
    public Graph mst(int startVertexId) {
        if (vertexes.isEmpty()) {
            return new SimpleGraph(0);
        }

        validateVertexId(startVertexId);

        List<Vertex> vertices = new ArrayList<>();
        dfs(startVertexId, v -> vertices.add(new SimpleVertex(v.getName())));

        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < vertices.size(); i++) {
            edges.add(SimpleEdge.nonDirected(i - 1, i));
        }

        return new SimpleGraph(vertices, edges);
    }

    private Integer findAdjustedUnvisited(int id, Set<Integer> visitedVertexes) {
        for (int i = 0; i < matrix.width(); i++) {
            Integer relationFlag = matrix.get(id, i);

            if (relationFlag == 1 && !visitedVertexes.contains(i)) {
                return i;
            }
        }

        return INVALID_ID;
    }

    private void validateEdge(Graph.Edge edge) {
        validateVertexId(edge.getStartVertexId());
        validateVertexId(edge.getEndVertexId());

        if (edge.getStartVertexId() == edge.getEndVertexId()) {
            throw new IllegalArgumentException("Edge start matches it's end");
        }

        if (edge.getDirectionType() == null) {
            throw new IllegalArgumentException("Direction type is null");
        }
    }

    private void validateVertexId(int vertexId) {
        if (vertexId >= vertexes.size()) {
            throw new IllegalArgumentException("Vertex id " + vertexId + " is not allowed");
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class SimpleVertex implements Graph.Vertex {
        private final String name;
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SimpleEdge implements Graph.Edge {
        private final int startVertexId;
        private final int endVertexId;
        private final double weight;
        private final DirectionType directionType;

        public static SimpleEdge nonDirected(int startVertexId, int endVertexId) {
            return new SimpleEdge(startVertexId, endVertexId, 0, DirectionType.NON_DIRECTED);
        }

        public static SimpleEdge directed(int startVertexId, int endVertexId) {
            return new SimpleEdge(startVertexId, endVertexId, 0, DirectionType.DIRECTED);
        }

        public static SimpleEdge weighted(int startVertexId, int endVertexId, double weight) {
            return new SimpleEdge(startVertexId, endVertexId, weight, DirectionType.NON_DIRECTED);
        }

        public static SimpleEdge weightedDirected(int startVertexId, int endVertexId, double weight) {
            return new SimpleEdge(startVertexId, endVertexId, weight, DirectionType.DIRECTED);
        }
    }
}
