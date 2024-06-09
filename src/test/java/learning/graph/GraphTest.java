package learning.graph;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
class GraphTest {

    @Test
    void testSimpleGraph() {
        innerTestGraph(new SimpleGraph(10, Graph.DirectionType.MIXED, Graph.WeightType.NON_WEIGHTED));
    }

    private void innerTestGraph(Graph graph) {
        Assertions.assertEquals(0, graph.size());

        Stream.of("0", "1", "2", "3", "4", "5", "6")
                .map(SimpleGraph.SimpleVertex::new)
                .forEach(graph::addVertex);

        Assertions.assertEquals(7, graph.size());

        /*
            0
         /.   \.
        1   -   2
        |.  .\  |.
        3   -   4
         */
        graph.addEdge(SimpleGraph.SimpleEdge.directed(0, 1));
        graph.addEdge(SimpleGraph.SimpleEdge.nonDirected(2, 1));
        graph.addEdge(SimpleGraph.SimpleEdge.directed(2, 0));
        graph.addEdge(SimpleGraph.SimpleEdge.directed(1, 3));
        graph.addEdge(SimpleGraph.SimpleEdge.directed(4, 1));
        graph.addEdge(SimpleGraph.SimpleEdge.nonDirected(3, 4));
        graph.addEdge(SimpleGraph.SimpleEdge.directed(2, 4));

        Assertions.assertTrue(graph.areAdjacent(0, 1));
        Assertions.assertFalse(graph.areAdjacent(0, 2));
        Assertions.assertFalse(graph.areAdjacent(0, 3));
        Assertions.assertFalse(graph.areAdjacent(1, 0));
        Assertions.assertFalse(graph.areAdjacent(0, 4));
        Assertions.assertTrue(graph.areAdjacent(1, 2));
        Assertions.assertFalse(graph.areAdjacent(1, 5));
        Assertions.assertTrue(graph.areAdjacent(2, 4));
        Assertions.assertFalse(graph.areAdjacent(4, 2));
        Assertions.assertTrue(graph.areAdjacent(3, 4));
        Assertions.assertTrue(graph.areAdjacent(4, 3));
        /*
          0
         /
        1 - 2
            |
        3 - 4
         */
        Graph mst = graph.mst();
        Assertions.assertEquals(5, mst.size());

        Assertions.assertTrue(mst.areAdjacent(0, 1));
        Assertions.assertFalse(mst.areAdjacent(0, 2));
        Assertions.assertFalse(mst.areAdjacent(0, 3));
        Assertions.assertFalse(mst.areAdjacent(0, 4));

        Assertions.assertTrue(mst.areAdjacent(1, 0));
        Assertions.assertTrue(mst.areAdjacent(1, 2));
        Assertions.assertFalse(mst.areAdjacent(1, 3));
        Assertions.assertFalse(mst.areAdjacent(1, 4));

        Assertions.assertFalse(mst.areAdjacent(2, 0));
        Assertions.assertTrue(mst.areAdjacent(2, 1));
        Assertions.assertFalse(mst.areAdjacent(2, 3));
        Assertions.assertTrue(mst.areAdjacent(2, 4));

        Assertions.assertFalse(mst.areAdjacent(3, 0));
        Assertions.assertFalse(mst.areAdjacent(3, 1));
        Assertions.assertFalse(mst.areAdjacent(3, 2));
        Assertions.assertTrue(mst.areAdjacent(3, 4));

        Assertions.assertFalse(mst.areAdjacent(4, 0));
        Assertions.assertFalse(mst.areAdjacent(4, 1));
        Assertions.assertTrue(mst.areAdjacent(4, 2));
        Assertions.assertTrue(mst.areAdjacent(4, 3));
    }

    @Test
    void findShortestPathTest() {
        SimpleGraph simpleGraph = new SimpleGraph(13, Graph.DirectionType.MIXED, Graph.WeightType.WEIGHTED);

        int s = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("S"));
        int a = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("A"));
        int b = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("B"));
        int c = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("C"));
        int d = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("D"));
        int e = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("E"));
        int f = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("F"));
        int g = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("G"));
        int h = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("H"));
        int i = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("I"));
        int j = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("J"));
        int k = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("K"));
        int l = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("L"));

        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(s, a, 7));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(s, b, 2));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(s, c, 3));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(a, d, 4));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(a, b, 3));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(d, b, 4));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(d, f, 5));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(f, h, 3));

        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weightedDirected(h, b, 1));

        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(h, g, 2));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(g, e, 2));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(c, l, 2));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(l, i, 4));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(l, j, 4));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(i, j, 6));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(i, k, 4));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(j, k, 4));
        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(k, e, 6));

        WeightedGraph.FoundPath shortestPath = simpleGraph.findShortestPath(s, e);

        Assertions.assertTrue(shortestPath.isPathPossible());
        Assertions.assertEquals(0, Double.compare(18D, shortestPath.getPathLength()));

        List<String> correctVertexOrder = List.of("B", "D", "F", "H", "G", "E");

        Assertions.assertEquals(correctVertexOrder.size(), shortestPath.getPathVertexes().size());

        for (int index = 0; index < correctVertexOrder.size(); index++) {
            Assertions.assertEquals(correctVertexOrder.get(index), shortestPath.getPathVertexes().get(index).getName());
        }
    }

    @Test
    void findNoPathTest() {
        SimpleGraph simpleGraph = new SimpleGraph(13, Graph.DirectionType.MIXED, Graph.WeightType.WEIGHTED);

        int s = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("S"));
        int a = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("A"));
        int b = simpleGraph.addVertex(new SimpleGraph.SimpleVertex("B"));

        simpleGraph.addEdge(SimpleGraph.SimpleEdge.weighted(s, a, 7));

        WeightedGraph.FoundPath shortestPath = simpleGraph.findShortestPath(s, b);

        Assertions.assertFalse(shortestPath.isPathPossible());
    }
}
