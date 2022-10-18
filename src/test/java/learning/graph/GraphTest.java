package learning.graph;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

@Slf4j
public class GraphTest {

    @Test
    public void testSimpleGraph() {
        innerTestGraph(new SimpleGraph(10, Graph.DirectionType.MIXED, Graph.WeightType.NON_WEIGHTED));
    }

    private void innerTestGraph(Graph graph) {
        Assert.assertEquals(0, graph.size());

        Stream.of("0", "1", "2", "3", "4", "5", "6")
                .map(SimpleGraph.SimpleVertex::new)
                .forEach(graph::addVertex);

        Assert.assertEquals(7, graph.size());

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

        Assert.assertTrue(graph.areAdjacent(0, 1));
        Assert.assertFalse(graph.areAdjacent(0, 2));
        Assert.assertFalse(graph.areAdjacent(0, 3));
        Assert.assertFalse(graph.areAdjacent(1, 0));
        Assert.assertFalse(graph.areAdjacent(0, 4));
        Assert.assertTrue(graph.areAdjacent(1, 2));
        Assert.assertFalse(graph.areAdjacent(1, 5));
        Assert.assertTrue(graph.areAdjacent(2, 4));
        Assert.assertFalse(graph.areAdjacent(4, 2));
        Assert.assertTrue(graph.areAdjacent(3, 4));
        Assert.assertTrue(graph.areAdjacent(4, 3));
        /*
          0
         /
        1 - 2
            |
        3 - 4
         */
        Graph mst = graph.mst();
        Assert.assertEquals(5, mst.size());

        Assert.assertTrue(mst.areAdjacent(0, 1));
        Assert.assertFalse(mst.areAdjacent(0, 2));
        Assert.assertFalse(mst.areAdjacent(0, 3));
        Assert.assertFalse(mst.areAdjacent(0, 4));

        Assert.assertTrue(mst.areAdjacent(1, 0));
        Assert.assertTrue(mst.areAdjacent(1, 2));
        Assert.assertFalse(mst.areAdjacent(1, 3));
        Assert.assertFalse(mst.areAdjacent(1, 4));

        Assert.assertFalse(mst.areAdjacent(2, 0));
        Assert.assertTrue(mst.areAdjacent(2, 1));
        Assert.assertFalse(mst.areAdjacent(2, 3));
        Assert.assertTrue(mst.areAdjacent(2, 4));

        Assert.assertFalse(mst.areAdjacent(3, 0));
        Assert.assertFalse(mst.areAdjacent(3, 1));
        Assert.assertFalse(mst.areAdjacent(3, 2));
        Assert.assertTrue(mst.areAdjacent(3, 4));

        Assert.assertFalse(mst.areAdjacent(4, 0));
        Assert.assertFalse(mst.areAdjacent(4, 1));
        Assert.assertTrue(mst.areAdjacent(4, 2));
        Assert.assertTrue(mst.areAdjacent(4, 3));

    }
}
